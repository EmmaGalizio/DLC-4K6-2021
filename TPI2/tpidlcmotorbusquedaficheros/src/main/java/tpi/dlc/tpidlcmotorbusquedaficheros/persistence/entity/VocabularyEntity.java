package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vocabulary")
public class VocabularyEntity {

    @Id
    private String token;
    @Column(name = "maxTf")
    private Integer maxTf;
    @Column(name = "nr")
    private Integer nr;
    //@OneToMany(mappedBy = "vocabularyEntity", cascade = CascadeType.ALL)
    //private List<PosteoEntity> posteos;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMaxTf() {
        return maxTf;
    }

    public void setMaxTf(Integer maxTf) {
        this.maxTf = maxTf;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }


    //public List<PosteoEntity> getPosteos() {
      //  return posteos;
    //}

    //public void setPosteos(List<PosteoEntity> posteos) {
      //  this.posteos = posteos;
    //}
}
