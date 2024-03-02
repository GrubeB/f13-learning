package pl.app.learning.topic_snapshot.domain.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.learning.topic.application.domain.TopicHasCategory;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_topic_has_category_snapshot")
public class TopicHasCategorySnapshot extends BaseJpaSnapshotDomainEntity<TopicHasCategory, TopicHasCategorySnapshot> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private TopicSnapshot topic;

}

