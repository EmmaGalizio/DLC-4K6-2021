package emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("vocabularyMapper")
public class VocabularyMapper implements EntityMapper<VocabularyEntity, VocabularySlot>{

    @Autowired
    private EntityMapper<PosteoEntity, PostingSlot> postingSlotEntityMapper;

    @Override
    public VocabularyEntity mapToEntity(VocabularySlot model) {
        VocabularyEntity vocabularyEntity = new VocabularyEntity();
        vocabularyEntity.setToken(model.getToken());
        vocabularyEntity.setMaxTf(model.getMaxTf());
        vocabularyEntity.setNr(model.getNr());

        /*
        if(model.getPostingList() != null){
            List<PosteoEntity> posteoEntities = new ArrayList<>();
            for(PostingSlot postingSlot: model.getPostingList()){
                PosteoEntity posteoEntity = postingSlotEntityMapper.mapToEntity(postingSlot);
                posteoEntity.setVocabularyEntity(vocabularyEntity);
                posteoEntities.add(posteoEntity);
            }
            vocabularyEntity.setPosteos(posteoEntities);
        }*/

        return vocabularyEntity;
    }

    @Override
    public List<VocabularyEntity> mapAllToEntity(List<VocabularySlot> models) {
        return models.stream().map(vocabularySlot -> this.mapToEntity(vocabularySlot)).collect(Collectors.toList());
    }

    @Override
    public VocabularySlot mapFromEntity(VocabularyEntity entity) {
        VocabularySlot vocabularySlot = new VocabularySlot();
        vocabularySlot.setToken(entity.getToken());
        vocabularySlot.setMaxTf(entity.getMaxTf());
        vocabularySlot.setNr(entity.getNr());
        return vocabularySlot;
    }

    @Override
    public List<VocabularySlot> mapAllFromEntity(List<VocabularyEntity> entities) {
        return entities.stream().map(vocabularyEntity -> this.mapFromEntity(vocabularyEntity)).collect(Collectors.toList());
    }
}
