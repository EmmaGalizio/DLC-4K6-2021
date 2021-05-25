package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListWriter;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.*;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.*;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.*;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service("postingListDBWriter")
public class PostingListDBWriter implements PostingListWriter {

    @Autowired
    @Qualifier("postingSlotMaper")
    private EntityMapper<PosteoEntity, PostingSlot> postingSlotMapper;
    @Autowired
    @Qualifier("vocabularyMapper")
    private EntityMapper<VocabularyEntity, VocabularySlot> vocabularyMapper;

    @Autowired
    private PostingSlotRepository postingSlotRepository;
    @Autowired
    private DocumentoIndexadoRepository documentoIndexadoRepository;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int BATCH_SIZE;
    private Logger logger;

    private Map<String,DocumentoIndexadoEntity> documentosEntitiesMap;
    //private Map<String, VocabularyEntity> vocabularyEntityMap;
    //private Map<PosteoEntityId, PosteoEntity> posteoEntityMap;

    @PostConstruct
    public void postConstruct(){

        documentosEntitiesMap = new Hashtable<>();
        //vocabularyEntityMap = new Hashtable<>();
        //posteoEntityMap = new Hashtable<>();
        logger = LoggerFactory.getLogger(this.getClass());

    }

    @Override
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException {
        if(modifiedTokens == null || modifiedTokens.isEmpty()) return;

        List<VocabularyEntity> vocabularyEntityList = new ArrayList<>();
        String [] modifiedTokensArray = modifiedTokens.keySet().toArray(new String[0]);
        List<PosteoEntity> posteoEntities = new ArrayList<>();

        for(String modifiedToken: modifiedTokensArray){

            VocabularySlot vocabularySlot = vocabulary.get(modifiedToken);
            vocabularySlot.sortPostingList();
            vocabularySlot.setMaxTfValue();

            VocabularyEntity vocabularyEntity = vocabularyMapper.mapToEntity(vocabularySlot);
            
            for(PostingSlot postingSlot : vocabularySlot.getPostingList()){
                PosteoEntity posteoEntity = postingSlotMapper.mapToEntity(postingSlot);
                posteoEntities.add(posteoEntity);
                this.updateDocumentosMap(postingSlot.getDocumentUrl());
            }
            vocabularyEntityList.add(vocabularyEntity);

            vocabularySlot.clearPostingList();
        }
        documentoIndexadoRepository.saveAll(documentosEntitiesMap.values());
        documentoIndexadoRepository.flush();
        vocabularyRepository.saveAll(vocabularyEntityList);
        vocabularyRepository.flush();
        postingSlotRepository.saveAll(posteoEntities);
        postingSlotRepository.flush();
    }

    /*

    @Override
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException {

        if(modifiedTokens == null || modifiedTokens.isEmpty()) return;

        List<VocabularyEntity> vocabularyEntityList = new ArrayList<>();
        String [] modifiedTokensArray = modifiedTokens.keySet().toArray(new String[0]);

        for(String modifiedToken: modifiedTokensArray){

            VocabularySlot vocabularySlot = vocabulary.get(modifiedToken);
            vocabularySlot.sortPostingList();
            vocabularySlot.setMaxTfValue();

            VocabularyEntity vocabularyEntity = vocabularyMapper.mapToEntity(vocabularySlot);
            List<PosteoEntity> posteoEntities = new ArrayList<>();
            for(PostingSlot postingSlot : vocabularySlot.getPostingList()){
                PosteoEntity posteoEntity = postingSlotMapper.mapToEntity(postingSlot);
                posteoEntity.setVocabularyEntity(vocabularyEntity);
                posteoEntities.add(posteoEntity);
                this.updateDocumentosMap(postingSlot.getDocumentUrl());
            }
            vocabularyEntity.setPosteos(posteoEntities);
            vocabularyEntityList.add(vocabularyEntity);
            vocabularySlot.clearPostingList();
        }
        documentoIndexadoRepository.saveAll(documentosEntitiesMap.values());
        documentoIndexadoRepository.flush();
        vocabularyRepository.saveAll(vocabularyEntityList);
        vocabularyRepository.flush();
    }
    */

    private void updateDocumentosMap(String url){
        if(documentosEntitiesMap.get(url) == null){
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(url);
            documentosEntitiesMap.put(url, documentoIndexadoEntity);
        }
    }

