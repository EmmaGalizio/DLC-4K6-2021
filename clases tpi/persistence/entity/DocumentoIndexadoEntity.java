package emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "documentoindexado")
public class DocumentoIndexadoEntity {

    @Id
    @Column(name = "url")
    private String url;

    @Column(name = "otro")
    private String otro;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }
}
