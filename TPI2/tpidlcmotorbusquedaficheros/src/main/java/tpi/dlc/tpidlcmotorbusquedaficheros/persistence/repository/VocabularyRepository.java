package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository;

import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface VocabularyRepository extends JpaRepository<VocabularyEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    <S extends VocabularyEntity> S save(S s);
}
