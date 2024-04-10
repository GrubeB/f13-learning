package pl.app.learning.topic.query.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import pl.app.common.model.audit.AuditColumnName;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.comment.query.dto.CommentContainerDto;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.reference.query.dto.ReferenceContainerDto;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.application.domain.TopicStatus;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private TopicStatus status;
    private List<SimpleCategoryDto> categories;
    private CommentContainerDto comment;
    private VotingDto voting;
    private ReferenceContainerDto reference;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
}
