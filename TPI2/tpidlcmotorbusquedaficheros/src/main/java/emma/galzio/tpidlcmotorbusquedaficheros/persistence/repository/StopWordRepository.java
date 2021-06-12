package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.StopWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopWordRepository extends JpaRepository<StopWordEntity, String> {
}
