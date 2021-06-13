package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository;

import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface PostingSlotRepository extends JpaRepository<PosteoEntity, PosteoEntityId> {

    //List<PosteoEntity> findByIdTermino(String termino);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends PosteoEntity> S save(S s);

    List<PosteoEntity> findByIdTerminoOrderByTfDesc(String termino);

}
