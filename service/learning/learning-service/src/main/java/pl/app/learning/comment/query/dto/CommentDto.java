package pl.app.learning.comment.query.dto;

import lombok.Data;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class CommentDto implements
        Serializable {
    private UUID id;
    private String content;
    private UUID userId;
    private Set<CommentDto> comments;
    private VotingDto voting;

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
}