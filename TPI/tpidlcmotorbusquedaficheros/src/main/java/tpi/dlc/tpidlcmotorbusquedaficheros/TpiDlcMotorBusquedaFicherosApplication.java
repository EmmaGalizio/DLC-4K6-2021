package tpi.dlc.tpidlcmotorbusquedaficheros;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.io.File;

@SpringBootApplication
@Profile("!test")
public class TpiDlcMotorBusquedaFicherosApplication implements CommandLineRunner {

    @Autowired
    private IndexationEngine indexationEngine;
    @Value("${tpi.dlc.documentsResourceDirectory}")
    private String path;

    public static void main(String[] args) {
        SpringApplication.run(TpiDlcMotorBusquedaFicherosApplication.class, args);
    }

    @Override
    public void run(String... args){

        File file = new File(path);
        try{
            indexationEngine.indexFiles(file);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
