package pl.app.learning.comment.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReplyCommand implements
        Serializable {
    private UUID parentCommentId;
    private UUID userId;
    private String content;
}