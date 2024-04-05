package pl.app.learning.comment.query.dto;

import jakarta.persistence.*;
import lombok.Data;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.comment.query.model.CommentQuery;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.query.dto.VotingDto;
import pl.app.learning.voting.query.model.VotingQuery;

import java.io.Serializable;
import java.util.LinkedHashSet;
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
}