package pl.app.learning.comment.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.learning.comment.application.port.out.CreateCommentVotingPort;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class CommentFactory {
    private final CreateCommentVotingPort createVotingPort;

    public Comment create(String content, AggregateId user) {
        Comment comment = new Comment(content, user);
        AggregateId voting = createVotingPort.createVoting(new AggregateId(comment.getId()));
        comment.setVoting(voting);
        return comment;
    }
}
