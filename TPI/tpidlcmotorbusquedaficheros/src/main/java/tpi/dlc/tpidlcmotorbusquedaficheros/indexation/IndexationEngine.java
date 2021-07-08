package tpi.dlc.tpidlcmotorbusquedaficheros.indexation;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListReader.PostingListReader;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListWriter.PostingListWriter;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import tpi.dlc.tpidlcmotorbusquedaficheros.utils.FileReader;
import tpi.dlc.tpidlcmotorbusquedaficheros.utils.StringNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Service
public class IndexationEngine {

    private static final int MAX_FILES_INDEXED = 600;

    private List<String> indexedDocuments;
    @Autowired
    @Qualifier("utfFileReader")
    private FileReader fileReader;

    @Autowired
    @Qualifier("postingListDBReader")
    private PostingListReader postingListReader;
    @Autowired
    @Qualifier("postingListJDBCWriter")
    private PostingListWriter postingListWriter;
    @Autowired
    private IDocumentController documentController;
    @Autowired
    private VocabularyController vocabularyController;

    @Autowired //Es un bean que utiliza el patrón singleton, es el mismo para toda la aplicación
    private Map<String, VocabularySlot> vocabulary;
    private Map<String,ModifiedToken> modifiedPostingLists;
    //Se deberia cargar al principio y mantenerse en memoria porque el servicio es un bean singleton
    private HashSet<String> stopWords;

    private Logger logger;

    public IndexationEngine(){

        logger = LoggerFactory.getLogger(IndexationEngine.class);
    }

    @PostConstruct
    private void init(){
        indexedDocuments = documentController.listAllIndexedDocuments();
        if(indexedDocuments == null) indexedDocuments = new ArrayList<>();
        if(vocabulary.isEmpty()) vocabularyController.loadVocabulary();
        stopWords = vocabularyController.loadStopWords();
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
        logger.info("Elapsed Time: " + (float)(fin.getTime() - inicio.getTime())/1000/60 + " minutos");
    }


    public void indexFiles(String path){
        File file = new File(path);
        this.indexFiles(file);
    }

    public Map<String, VocabularySlot> indexQuery(String query){
        if(query == null || query.isEmpty()) return null;

        //String [] tokens = query.split("[\\p{Punct}\\s]+");
        //String [] tokens = query.split("([.,;:¿?!¡=-_'\"]*\\s)");
        query = query + " ";
        String [] tokens = query.split("([\\W]*\\s)");
        Map<String, VocabularySlot> hastable = new Hashtable<>();

        StringNormalizer formatter = new StringNormalizer();

        for(String token: tokens){
            token = formatter.unaccent(token);
            token=token.toLowerCase();
            if(!stopWords.contains(token)) {
                VocabularySlot queryToken = hastable.getOrDefault(token, new VocabularySlot());
                if (queryToken.getToken() == null) {
                    queryToken.setToken(token);
                }
                queryToken.incrementMaxTf();
                hastable.put(token, queryToken);
            }
        }
        return hastable;
    }


    private void indexSingleFile(File file){
        if(this.indexFile(file)){
            postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
            modifiedPostingLists = null;
            System.gc();
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
                System.gc();
            }
        }
        postingListWriter.writeAndClean(vocabulary, modifiedPostingLists);
        modifiedPostingLists = null;
        System.gc();
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
                ModifiedToken modifiedToken = new ModifiedToken();
                modifiedToken.setToken(token);

                if (vocabularySlot.existInVocabulary()) {
                    if (!vocabularySlot.postingListIsLoaded()) {
                        postingListReader.loadPostingList(vocabularySlot);
                        modifiedToken.setNeedUpdate(true);
                    }
                    if(!vocabularySlot.incrementPostingSlotForDocument(document.getAbsolutePath())){
                        vocabularySlot.addPostingSlot(document.getAbsolutePath());
                    }
                } else {
                    vocabularySlot.setToken(token);
                    vocabularySlot.addPostingSlot(document.getAbsolutePath());
                    vocabulary.put(token, vocabularySlot);

                    modifiedToken.setNewToken(true);
                    modifiedToken.setNeedUpdate(false);
                }
                this.addModifiedToken(modifiedToken);
                this.addIndexedDocument(document);
            }
            fileReader.closeFile();
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


    private boolean isIndexed(File file){
        return indexedDocuments.contains(file.getAbsolutePath());
    }

    private void addModifiedToken(ModifiedToken modifiedToken){
        if(modifiedPostingLists == null) modifiedPostingLists = Collections.synchronizedMap(new HashMap<>());

        ModifiedToken aux = modifiedPostingLists.get(modifiedToken.getToken());
        if(aux != null){
            if(aux.isNewToken()){
                modifiedToken.setNewToken(true);
            }
            if(aux.isNeedUpdate() && !modifiedToken.isNeedUpdate()){
                modifiedToken.setNeedUpdate(true);
            }
        }
        modifiedPostingLists.put(modifiedToken.getToken(), modifiedToken);
    }

    //<editor-fold desc="GETTERS AND SETTERS">

    public List<String> getIndexedDocuments() {
        return indexedDocuments;
    }
    public void setIndexedDocuments(List<String> indexedDocuments) {
        this.indexedDocuments = indexedDocuments;
    }

    //</editor-fold>
}

