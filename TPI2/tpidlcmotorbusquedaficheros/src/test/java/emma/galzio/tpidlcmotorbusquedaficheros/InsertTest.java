package emma.galzio.tpidlcmotorbusquedaficheros;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntityId;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.PostingSlotRepository;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.VocabularyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InsertTest {

    @Autowired
    private PostingSlotRepository postingSlotRepository;
    @Autowired
    private DocumentoIndexadoRepository documentoIndexadoRepository;
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testPostingSlotInsert(){

        List<DocumentoIndexadoEntity> documentos = new ArrayList<>();
        List<PosteoEntity> posteos = new ArrayList<>();
        List<VocabularyEntity> vocabulary = new ArrayList<>();

        DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
        documentoIndexadoEntity.setUrl("D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\TPI\\documentos\\test\\8lssm10u.txt");
        documentos.add(documentoIndexadoEntity);
//______________________________________________________
        VocabularyEntity vocabularyEntity1 = new VocabularyEntity();
        vocabularyEntity1.setToken("caca");
        vocabularyEntity1.setMaxTf(1);
        vocabularyEntity1.setNr(1);

        vocabulary.add(vocabularyEntity1);
//_______________________________________________________________
        PosteoEntityId posteoEntityId1 = new PosteoEntityId();
        posteoEntityId1.setTermino(vocabularyEntity1.getToken());
        posteoEntityId1.setDocumento(documentoIndexadoEntity.getUrl());

        PosteoEntity posteoEntity1 = new PosteoEntity();
        posteoEntity1.setId(posteoEntityId1);
        posteoEntity1.setTf(4);
        //posteoEntity1.setVocabularyEntity(vocabularyEntity1);
        //posteoEntity1.setDocumento(documentoIndexadoEntity);

        posteos.add(posteoEntity1);
        //posteoEntity.setVocabularyEntity(vocabularyEntity);
        //________________________________________________________________

        VocabularyEntity vocabularyEntity2 = this.createVocabularyEntity2();
        PosteoEntity posteoEntity2 = this.createPosteoEntity2(vocabularyEntity2, documentoIndexadoEntity);
        vocabulary.add(vocabularyEntity2);
        posteos.add(posteoEntity2);



        documentoIndexadoRepository.saveAll(documentos);
        documentoIndexadoRepository.flush();
        vocabularyRepository.saveAll(vocabulary);
        vocabularyRepository.flush();
        postingSlotRepository.saveAll(posteos);
        postingSlotRepository.flush();



    }

    private PosteoEntity createPosteoEntity2(VocabularyEntity vocabularyEntity2, DocumentoIndexadoEntity documentoIndexadoEntity) {
        PosteoEntity posteoEntity = new PosteoEntity();
        PosteoEntityId posteoEntityId = new PosteoEntityId();
        posteoEntityId.setTermino(vocabularyEntity2.getToken());
        posteoEntityId.setDocumento(documentoIndexadoEntity.getUrl());
        posteoEntity.setId(posteoEntityId);
        posteoEntity.setTf(1);
        //posteoEntity.setVocabularyEntity(vocabularyEntity2);
        return posteoEntity;

    }

    private VocabularyEntity createVocabularyEntity2(){
        VocabularyEntity vocabularyEntity = new VocabularyEntity();
        vocabularyEntity.setToken("lola");
        vocabularyEntity.setNr(1);
        vocabularyEntity.setMaxTf(1);
        return vocabularyEntity;
    }




}
