package pl.app.learning.comment.query.dto;

import lombok.Data;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class CommentContainerDto implements
        Serializable {
    private Set<CommentDto> comments;

    private UUID domainObject;
    private DomainObjectType domainObjectType;
}