    /*
    @Override
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException {

        if(modifiedTokens == null) return;
        int modifiedTokensCount = modifiedTokens.size();
        int batchedPostingList = 0;

        List<PosteoEntity> posteoEntities = new ArrayList<>();
        String [] modifiedTokensArray = modifiedTokens.keySet().toArray(new String[modifiedTokens.size()]);
        VocabularySlot vocabularySlot;

        try {

            for(String token: modifiedTokensArray){
                vocabularySlot = vocabulary.get(token);
                vocabularySlot.sortPostingList();
                vocabularySlot.setMaxTfValue();

                //Me aseguro de que se utilicen solo los objetos nuevos que son necesarios, si hay un objeto creado y managed
                //entonces uso ese
                this.updateVocabularyEntities(vocabularySlot);

                if(batchedPostingList + vocabularySlot.getNr() <= BATCH_SIZE){

                    List<PosteoEntity> auxEntities = postingSlotMapper.mapAllToEntity(vocabularySlot.getPostingList());
                    auxEntities = this.validateAuxEntities(auxEntities);
                    posteoEntities.addAll(auxEntities);
                    batchedPostingList += auxEntities.size();

                } else{

                }

            }

        } catch (Exception e){
            logger.info("Ha ocurrido un error con al procesar el lote");
            logger.info(e.getMessage());
            e.printStackTrace();


        }

    }

    private List<PosteoEntity> validateAuxEntities(List<PosteoEntity> auxEntities) {

        return auxEntities.stream().map(entity ->
            (posteoEntityMap.get(entity.getId()) != null)
                    ? this.updatePosteoEntity(posteoEntityMap.get(entity.getId()), entity)
                    : entity).collect(Collectors.toList());

    }

    private PosteoEntity updatePosteoEntity(PosteoEntity oldEntity, PosteoEntity newEntity) {
        oldEntity.setTf(newEntity.getTf());
        oldEntity.setVocabularyEntity(vocabularyEntityMap.get(oldEntity.getId().getTermino()));
        return oldEntity;
    }

    private void updateVocabularyEntities(VocabularySlot vocabularySlot){

        if(vocabularyEntityMap.get(vocabularySlot.getToken()) != null){
            VocabularyEntity aux = vocabularyEntityMap.get(vocabularySlot.getToken());
            aux.setNr(vocabularySlot.getNr());
            aux.setMaxTf(vocabularySlot.getMaxTf());
        } else{
            VocabularyEntity aux = vocabularyMapper.mapToEntity(vocabularySlot);
            vocabularyEntityMap.put(aux.getToken(), aux);
        }

    }

 */

