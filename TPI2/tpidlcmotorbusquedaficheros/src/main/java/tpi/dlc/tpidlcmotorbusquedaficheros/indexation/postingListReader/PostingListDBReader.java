package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListReader;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.PostingSlotRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
