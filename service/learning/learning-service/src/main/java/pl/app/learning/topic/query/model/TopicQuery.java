package pl.app.learning.topic.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.topic.application.domain.TopicStatus;
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
@Table(name = "t_topic")
public class TopicQuery extends BaseAuditEntity<TopicQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TopicHasCategoryQuery> categories = new LinkedHashSet<>();
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TopicHasReferenceQuery> references = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "comment_container_id")
    private CommentContainerQuery comment;
}

