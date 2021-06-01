package emma.galzio.tpidlcmotorbusquedaficheros.indexation;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListReader.PostingListReader;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListWriter.PostingListWriter;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Service
public class IndexationEngine {

    private static final int MAX_FILES_INDEXED = 100;

    private List<String> indexedDocuments;
    @Autowired
    @Qualifier("utfFileReader")
    private FileReader fileReader;

    @Autowired
    @Qualifier("postingListDBReader")
    private PostingListReader postingListReader;
    @Autowired
    @Qualifier("postingListDBWriter")
    private PostingListWriter postingListWriter;
    @Autowired
    private IDocumentController documentController;
    @Autowired
    private VocabularyController vocabularyController;

    @Autowired //Es un bean porque utiliza el patrón singleton, es el mismo para toda la aplicación
    private Map<String, VocabularySlot> vocabulary;
    private Map<String,ModifiedToken> modifiedPostingLists;

    private Logger logger;

    public IndexationEngine(){

        logger = LoggerFactory.getLogger(IndexationEngine.class);

    }

    @PostConstruct
    private void init(){
        indexedDocuments = documentController.listAllIndexedDocuments();
        if(indexedDocuments == null) indexedDocuments = new ArrayList<>();
        vocabularyController.loadVocabulary();
    }



    public void indexFiles(File file){
        Date inicio = new Date();

        if(!file.exists()) throw new IllegalArgumentException("The path doesn't match with any existent file");
        if(file.isDirectory()){
            this.indexDirectory(file.listFiles());
        } else{
            this.indexSingleFile(file);
        }
        Date fin = new Date();
        logger.info("Tiempo transcurrido: " + (float)(fin.getTime() - inicio.getTime())/1000/60 + " minutos");
    }


    public void indexFiles(String path){
        File file = new File(path);
        this.indexFiles(file);
    }

    //Necesitaría una regex para pasarle a un StringTokenizer y así poder procesar cada token de la query
    public Map<String, VocabularySlot> indexQuery(String query){
        if(query == null || query.isEmpty()) return null;
        return null;
    }


    private void indexSingleFile(File file){
        if(this.indexFile(file)){
            postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
            modifiedPostingLists = null;
        }
    }

    private void indexDirectory(File[] files){
        int filesIndexed = 0;

        for(File document : files){

            if(this.indexFile(document)){
                filesIndexed++;
            }


            if(filesIndexed == MAX_FILES_INDEXED){
                postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
                modifiedPostingLists = null;
                filesIndexed = 0;
            }
        }
        postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
        modifiedPostingLists = null;
    }

