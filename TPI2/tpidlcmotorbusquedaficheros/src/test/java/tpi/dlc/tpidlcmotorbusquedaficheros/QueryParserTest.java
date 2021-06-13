package tpi.dlc.tpidlcmotorbusquedaficheros;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class QueryParserTest {

    @Autowired
    private IndexationEngine indexationEngine;
    @Autowired
    private DocumentoIndexadoRepository documentoIndexadoRepository;

    //@Test
    public void queryParserTest(){

        String query = "But I know. For example, the word can't should work!.";
        Map<String, VocabularySlot> parsedQuery = indexationEngine.indexQuery(query);

        assertThat(parsedQuery.size()).isEqualTo(4);
        assertThat(parsedQuery.containsKey("perro")).isTrue();
        Integer count = documentoIndexadoRepository.countAll();
        System.out.println("Cant documentos: " + count);



    }

}
