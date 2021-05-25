package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.utils.FileReader;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.StringNormalizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ReaderTests {

    @Autowired
    @Qualifier("utfFileReader")
    private FileReader utfFileReader;

    //@Test
    public void testUtfFileReader(){
        String path = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\test-parser.txt";
        utfFileReader.openFile(path);
        Map<String, Integer> tokens = new HashMap<>();
        while(utfFileReader.isReady()){
            String token = utfFileReader.readNextWord().toLowerCase();
            Integer count = tokens.getOrDefault(token,0);
            count++;
            tokens.put(token, count);
        }

        assertThat(tokens.get("project's")).isNotNull();
        assertThat(tokens.get("project's")).isEqualTo(1);
        assertThat(tokens.get("other")).isNotNull();

    }

    @Test
    public void stringNormalizerTest(){
        String accents 	= "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ";
        String expected	= "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,C,c,A,a,O,o";

        String accents2	= "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
        String expected2	= "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";

        String accents3	= "Gisele Bündchen da Conceição e Silva foi batizada assim em homenagem à sua conterrânea de Horizontina, RS.";
        String expected3	= "Gisele Bundchen da Conceicao e Silva foi batizada assim em homenagem a sua conterranea de Horizontina, RS.";

        String accents4	= "/Users/rponte/arquivos-portalfcm/Eletron/Atualização_Diária-1.23.40.exe";
        String expected4	= "/Users/rponte/arquivos-portalfcm/Eletron/Atualizacao_Diaria-1.23.40.exe";

        StringNormalizer stringNormalizer = new StringNormalizer();

        assertThat(stringNormalizer.unaccent(accents)).isEqualTo(expected);
        assertThat(stringNormalizer.unaccent(accents2)).isEqualTo(expected2);
        assertThat(stringNormalizer.unaccent(accents3)).isEqualTo(expected3);
        assertThat(stringNormalizer.unaccent(accents4)).isEqualTo(expected4);

    }

}
