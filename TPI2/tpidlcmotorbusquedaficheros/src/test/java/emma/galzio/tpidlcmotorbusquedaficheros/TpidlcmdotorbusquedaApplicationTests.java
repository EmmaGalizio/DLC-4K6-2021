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
    public void modifiedTokenComparatorTest(){
        ModifiedToken modifiedToken1 = new ModifiedToken();
        modifiedToken1.setStartOfListIndex(3L);

        ModifiedToken modifiedToken2 = new ModifiedToken();
        modifiedToken2.setStartOfListIndex(5L);

        Comparator<ModifiedToken> comparator = modifiedToken1.getStartIndexComparator();
        int result = comparator.compare(modifiedToken1, modifiedToken2);
        System.out.println("Resiltado: " + result);
        assertThat(result < 0).isTrue();

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

    //@Test
    public void testModifiedTokensSorter(){

        Random random = new Random();
        ModifiedToken[] modifiedTokens = new ModifiedToken[1000000];
        for(int i = 0; i < modifiedTokens.length; i++){
            ModifiedToken modifiedToken = new ModifiedToken();
            modifiedToken.setToken("Token " + i);
            modifiedToken.setStartOfListIndex((long)(Math.random()*1000));
            modifiedTokens[i] = modifiedToken;
        }


        //ModifiedTokensSorter modifiedTokensSorter = new ModifiedTokensSorter();
        //modifiedTokensSorter.sort(modifiedTokens);
        Arrays.sort(modifiedTokens, modifiedTokens[0].getStartIndexComparator());
        //Arrays.parallelSort(modifiedTokens);

        for(int i = 0; i< modifiedTokens.length -1; i+=10000){
            System.out.println(modifiedTokens[i].getToken() + "  " + modifiedTokens[i].getStartOfListIndex());
        }


    }

}
