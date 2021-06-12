package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.api.dto.DocumentResult;
import emma.galzio.tpidlcmotorbusquedaficheros.searchEngine.SearchEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SearchEngineTest {

    @Autowired
    private SearchEngine searchEngine;

    @Test
    public void searchTest(){

        String query = "quixote";
        List<DocumentResult> documentResultList = searchEngine.search(query, 10);
        for(DocumentResult documentResult : documentResultList){
            System.out.println(documentResult);
        }
    }

}
