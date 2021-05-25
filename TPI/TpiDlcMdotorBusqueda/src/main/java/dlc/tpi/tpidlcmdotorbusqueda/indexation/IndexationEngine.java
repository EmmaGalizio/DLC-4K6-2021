package dlc.tpi.tpidlcmdotorbusqueda.indexation;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListReader.PostingListReader;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListWriter.PostingListFileWriter;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListWriter.PostingListWriter;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.ModifiedToken;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.VocabularySlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class IndexationEngine {

    private String documentsRootDirecroty;
    private static final String TESTING_DOCUMENTS_ROOT_DIRECTORY = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\documentos\\test";
    private static final int MAX_FILES_INDEXED = 10;
    private static final String POSTING_LISTS_FILE_PATH = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\postingLists\\postingLists";
    private static final String AUXILIAR_POSTING_FILE = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\postingLists\\aux";


    private List<String> indexedDocuments;
    @Autowired
    private FileReader fileReader;

    @Autowired
    @Qualifier("postingListFileReader")
    private PostingListReader postingListReader;
    @Autowired
    @Qualifier("postingListFileWriter")
    private PostingListWriter postingListWriter;

    private Map<String, VocabularySlot> vocabulary;
    private List<ModifiedToken> modifiedPostingLists;

    private Logger logger;

    public IndexationEngine(){
        documentsRootDirecroty = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\documentos";
        vocabulary = Collections.synchronizedMap(new Hashtable<>());
        logger = LoggerFactory.getLogger(IndexationEngine.class);
        //Aca se debe cargar la lista de documentos indexados
        indexedDocuments = new ArrayList<>();
    }



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
                    while (fileReader.hasNextToRead()) {

                        String token = fileReader.readNextWord();
                        VocabularySlot vocabularySlot = vocabulary.getOrDefault(token, new VocabularySlot());
                        ModifiedToken modifiedToken = new ModifiedToken();


                        if (vocabularySlot.existInVocabulary()) {

                            if (!vocabularySlot.postingListIsLoaded()) {
                                //logger.info("El token existe en el vocabulario pero la lista se debe cargar!");
                                postingListReader.loadPostingList(vocabularySlot, POSTING_LISTS_FILE_PATH);
                                modifiedToken.setOriginalListSize(vocabularySlot.getPostingList().size());
                                modifiedToken.setStartOfListIndex(vocabularySlot.getPostingListStartIndex());
                                //logger.info("Se cargó la lista");
                            }
                            if(!vocabularySlot.incrementPostingSlotForDocument(document.getAbsolutePath())){
                                //logger.info("Primera vez que se carga el token en el documento");
                                vocabularySlot.addPostingSlot(document.getAbsolutePath());
                            }
                        }else{
                            //logger.info("El token no existe en el vocabulario");
                            vocabularySlot.setToken(token);
                            vocabularySlot.addPostingSlot(document.getAbsolutePath());
                            vocabulary.put(token, vocabularySlot);
                            //logger.info("Se cargó el token al vocabulario");
                        }

                        modifiedToken.setToken(vocabularySlot.getToken());
                        this.addModifiedToken(modifiedToken);
                        /*Fin del procesamiento de un documento*/
                    }
                    /*Lo único que queda para completar el índice es la escritura en disco*/
                    logger.info("File processing finished");
                    logger.info("========================");
                    if(!indexedDocuments.contains(document.getAbsolutePath())){
                        indexedDocuments.add(document.getAbsolutePath());
                    }

                    /*PASAR TODOS LOS TOKENS A MINUSCULA!!!!!!!*/

                    ++filesIndexed;
                    if(filesIndexed == MAX_FILES_INDEXED){

                        postingListWriter.writeAndClean(vocabulary, modifiedPostingLists,
                                                POSTING_LISTS_FILE_PATH, AUXILIAR_POSTING_FILE);
                        modifiedPostingLists = null;
                        //Aca va la escritura del archivo parcial
                        filesIndexed = 0;
                    }
                    break;

                }

                postingListWriter.writeAndClean(vocabulary, modifiedPostingLists,
                                                        POSTING_LISTS_FILE_PATH, AUXILIAR_POSTING_FILE);

                /*Cada vez que grabe los archivos en el disco tengo que limpiar todas las listas
                * de posteo de memoria y la lista de tokens modificados, todo eso dentro de esta misma clase*/

                /*Tambien tengo que hacer el proceso de escritura una vez que haya terminado de procesar
                * todos los documentos*/
            }catch (IOException e){
                logger.error("Ocurrio un error: " + e.getMessage());
            }
        }
        Date fin = new Date();
        logger.info("===============================");
        logger.info("================================");
        logger.info("Tiempo que demoró en procesar 50 archivos:" + (fin.getTime() - inicio.getTime()));
    }

    private boolean isIndexed(File file){
        return indexedDocuments.contains(file.getAbsolutePath());
    }

    private void addModifiedToken(ModifiedToken modifiedToken){
        if(modifiedPostingLists == null) modifiedPostingLists = new ArrayList<>();

        if(!modifiedPostingLists.contains(modifiedToken)){
            modifiedPostingLists.add(modifiedToken);
            modifiedPostingLists.sort(modifiedToken.getStartIndexComparator());
        }
    }


    //<editor-fold desc="PSEUTO TESTS">
    public boolean testFileRead() throws IOException {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        File file = new File(documentsRootDirecroty+"\\00ws110.txt");
        Map<String, Integer> fileWords = Collections.synchronizedMap(new Hashtable<>());
        fileReader.openFile(file.getAbsolutePath());
        while(fileReader.hasNextToRead()){
            String key = fileReader.readNextWord();
            Integer value = fileWords.getOrDefault(key, 0);
            fileWords.put(key, value+1);
        }
        fileReader.closeFile();
        logger.info("Cantidad de palabras distintas: " + fileWords.size());
        return fileWords.containsKey("Copyright");

    }

    public String testFileDetection(){

        File file = new File(documentsRootDirecroty);
        StringBuffer stringBuffer = new StringBuffer("");
        if(file.exists() && file.isDirectory()){
            String[] archivos = file.list();
            for(String archivo: archivos){

                stringBuffer.append("\n"+ archivo);
            }
            return stringBuffer.toString();
        }
        return "No existe el diretorio";
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS AND SETTERS">
    public String getDocumentsRootDirecroty() {
        return documentsRootDirecroty;
    }

    public void setDocumentsRootDirecroty(String documentsRootDirecroty) {
        this.documentsRootDirecroty = documentsRootDirecroty;
    }

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

    public List<ModifiedToken> getModifiedPostingLists() {
        return modifiedPostingLists;
    }

    public void setModifiedPostingLists(List<ModifiedToken> modifiedPostingLists) {
        this.modifiedPostingLists = modifiedPostingLists;
    }
    //</editor-fold>
}

