package pl.app.learning.group.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.group.application.domain.GroupStatus;
import pl.app.learning.progress.query.model.ProgressContainerQuery;
import pl.app.learning.reference.query.model.ReferenceContainerQuery;
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
@Table(name = "t_group")
public class GroupQuery extends BaseAuditEntity<GroupQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private GroupStatus status;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<GroupHasCategoryQuery> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<GroupHasTopicQuery> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<GroupHasGroupQuery> groups = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "comment_container_id")
    private CommentContainerQuery comment;

    @OneToOne
    @JoinColumn(name = "voting_id")
    private VotingQuery voting;

    @OneToOne
    @JoinColumn(name = "reference_container_id")
    private ReferenceContainerQuery reference;

    @OneToOne
    @JoinColumn(name = "progress_container_id")
    private ProgressContainerQuery progress;
}

