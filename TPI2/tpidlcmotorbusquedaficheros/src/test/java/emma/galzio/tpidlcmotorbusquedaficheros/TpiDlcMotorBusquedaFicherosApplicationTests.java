package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TpiDlcMotorBusquedaFicherosApplicationTests {

    @Autowired
    private DocumentoIndexadoRepository repository;

    @Test
    void contextLoads() {
        System.out.println("Carga de contexto!");
    }

    @Transactional
    @Test
    //@Rollback(value = false)
    void documentoRepositorySaveTest(){
        DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
        String url = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\documentos\\00ws110.txt";
        documentoIndexadoEntity.setUrl(url);
        repository.save(documentoIndexadoEntity);
        repository.flush();
        DocumentoIndexadoEntity documentoIndexadoEntity1 = repository.getOne(url);
        assertThat(documentoIndexadoEntity1.getUrl()).isEqualTo(url);
    }

}
