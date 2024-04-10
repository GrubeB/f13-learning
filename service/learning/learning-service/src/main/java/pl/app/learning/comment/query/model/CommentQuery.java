package pl.app.learning.comment.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.voting.query.model.VotingQuery;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_comment")
public class CommentQuery extends BaseAuditEntity<CommentQuery, UUID> {
    @Id
    private UUID id;
    private String content;
    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(mappedBy = "parentComment")
    private Set<CommentQuery> comments = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "voting_id")
    private VotingQuery voting;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentQuery parentComment;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private CommentContainerQuery container;
}
