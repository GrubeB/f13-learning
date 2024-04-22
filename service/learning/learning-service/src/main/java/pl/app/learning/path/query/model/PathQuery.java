package pl.app.learning.path.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.path.application.domain.PathStatus;
import pl.app.learning.progress.query.model.ProgressContainerQuery;
import pl.app.learning.voting.query.model.VotingQuery;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_path")
public class PathQuery extends BaseAuditEntity<PathQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "path_name")
    private String name;
    @Column(name = "path_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "path_status")
    private PathStatus status;
    @OneToMany(mappedBy = "path", fetch = FetchType.EAGER)
    private Set<PathHasCategoryQuery> categories = new LinkedHashSet<>();
    @OneToMany(mappedBy = "path", fetch = FetchType.EAGER)
    private Set<PathItemQuery> items = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "comment_container_id")
    private CommentContainerQuery comment;

    @OneToOne
    @JoinColumn(name = "voting_id")
    private VotingQuery voting;

    @OneToOne
    @JoinColumn(name = "progress_container_id")
    private ProgressContainerQuery progress;
}

