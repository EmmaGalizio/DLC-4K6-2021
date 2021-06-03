package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListWriter;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.*;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


@Service("postingListJDBCWriter")
public class PostingListJDBCWriter implements PostingListWriter{

    @Autowired
    @Qualifier("postingSlotMaper")
    private EntityMapper<PosteoEntity, PostingSlot> postingSlotMapper;
    @Autowired
    @Qualifier("vocabularyMapper")
    private EntityMapper<VocabularyEntity, VocabularySlot> vocabularyMapper;

    @Autowired
    private PosteoDao posteoDao;
    @Autowired
    private DocumentoIndexadoDao documentoIndexadoDao;
    @Autowired
    private VocabularyDao vocabularyDao;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int BATCH_SIZE;
    private Logger logger;

    private Map<String, DocumentoIndexadoEntity> documentosEntitiesMap;
    private Map<String, DocumentoIndexadoEntity> persistedDocuments;

    @PostConstruct
    public void postConstruct(){

        logger = LoggerFactory.getLogger(this.getClass());
        persistedDocuments = new Hashtable<>();
    }

    @Override
    //@Transactional
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens){

        if(modifiedTokens == null || modifiedTokens.isEmpty()) return;

        documentosEntitiesMap = new Hashtable<>();

        logger.info("Initiating domain-entity mapping");

        List<VocabularyEntity> newVocabularyEntityList = new LinkedList<>();
        List<VocabularyEntity> modifiedVocabularyEntityList = new LinkedList<>();

        Collection<ModifiedToken> modifiedTokensCollection = modifiedTokens.values();
        List<PosteoEntity> newPosteoEntities = new LinkedList<>();
        List<PosteoEntity> modifiedPosteoEntities = new LinkedList<>();

        for(ModifiedToken modifiedToken: modifiedTokensCollection){

            VocabularySlot vocabularySlot = vocabulary.get(modifiedToken.getToken());
            vocabularySlot.sortPostingList();
            vocabularySlot.setMaxTfValue();

            VocabularyEntity vocabularyEntity = vocabularyMapper.mapToEntity(vocabularySlot);

            for(PostingSlot postingSlot : vocabularySlot.getPostingList()){
                PosteoEntity posteoEntity = postingSlotMapper.mapToEntity(postingSlot);
                if(postingSlot.isNew()){
                    newPosteoEntities.add(posteoEntity);
                } else{
                    if(postingSlot.isNeedsUpdate()) { //En teoria no haría falta, debería estar siempre vacía
                        modifiedPosteoEntities.add(posteoEntity);
                    }
                }
                this.updateDocumentosMap(postingSlot.getDocumentUrl());
            }
            if(modifiedToken.isNewToken()){
                newVocabularyEntityList.add(vocabularyEntity);
            } else{
                modifiedVocabularyEntityList.add(vocabularyEntity);
            }
            vocabularySlot.clearPostingList();
        }
        logger.info("Domain-entity mapping finished");
        logger.info("Initiating batch inserts");


        List<DocumentoIndexadoEntity> documentoIndexadoEntityList = new LinkedList<>(documentosEntitiesMap.values());
        logger.info("Modified PostingSlots list size: " + modifiedPosteoEntities.size());
        this.<DocumentoIndexadoEntity>insertBatch(documentoIndexadoDao, documentoIndexadoEntityList);
        logger.info("Documents insert finished");
        this.<VocabularyEntity>insertBatch(vocabularyDao, newVocabularyEntityList);
        this.<VocabularyEntity>updateBatch(vocabularyDao, modifiedVocabularyEntityList);
        logger.info("Tokens inserts and updates finished");
        this.<PosteoEntity>insertBatch(posteoDao, newPosteoEntities);
        this.<PosteoEntity>updateBatch(posteoDao, modifiedPosteoEntities);
        logger.info("Posting lists inserts and updates finished");

        logger.info("All entities inserted successfully");
        //this.cleanAll();

    }

    private void updateDocumentosMap(String url){
        if(documentosEntitiesMap.get(url) == null && persistedDocuments.get(url) == null){
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(url);
            documentosEntitiesMap.put(url, documentoIndexadoEntity);
            persistedDocuments.put(url, documentoIndexadoEntity);
        }
    }

    private <T> void insertBatch(BaseDao<T> dao, List<T> elements){
        int index = 0;
        while(index < elements.size()){
            if(index + BATCH_SIZE <= elements.size()){

                dao.insertBatch(elements.subList(index, index + BATCH_SIZE));
                index = index + BATCH_SIZE;
            } else{
                int currentBatchSize = elements.size() - index;
                int toIndex = currentBatchSize + index;
                //toIndex es exclusivo, por eso no hace falta restarle 1
                dao.insertBatch(elements.subList(index, toIndex));
                index = index + currentBatchSize;
            }
        }
    }
    private <T> void updateBatch(BaseDao<T> dao, List<T> elements){
        int index = 0;
        while(index < elements.size()){
            if(index + BATCH_SIZE <= elements.size()){
                dao.updateBatch(elements.subList(index, index + BATCH_SIZE));
                index = index + BATCH_SIZE;
            } else{
                int currentBatchSize = elements.size() - index;
                int toIndex = index + currentBatchSize;
                dao.updateBatch(elements.subList(index, toIndex));
                index = index + currentBatchSize;
            }
        }
    }

    public void cleanAll(){
        documentosEntitiesMap.clear();
        //posteoEntityMap.clear();
        //vocabularyEntityMap.clear();
    }
}
