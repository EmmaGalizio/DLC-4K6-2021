package emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PosteoEntityId implements Serializable {

    @Basic
    @Column(name = "termino")
    private String termino;
    @Basic
    @Column(name = "documento")
    private String documento;


    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
