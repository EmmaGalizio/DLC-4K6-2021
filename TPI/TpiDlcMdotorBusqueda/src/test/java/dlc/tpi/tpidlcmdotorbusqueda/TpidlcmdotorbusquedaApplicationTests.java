package dlc.tpi.tpidlcmdotorbusqueda;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.FileReader;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.IndexationEngine;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.ModifiedToken;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

@SpringBootTest
class TpidlcmdotorbusquedaApplicationTests {

    @Autowired
    private IndexationEngine indexationEngine;
    @Autowired
    private FileReader fileReader;

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
    public void readFileTokens(){
        Date inicio = new Date();

        Map<String, Integer> tokens = new Hashtable<>();
        File rootDirectory = new File("D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\documentos\\test");
        File[] documents = rootDirectory.listFiles();

        for(File document: documents){
            System.out.println("Leyendo archivo: " + document.getName());

            fileReader.openFile(document.getAbsolutePath());
            while(fileReader.hasNextToRead()){
                String token = fileReader.readNextWord();
                token.toLowerCase();
                Integer count = tokens.get(token);
                if(count == null) count = 0;
                count++;
                tokens.put(token, count);
            }
            try{
                fileReader.closeFile();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        Date fin = new Date();
        System.out.println("======================");
        System.out.println("Tiempo de procesamiento de 50 archivos: " + (inicio.getTime() - fin.getTime()));
        System.out.println("======================");
        System.out.println("Cantidad de tokens diferentes: " + tokens.size());

    }

    @Test
    public void indexationTest(){
        System.out.println("Prueba de indexacion de 50 ficheros");
        indexationEngine.indexTest();
    }

}
