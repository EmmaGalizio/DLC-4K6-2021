package tpi.dlc.tpidlcmotorbusquedaficheros;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListReader.PostingListDBReader;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListReader.PostingListReader;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository.PostingSlotRepository;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TpidlcmdotorbusquedaApplicationTests {

    @Autowired
    private IndexationEngine indexationEngine;
    @Autowired
    private PostingSlotRepository postingSlotRepository;

    @Value("${tpi.dlc.documentsResourceDirectory}")
    private String path;


    //@Test
    public void testPostingListReadOrder(){
        List<PosteoEntity> posteoEntityList = postingSlotRepository.findByIdTerminoOrderByTfDesc("cervantes");
        posteoEntityList.forEach(posteoEntity -> {
            System.out.println("URL: " + posteoEntity.getId().getDocumento());
            System.out.println("Tf: " + posteoEntity.getTf());
        });
    }


    //@Test
    //@Order(1)
    @Rollback(value = false)
    public void directoryIndexationTest(){

        File file = new File(path);
        try{
            indexationEngine.indexFiles(file);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    //@Test
    //@Order(2)
    //@Rollback(value = false)
    public void singleFileIndexationTest(){

        try{
            indexationEngine.indexFiles(path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //@Test
    //@Rollback(value = false)
    public void indexationTest(){
        System.out.println("Prueba de indexacion de 50 ficheros");
        //indexationEngine.indexTest();
    }


}
