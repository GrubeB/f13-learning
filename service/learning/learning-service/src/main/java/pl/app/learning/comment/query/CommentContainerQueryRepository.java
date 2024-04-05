package pl.app.learning.comment.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.comment.query.model.CommentContainerQuery;

import java.util.UUID;

@Repository
public interface CommentContainerQueryRepository extends
        JpaRepository<CommentContainerQuery, UUID>,
        JpaSpecificationExecutor<CommentContainerQuery> {
}
