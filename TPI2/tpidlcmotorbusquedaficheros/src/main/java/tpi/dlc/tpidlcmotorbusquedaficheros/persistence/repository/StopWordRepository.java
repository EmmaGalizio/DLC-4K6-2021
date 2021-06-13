package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository;

import tpi.dlc.tpidlcmotorbusquedaficheros.persistence.entity.StopWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopWordRepository extends JpaRepository<StopWordEntity, String> {
}
