package tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListReader;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.PostingSlotRepository;
import tpi.dlc.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("postingListDBReader")
public class PostingListDBReader implements PostingListReader {

    @Autowired
    private PostingSlotRepository postingSlotRepository;
    @Autowired
    @Qualifier("postingSlotMaper")
    private EntityMapper<PosteoEntity, PostingSlot> entityEntityMapper;

    @Override
    public void loadPostingList(VocabularySlot vocabularySlot){
        List<PosteoEntity> posteos = postingSlotRepository.findByIdTerminoOrderByTfDesc(vocabularySlot.getToken());
        List<PostingSlot> postingSlots = entityEntityMapper.mapAllFromEntity(posteos);
        vocabularySlot.setPostingList(postingSlots);

    }
}
