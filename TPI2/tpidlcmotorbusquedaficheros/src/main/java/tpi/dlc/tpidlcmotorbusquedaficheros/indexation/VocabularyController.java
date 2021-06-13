package tpi.dlc.tpidlcmotorbusquedaficheros.indexation;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.StopWordEntity;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.StopWordRepository;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.VocabularyRepository;
import tpi.dlc.tpidlcmotorbusquedaficheros.utils.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VocabularyController {

    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private EntityMapper<VocabularyEntity, VocabularySlot> vocabularyMaper;
    @Autowired
    private StopWordRepository stopWordRepository;

    @Autowired
    private Map<String, VocabularySlot> vocabulary;

    //El vocabulario se carga de esta forma porque es un bean singleton
    public void loadVocabulary(){
        List<VocabularyEntity> vocabularyEntities = vocabularyRepository.findAll();
        List<VocabularySlot> vocabularySlotList = vocabularyMaper.mapAllFromEntity(vocabularyEntities);

        vocabularySlotList.forEach(vocabularySlot -> vocabulary.put(vocabularySlot.getToken(), vocabularySlot));

    }

    public HashSet<String> loadStopWords(){

        List<StopWordEntity> stopWordEntityList = stopWordRepository.findAll();
        HashSet<String> stopWords = new HashSet<>();
        for(StopWordEntity stopWordEntity : stopWordEntityList){
            stopWords.add(stopWordEntity.getToken());
        }
        return stopWords;

    }
}
