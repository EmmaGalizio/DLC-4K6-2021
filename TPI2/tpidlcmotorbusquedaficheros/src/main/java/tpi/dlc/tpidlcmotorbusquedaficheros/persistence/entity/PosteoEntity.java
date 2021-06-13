package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "posteo")
public class PosteoEntity {

    @EmbeddedId
    private PosteoEntityId id;
    @Column(name = "tf")
    private Integer tf;

    //@MapsId("documento")
    //@ManyToOne
    //@JoinColumn(name = "documento", referencedColumnName = "url")
    //private DocumentoIndexadoEntity documento;
    //@MapsId("termino")
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "termino", referencedColumnName = "token")
    //private VocabularyEntity vocabularyEntity;

    public PosteoEntityId getId() {
        return id;
    }

    public void setId(PosteoEntityId id) {
        this.id = id;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }
/*
    public DocumentoIndexadoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoIndexadoEntity documento) {
        this.documento = documento;
    }

    public VocabularyEntity getVocabularyEntity() {
        return vocabularyEntity;
    }

    public void setVocabularyEntity(VocabularyEntity vocabularyEntity) {
        this.vocabularyEntity = vocabularyEntity;
    }*/
}