    private boolean indexFile(File document){
        if(this.isIndexed(document)) return false;

        logger.info("=====================================");
        logger.info("Processing file: " + document.getName());
        try{
            fileReader.openFile(document.getAbsolutePath());
            while(fileReader.isReady()) {
                String token = fileReader.readNextWord();

                if (!this.isValidToken(token)) continue;

                token = token.toLowerCase();
                VocabularySlot vocabularySlot = vocabulary.getOrDefault(token, new VocabularySlot());

                if (vocabularySlot.existInVocabulary()) {
                    if (!vocabularySlot.postingListIsLoaded()) {
                        postingListReader.loadPostingList(vocabularySlot);
                    }
                    if(!vocabularySlot.incrementPostingSlotForDocument(document.getAbsolutePath())){
                        vocabularySlot.addPostingSlot(document.getAbsolutePath());
                    }
                } else {
                    vocabularySlot.setToken(token);
                    vocabularySlot.addPostingSlot(document.getAbsolutePath());
                    vocabulary.put(token, vocabularySlot);
                }
                ModifiedToken modifiedToken = new ModifiedToken();
                modifiedToken.setToken(vocabularySlot.getToken());
                this.addModifiedToken(modifiedToken);
                this.addIndexedDocument(document);
            }
            logger.info("File processing finished");
            logger.info("========================");
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean isValidToken(String token){
        return token != null && !token.isEmpty();
    }

    private void addIndexedDocument(File document){
        if(!indexedDocuments.contains(document.getAbsolutePath()))
                indexedDocuments.add(document.getAbsolutePath());
    }

    /*
    @Transactional
    public void indexTest(){

        Date inicio = new Date();
        File rootDirectory = new File(TESTING_DOCUMENTS_ROOT_DIRECTORY);
        int filesIndexed = 0;

        if(rootDirectory.exists() && rootDirectory.isDirectory()) {
            File[] documents = rootDirectory.listFiles();
            try{

                for (File document : documents) {
                    logger.info("=====================================");
                    logger.info("Processing file: " + document.getName());
                    if(this.isIndexed(document)){
                        continue;
                    }
                    fileReader.openFile(document.getAbsolutePath());
                    while (fileReader.isReady()) {

                        String token = fileReader.readNextWord();
                        if(token == null || token.isEmpty()){
                            continue;
                        }
                        token = token.toLowerCase();
                        VocabularySlot vocabularySlot = vocabulary.getOrDefault(token, new VocabularySlot());
                        ModifiedToken modifiedToken = new ModifiedToken();

                        if (vocabularySlot.existInVocabulary()) {

                            if (!vocabularySlot.postingListIsLoaded()) {

                                postingListReader.loadPostingList(vocabularySlot);
                                modifiedToken.setOriginalListSize(vocabularySlot.getPostingList().size());
                                modifiedToken.setStartOfListIndex(vocabularySlot.getPostingListStartIndex());
                           }
                            if(!vocabularySlot.incrementPostingSlotForDocument(document.getAbsolutePath())){

                                vocabularySlot.addPostingSlot(document.getAbsolutePath());
                            }
                        }else{
                            vocabularySlot.setToken(token);
                            vocabularySlot.addPostingSlot(document.getAbsolutePath());
                            vocabulary.put(token, vocabularySlot);
                        }
                        modifiedToken.setToken(vocabularySlot.getToken());
                        this.addModifiedToken(modifiedToken);
                    }
                    logger.info("File processing finished");
                    logger.info("========================");
                    if(!indexedDocuments.contains(document.getAbsolutePath())){
                        indexedDocuments.add(document.getAbsolutePath());
                    }

                    ++filesIndexed;
                    if(filesIndexed == MAX_FILES_INDEXED){

                        postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
                        modifiedPostingLists = null;
                        logger.info("=============================");

                        filesIndexed = 0;
                    }
                }
                postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);

            }catch (Exception e){
                logger.error("Ocurrio un error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        Date fin = new Date();
        logger.info("===============================");
        logger.info("================================");
        logger.info("Tiempo que demoró en procesar archivos:" + (((fin.getTime() - inicio.getTime()))/1000)/60);
        logger.info("Cantidad de tokens detectados: " + vocabulary.size());

    }
    */

    private boolean isIndexed(File file){
        return indexedDocuments.contains(file.getAbsolutePath());
    }

    private void addModifiedToken(ModifiedToken modifiedToken){
        if(modifiedPostingLists == null) modifiedPostingLists = Collections.synchronizedMap(new HashMap<>());

        modifiedPostingLists.put(modifiedToken.getToken(), modifiedToken);

    }



    //<editor-fold desc="GETTERS AND SETTERS">

    public List<String> getIndexedDocuments() {
        return indexedDocuments;
    }

    public void setIndexedDocuments(List<String> indexedDocuments) {
        this.indexedDocuments = indexedDocuments;
    }

    public Map<String, VocabularySlot> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Map<String, VocabularySlot> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public Map<String,ModifiedToken> getModifiedPostingLists() {
        return modifiedPostingLists;
    }

    public void setModifiedPostingLists(Map<String,ModifiedToken> modifiedPostingLists) {
        this.modifiedPostingLists = modifiedPostingLists;
    }
    //</editor-fold>
}

