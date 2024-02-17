package pl.app.learning.chapter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.chapter.model.ChapterEntitySnapshot;

import java.util.UUID;

@Repository
public interface ChapterVersionRepository extends
        JpaRepository<ChapterEntitySnapshot, UUID>{
}
