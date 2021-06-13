package emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "documentoIndexado")
public class DocumentoIndexadoEntity {

    @Id
    @Column(name = "url")
    private String url;

      public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
