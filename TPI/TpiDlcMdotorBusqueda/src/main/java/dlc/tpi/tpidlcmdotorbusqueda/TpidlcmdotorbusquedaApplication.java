package dlc.tpi.tpidlcmdotorbusqueda;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.IndexationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpidlcmdotorbusquedaApplication implements CommandLineRunner {

    @Autowired
    private IndexationEngine indexationEngine;

    public static void main(String[] args) {
        SpringApplication.run(TpidlcmdotorbusquedaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
