package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingSlotRepository extends JpaRepository<PosteoEntity, PosteoEntityId> {

    //List<PosteoEntity> findByIdTermino(String termino);

    List<PosteoEntity> findByPosteoEntityIdTerminoOrderByTfDesc(String termino);

}
