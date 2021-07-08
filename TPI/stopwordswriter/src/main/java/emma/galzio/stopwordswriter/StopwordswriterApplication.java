package emma.galzio.stopwordswriter;

import emma.galzio.stopwordswriter.persistence.entity.StopWordEntity;
import emma.galzio.stopwordswriter.persistence.repository.StopWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class StopwordswriterApplication implements CommandLineRunner {

    @Autowired
    private StopWordRepository stopWordRepository;

    public static void main(String[] args) {
        SpringApplication.run(StopwordswriterApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Hola Mundo");
        String path = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\stopwords.txt";
        Scanner scanner = new Scanner(new FileInputStream(new File(path)));
        List<StopWordEntity> stopWordEntityList = new LinkedList<>();

        while(scanner.hasNext()){
            String token = scanner.next();
            StopWordEntity stopWordEntity = new StopWordEntity();
            stopWordEntity.setToken(token);
            stopWordEntityList.add(stopWordEntity);
        }
        stopWordRepository.saveAll(stopWordEntityList);


    }
}
