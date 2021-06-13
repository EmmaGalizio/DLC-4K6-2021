package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TpidlcmdotorbusquedaApplicationTests {

    @Autowired
    private IndexationEngine indexationEngine;

    @Value("${tpi.dlc.documentsResourceDirectory}")
    private String path;




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