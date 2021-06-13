package emma.galzio.tpidlcmotorbusquedaficheros.searchEngine;

import emma.galzio.tpidlcmotorbusquedaficheros.api.dto.DocumentResult;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IDocumentController;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.VocabularyController;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListReader.PostingListReader;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchEngine {

    @Autowired
    private Map<String, VocabularySlot> vocabulary;
    @Autowired
    private VocabularyController vocabularyController;
    @Autowired
    private IndexationEngine indexationEngine;
    @Autowired
    @Qualifier("postingListDBReader")
    private PostingListReader postingListReader;
    @Autowired
    private IDocumentController documentController;

    @Autowired
    private ServletContext servletContext;

    public SearchEngine() {
    }

    @PostConstruct
    private void init(){
        if(vocabulary.isEmpty()){
            vocabularyController.loadVocabulary();
        }
    }

    public List<DocumentResult> search(String query, int r){

        if(query == null || query.isEmpty()) throw new IllegalArgumentException("Query can't be empty");
        if(r <= 0) r = 10;
        Map<String, VocabularySlot> queryMap = indexationEngine.indexQuery(query);

        List<VocabularySlot> vocabularySlotList = new ArrayList<>();
        Map<String,DocumentResult> documentResultMap = new HashMap<>();

        Integer documentsCount = documentController.countDocuments();

        this.fillVocabularySlotList(vocabularySlotList, queryMap);
        vocabularySlotList.sort(new VocabularySlot().nrComparator());

        for(VocabularySlot vocabularySlot: vocabularySlotList){

            if(!vocabularySlot.postingListIsLoaded()){
                postingListReader.loadPostingList(vocabularySlot);
            }
            Iterator<PostingSlot> postingSlotIterator = vocabularySlot.getPostingSlotIterator();
            int count = 0;
            while(postingSlotIterator.hasNext() && count <= r ){

                PostingSlot postingSlot = postingSlotIterator.next();
                this.updateReulstMap(documentResultMap, postingSlot, vocabularySlot,
                                                documentsCount, queryMap.get(vocabularySlot.getToken()));
                count++;
            }
            vocabularySlot.clearPostingList();
        }
        List<DocumentResult> documentResultList = new ArrayList<>(documentResultMap.values());
        documentResultList.sort(Collections.reverseOrder());

        if(documentResultList.size() >= r) return documentResultList.subList(0, r);
        return  documentResultList;

    }

    private void updateReulstMap(Map<String, DocumentResult> documentResultMap, PostingSlot postingSlot,
                                                        VocabularySlot vocabularySlot, int documentsCount,
                                                        VocabularySlot queryTokenSlot){

        DocumentResult documentResult = documentResultMap.getOrDefault(postingSlot.getDocumentUrl(),
                new DocumentResult());
        documentResult.setUrl(postingSlot.getDocumentUrl());
        documentResult.setName(new File(postingSlot.getDocumentUrl()).getName());
        documentResult.setUri(servletContext.getContextPath() + "/documentos/"+ documentResult.getName());
        double idf = Math.log10(((double)documentsCount/vocabularySlot.getNr()));
        double auxIr = postingSlot.getTokenFrecuency() * idf * queryTokenSlot.getMaxTf();
        documentResult.setIr(documentResult.getIr() + auxIr);
        documentResultMap.put(postingSlot.getDocumentUrl(), documentResult);
    }

    private void fillVocabularySlotList(List<VocabularySlot> vocabularySlotList, Map<String, VocabularySlot> queryMap){

        for(String token: queryMap.keySet()){
            VocabularySlot vocabularySlot = vocabulary.get(token);
            if(vocabularySlot == null) continue;
            vocabularySlotList.add(vocabularySlot);
        }
    }




}
