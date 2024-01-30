package pl.app.learning.chapter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.chapter.model.ChapterEntity;

import java.util.UUID;

@Repository
public interface ChapterRepository extends
        JpaRepository<ChapterEntity, UUID>,
        JpaSpecificationExecutor<ChapterEntity> {
}
