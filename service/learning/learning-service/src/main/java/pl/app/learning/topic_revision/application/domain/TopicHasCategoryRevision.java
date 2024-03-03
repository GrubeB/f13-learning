package pl.app.learning.topic_revision.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.model.BaseRevisionEntity;
import pl.app.learning.topic.application.domain.TopicHasCategory;

import java.util.UUID;

@EntityAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "t_topic_has_category_revision")
public class TopicHasCategoryRevision extends BaseRevisionEntity<TopicHasCategory, UUID, TopicHasCategoryRevision, UUID> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private TopicRevision topic;
}

