package pl.app.learning.group.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_topic")
public class GroupHasTopicQuery extends BaseAuditEntity<GroupHasTopicQuery, UUID> {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private GroupQuery group;
    @OneToOne
    @JoinColumn(name = "topic_id")
    private TopicQuery topic;
}

