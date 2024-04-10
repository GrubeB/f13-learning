package pl.app.learning.comment.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.comment.application.port.in.command.CreateCommentCommand;
import pl.app.learning.comment.application.port.in.command.CreateReplyCommand;
import pl.app.learning.comment.application.port.in.command.DeleteCommentCommand;
import pl.app.learning.comment.application.port.in.command.UpdateCommentCommand;
import pl.app.learning.comment.query.CommentQueryService;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@RestController
@RequestMapping(GroupCommentController.resourcePath)
@RequiredArgsConstructor
public class GroupCommentController {
    public static final String resourceName = "comments";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;

    private final CommandGateway gateway;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<CommentDto> create(@PathVariable UUID groupId, @RequestBody CreateCommentCommand command) {
        command.setDomainObjectId(groupId);
        command.setDomainObjectType(DomainObjectType.GROUP);
        UUID commentId = gateway.send(command);
        return ResponseEntity
                .ok(commentQueryService.fetchById(commentId, CommentDto.class));
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(@PathVariable UUID commentId, @RequestBody UpdateCommentCommand command) {
        command.setCommentId(commentId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID groupId, @PathVariable UUID commentId) {
        gateway.send(new DeleteCommentCommand(commentId));
        return ResponseEntity
                .accepted()
                .build();
    }
    @PostMapping("/{parentCommentId}/comments")
    public ResponseEntity<CommentDto> createReplay(@PathVariable UUID groupId, @PathVariable UUID parentCommentId, @RequestBody CreateReplyCommand command) {
        command.setParentCommentId(parentCommentId);
        UUID commentId = gateway.send(command);
        return ResponseEntity
                .ok(commentQueryService.fetchById(commentId, CommentDto.class));
    }
    @PutMapping("/{parentCommentId}/comments/{commentId}")
    public ResponseEntity<Void> updateReplay(@PathVariable UUID commentId, @RequestBody UpdateCommentCommand command) {
        command.setCommentId(commentId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping("/{parentCommentId}/comments/{commentId}")
    public ResponseEntity<Void> deleteReplay(@PathVariable UUID groupId, @PathVariable UUID commentId) {
        gateway.send(new DeleteCommentCommand(commentId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
