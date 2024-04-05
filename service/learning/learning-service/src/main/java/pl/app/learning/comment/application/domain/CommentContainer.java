package pl.app.learning.comment.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.UserVote;
import pl.app.learning.voting.application.domain.UserVoteType;

import java.util.*;
import java.util.stream.Collectors;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_comment_container")
public class CommentContainer extends BaseJpaAuditDomainAggregateRoot<CommentContainer> {

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "domain_object_id", nullable = false, updatable = false))
    })
    private AggregateId domainObject;
    @Column(name = "domain_object_type", nullable = false, updatable = false)
    private DomainObjectType domainObjectType;

    public CommentContainer(AggregateId domainObject, DomainObjectType domainObjectType) {
        super();
        this.domainObject = domainObject;
        this.domainObjectType = domainObjectType;
    }

    @SuppressWarnings("unused")
    protected CommentContainer() {
        super();
    }

    public void addComment(Comment comment) {
        comment.setContainer(this);
        this.comments.add(comment);
    }
    public void addComment(UUID parentCommentId, Comment comment) {
        getComment(parentCommentId).ifPresent(parentComment -> parentComment.addComment(comment));
    }
    public void updateComment(UUID commentId, String content) {
        getComment(commentId)
                .ifPresent(comment -> comment.setContent(content));
    }

    public void deleteComment(UUID commentId) {
        Optional<Comment> comment = getComment(commentId);
        if(comment.isEmpty()){
            return;
        }
        if(this.comments.remove(comment.get())){
            return;
        }
        Comment parentComment = comment.get().getParentComment();
        if(parentComment != null) {
            parentComment.deleteComment(comment.get());
        }
    }

    public Optional<Comment> getComment(UUID commentId) {
        return getAllComments().stream()
                .filter(c -> Objects.equals(commentId, c.getId()))
                .findAny();
    }
    public Set<Comment> getAllComments(){
        return this.comments.stream()
                .map(Comment::getAllComments)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}

