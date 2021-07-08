package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository;

import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface DocumentoIndexadoRepository extends JpaRepository<DocumentoIndexadoEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends DocumentoIndexadoEntity> S save(S s);

    @Query("SELECT COUNT(d.url) FROM DocumentoIndexadoEntity d")
    Integer countAll();

}
