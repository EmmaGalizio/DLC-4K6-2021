package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpiDlcMotorBusquedaFicherosApplication implements CommandLineRunner {

    @Autowired
    private IndexationEngine indexationEngine;

    public static void main(String[] args) {
        SpringApplication.run(TpiDlcMotorBusquedaFicherosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //indexationEngine.indexTest();
    }
}
