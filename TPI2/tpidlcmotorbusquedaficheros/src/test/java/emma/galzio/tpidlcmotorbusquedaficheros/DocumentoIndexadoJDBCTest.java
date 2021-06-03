package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoDao;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoDaoJdbcTest;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class DocumentoIndexadoJDBCTest {
    
    @Autowired
    private DocumentoIndexadoDao documentoIndexadoDao;

    /*
    @Test
    @Rollback(value = false)
    public void testDocumentoIndexadoDaoJdbcInsert(){

        boolean result = documentoIndexadoDaoJdbcTest.insertDocumento("unaUrlPrueba");

        
    }*/

    //@Test
    @Rollback(value = false)
    public void testBatchUpdateDocumentoDao(){

        RandomString randomString = new RandomString(20);

        List<DocumentoIndexadoEntity> documentoIndexadoEntityList = new ArrayList<>();
        for(int i = 0; i<300; i++){
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(randomString.nextString());
            documentoIndexadoEntityList.add(documentoIndexadoEntity);
        }
        documentoIndexadoDao.insertBatch(documentoIndexadoEntityList);

    }
}
