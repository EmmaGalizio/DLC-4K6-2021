package emma.galzio.stopwordswriter.persistence.repository;

import emma.galzio.stopwordswriter.persistence.entity.StopWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopWordRepository extends JpaRepository<StopWordEntity, String> {
}
