package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.comment.application.port.in.command.CreateCommentCommand;
import pl.app.learning.comment.application.port.in.command.CreateReplyCommand;
import pl.app.learning.comment.application.port.in.command.DeleteCommandCommand;
import pl.app.learning.comment.query.CommentQueryService;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(TopicCommentController.resourcePath)
@RequiredArgsConstructor
public class TopicCommentController {
    public static final String resourceName = "comments";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final CommandGateway gateway;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<CommentDto> create(@PathVariable UUID topicId, @RequestBody CreateCommentCommand command) {
        command.setDomainObjectId(topicId);
        command.setDomainObjectType(DomainObjectType.TOPIC);
        UUID commentId = gateway.send(command);
        return ResponseEntity
                .ok(commentQueryService.fetchById(commentId, CommentDto.class));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID topicId, @PathVariable UUID commentId) {
        gateway.send(new DeleteCommandCommand(commentId));
        return ResponseEntity
                .accepted()
                .build();
    }
    @PostMapping("/{parentCommentId}/comments")
    public ResponseEntity<CommentDto> createReplay(@PathVariable UUID topicId, @PathVariable UUID parentCommentId, @RequestBody CreateReplyCommand command) {
        command.setParentCommentId(parentCommentId);
        UUID commentId = gateway.send(command);
        return ResponseEntity
                .ok(commentQueryService.fetchById(commentId, CommentDto.class));
    }
    @DeleteMapping("/{parentCommentId}/comments/{commentId}")
    public ResponseEntity<Void> deleteReplay(@PathVariable UUID topicId, @PathVariable UUID commentId) {
        gateway.send(new DeleteCommandCommand(commentId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
