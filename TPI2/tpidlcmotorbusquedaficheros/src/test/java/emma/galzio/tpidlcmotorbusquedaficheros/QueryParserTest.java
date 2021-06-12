package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import org.junit.jupiter.api.Test;
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

    @Test
    public void queryParserTest(){

        //String query = "But I know. For example, the word \"can't\" should";
        //Map<String, VocabularySlot> parsedQuery = indexationEngine.indexQuery(query);

        //assertThat(parsedQuery.size()).isEqualTo(4);
        //assertThat(parsedQuery.containsKey("perro")).isTrue();
        //Integer count = documentoIndexadoRepository.countAll();
        //System.out.println("Cant documentos: " + count);



    }

}
