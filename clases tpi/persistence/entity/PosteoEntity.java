package emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.*;

//@Entity
//@Table(name = "posteo")
public class PosteoEntity {

    //@EmbeddedId
    private PosteoEntityId posteoEntityId;

    //@MapsId("termino")
    //@Basic
    //@Column(name="termino")
    private String termino;

    //@MapsId("documento")
    //@ManyToOne
    //@JoinColumn(name = "documento")
    private DocumentoIndexadoEntity documento;

    @Column(name = "tf")
    private Integer tf;

    public PosteoEntityId getPosteoEntityId() {
        return posteoEntityId;
    }

    public void setPosteoEntityId(PosteoEntityId posteoEntityId) {
        this.posteoEntityId = posteoEntityId;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public DocumentoIndexadoEntity getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoIndexadoEntity documento) {
        this.documento = documento;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }
}
