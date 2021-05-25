package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntityId;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.VocabularyRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class RandomInsertTest {

    @Autowired
    private DocumentoIndexadoRepository documentoIndexadoRepository;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    //@Rollback(value = false)
    @Transactional
    public void randomInserTest(){
        RandomString randomString = new RandomString(150);
        List<DocumentoIndexadoEntity> documentoIndexadoEntities = new ArrayList<>();

        for(int i = 0; i< 1500; i++){
            String random = randomString.nextString();
            DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
            documentoIndexadoEntity.setUrl(random);
            documentoIndexadoEntities.add(documentoIndexadoEntity);
        }

        randomString = new RandomString(50);

        List<VocabularyEntity> vocabulary = new ArrayList<>();
        for(int i = 0; i< 1500; i++){
            String token = randomString.nextString();
            VocabularyEntity vocabularyEntity = new VocabularyEntity();
            vocabularyEntity.setToken(token);
            vocabularyEntity.setMaxTf(4);
            vocabularyEntity.setNr(2);

            PosteoEntityId posteoEntityId = new PosteoEntityId();
            posteoEntityId.setTermino(token);
            posteoEntityId.setDocumento(documentoIndexadoEntities.get(i).getUrl());

            PosteoEntity posteoEntity = new PosteoEntity();
            posteoEntity.setId(posteoEntityId);
            posteoEntity.setTf(4);
            //posteoEntity.setVocabularyEntity(vocabularyEntity);

            List<PosteoEntity> posteos = new ArrayList<>();
            posteos.add(posteoEntity);
            //vocabularyEntity.setPosteos(posteos);
            vocabulary.add(vocabularyEntity);
        }

        Date inicio = new Date();

        documentoIndexadoRepository.saveAll(documentoIndexadoEntities);
        documentoIndexadoRepository.flush();
        vocabularyRepository.saveAll(vocabulary);
        vocabularyRepository.flush();
        Date fin = new Date();
        System.out.println("Tiempo para 2300 inserts: " + (float)(fin.getTime()-inicio.getTime())/1000);

    }



}
