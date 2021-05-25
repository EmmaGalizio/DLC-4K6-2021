package emma.galzio.tpidlcmotorbusquedaficheros.indexation;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.VocabularyRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VocabularyController {

    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private EntityMapper<VocabularyEntity, VocabularySlot> vocabularyMaper;

    @Autowired
    private Map<String, VocabularySlot> vocabulary;

    public void loadVocabulary(){
        List<VocabularyEntity> vocabularyEntities = vocabularyRepository.findAll();
        List<VocabularySlot> vocabularySlotList = vocabularyMaper.mapAllFromEntity(vocabularyEntities);

        vocabularySlotList.forEach(vocabularySlot -> vocabulary.put(vocabularySlot.getToken(), vocabularySlot));
        //vocabulary = vocabularySlotList.stream()
          //      .collect(Collectors.toMap(VocabularySlot::getToken, vocSlot -> vocSlot));

    }
}