    /*
    //En todo el metodo se supone que las listas de posteo no van a tener un tamaño mayor a 1000, porque hay 593 archivos solamente

    @Override
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException {

        if(modifiedTokens == null) return;

        if(logger == null) logger = LoggerFactory.getLogger(this.getClass());
        int modifiedTokensCount = modifiedTokens.size();
        int batchedPostingLists = 0;
        int nextBatchStartIndex;

        List<PosteoEntity> posteoEntities = new ArrayList<>();

        String[] modifiedTokensArray = modifiedTokens.keySet().toArray(new String[modifiedTokensCount]);
        VocabularySlot vocabularySlot = null;

        try {
            for (String modifiedToken : modifiedTokensArray) {


                vocabularySlot = vocabulary.get(modifiedToken);

                if ((vocabularySlot.getNr() + batchedPostingLists) <= BATCH_SIZE) {

                    batchedPostingLists += vocabularySlot.getNr();
                    vocabularySlot.sortPostingList();
                    vocabularySlot.setMaxTf(vocabularySlot.getPostingList().get(0).getTokenFrecuency());

                    this.updateVocabularyEntityMap(vocabularyEntityMap, vocabularySlot);
                    List<PosteoEntity> auxEntity = postingSlotMapper.mapAllToEntity(vocabularySlot.getPostingList());
                    //Me aseguro de que si hay entities repetidas (de otra invocacion al metodo o lote), no creo dos objetos, sino que actualizo el viejo
                    //de paso seteo el VocabularyEntity para cada lista de posteos
                    auxEntity = this.mapPosteoEntityList(posteoEntityMap, auxEntity);

                    posteoEntities.addAll(auxEntity);
                    for (PosteoEntity entity : auxEntity) {
                        this.updateDocumentoEntities(documentosEntities, entity.getId().getDocumento());
                    }

                } else {

                    int postingListsSubListSize = BATCH_SIZE - batchedPostingLists;
                    if(postingListsSubListSize > 0) {
                        List<PosteoEntity> auxEntity = postingSlotMapper
                                .mapAllToEntity(vocabularySlot.getPostingList().subList(0, postingListsSubListSize - 1));
                        //Me aseguro de que si hay entities repetidas, no creo dos objetos, sino que actualizo el viejo
                        //de paso seteo el VocabularyEntity para cada lista de posteos
                        auxEntity = this.mapPosteoEntityList(posteoEntityMap, auxEntity);
                        posteoEntities.addAll(auxEntity);

                        for (PosteoEntity entity : auxEntity) {
                            this.updateDocumentoEntities(documentosEntities, entity.getId().getDocumento());
                        }
                    }

                    documentoIndexadoRepository.saveAll(documentosEntities.values());
                    documentoIndexadoRepository.flush();
                    postingSlotRepository.saveAll(posteoEntities);
                    postingSlotRepository.flush();
                    posteoEntities = new ArrayList<>();

                    if (postingListsSubListSize < vocabularySlot.getNr()) {
                        posteoEntities.addAll(postingSlotMapper
                                .mapAllToEntity(vocabularySlot.getPostingList().subList(postingListsSubListSize, vocabularySlot.getNr() - 1)));
                    }
                    batchedPostingLists = posteoEntities.size();
                    //documentosEntities = new Hashtable<>();
                }
                vocabularySlot.clearPostingList();
            }
            if (posteoEntities != null && !posteoEntities.isEmpty()) {
                postingSlotRepository.saveAll(posteoEntities);
                //postingSlotRepository.flush();
            }
        }catch(Exception e){
           // VocabularySlot testSlot = vocabulary.get("arraché");
            //List<PosteoEntity> auxPostingList = postingSlotMapper.mapAllToEntity(testSlot.getPostingList());
            //System.out.println(testSlot.getToken());
            //System.out.println("Error en el token: arraché");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    private void updateDocumentoEntities(Map<String, DocumentoIndexadoEntity> documentos, String url){

        if(documentos.get(url) == null){
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(url);
            documentos.put(url, documentoIndexadoEntity);
        }

    }

     */

    /**
     * Este metodo verifica, para cada elemento de posteoEntities si ya existe un objeto tipo Entity
     * dentro del map posteoEntityMap, de ser cierto, significa que el objeto ya está manejado y por lo tanto
     * está conectado con la base de datos, de no ser así directamente se usa el objeto nuevo
     * @
     * */
    /*
    private List<PosteoEntity> mapPosteoEntityList(Map<PosteoEntityId, PosteoEntity> posteoEntityMap
                                                                ,List<PosteoEntity> posteoEntities){

        return posteoEntities.stream().map(posteo ->
                        (posteoEntityMap.get(posteo.getId())!= null) ?
                                this.updatePosteoEntity(posteoEntityMap.get(posteo.getId()), posteo)
                                : this.updatePosteoEntity(posteo, posteo)).collect(Collectors.toList());
    }

    private void updateVocabularyEntityMap(Map<String, VocabularyEntity> vocabularyEntityMap, VocabularySlot vocabularySlot){
        if(vocabularyEntityMap.get(vocabularySlot.getToken()) == null){
            vocabularyEntityMap.put(vocabularySlot.getToken(), vocabularyMapper.mapToEntity(vocabularySlot));
        }else{
            VocabularyEntity entity = vocabularyEntityMap.get(vocabularySlot.getToken());
            entity.setMaxTf(vocabularySlot.getMaxTf());
            entity.setNr(vocabularySlot.getNr());
        }
    }

    private PosteoEntity updatePosteoEntity(PosteoEntity oldEntity, PosteoEntity newEntity){
        oldEntity.setTf(newEntity.getTf());
        oldEntity.setVocabularyEntity(vocabularyEntityMap.get(oldEntity.getId().getTermino()));

        return oldEntity;
    }

     */

    public void cleanAll(){
        documentosEntitiesMap.clear();
        //posteoEntityMap.clear();
        //vocabularyEntityMap.clear();
    }


}
