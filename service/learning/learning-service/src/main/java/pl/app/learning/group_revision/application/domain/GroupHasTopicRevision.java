package pl.app.learning.group_revision.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.model.BaseRevisionEntity;
import pl.app.learning.group.application.domain.GroupHasTopic;

import java.util.UUID;

@EntityAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_topic_revision")
public class GroupHasTopicRevision extends BaseRevisionEntity<GroupHasTopic, UUID, GroupHasTopicRevision, UUID> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private GroupRevision group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "topic_id", nullable = false, updatable = false))
    })
    private AggregateId topic;
}

