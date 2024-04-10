package pl.app.learning.comment.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EntityAnnotation
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_comment")
public class Comment extends BaseJpaAuditDomainEntity<Comment> {
    @Column(nullable = false, length = 8_000)
    private String content;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "user_id", nullable = false, updatable = false))
    })
    private AggregateId user;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "voting_id"))
    })
    private AggregateId voting;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "container_id", referencedColumnName = "id")
    })
    private CommentContainer container;

    public Comment(String content, AggregateId user) {
        this.content = content;
        this.user = user;
        this.voting = null;
        this.parentComment = null;
        this.container = null;
    }

    public Set<Comment> getAllComments() {
        return Stream.concat(Stream.of(this), this.comments.stream().map(Comment::getAllComments).flatMap(Set::stream))
                .collect(Collectors.toSet());
    }

    public void deleteComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void addComment(Comment comment) {
        comment.setParentComment(this);
        this.comments.add(comment);
    }
}

