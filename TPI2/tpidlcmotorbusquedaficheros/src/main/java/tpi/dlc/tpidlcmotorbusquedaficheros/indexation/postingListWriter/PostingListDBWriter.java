package tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListWriter;


import tpi.dlc.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.*;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.*;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.*;


import javax.annotation.PostConstruct;
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

    private Map<String, DocumentoIndexadoEntity> documentosEntitiesMap;
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
    @Transactional
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens){
        if(modifiedTokens == null || modifiedTokens.isEmpty()) return;

        logger.info("Initiating domain-entity mapping");

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
        logger.info("Domain-entity mapping finished");
        logger.info("Initiating batch inserts");
        documentoIndexadoRepository.saveAll(documentosEntitiesMap.values());
        documentoIndexadoRepository.flush();
        vocabularyRepository.saveAll(vocabularyEntityList);
        vocabularyRepository.flush();
        postingSlotRepository.saveAll(posteoEntities);
        postingSlotRepository.flush();

        logger.info("All entities inserted successfully");
        this.cleanAll();
    }

    private void updateDocumentosMap(String url){
        if(documentosEntitiesMap.get(url) == null){
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(url);
            documentosEntitiesMap.put(url, documentoIndexadoEntity);
        }
    }

    public void cleanAll(){
        documentosEntitiesMap.clear();
        //posteoEntityMap.clear();
        //vocabularyEntityMap.clear();
    }


}
