package pl.app.learning.topic.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.progress.query.dto.ProgressDto;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.application.domain.TopicStatus;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;
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
    private VotingDto voting;
    private Set<CommentDto> comments;
    private Set<ReferenceDto> references;
    private Set<ProgressDto> progresses;

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
}
