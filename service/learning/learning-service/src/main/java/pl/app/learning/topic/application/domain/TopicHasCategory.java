package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.topic_revision.application.domain.TopicHasCategoryRevision;
import pl.app.learning.topic_snapshot.domain.model.TopicHasCategorySnapshot;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_topic_has_category")
public class TopicHasCategory extends BaseJpaAuditDomainEntity<TopicHasCategory> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private Topic topic;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @SuppressWarnings("unused")
    protected TopicHasCategory() {
        super();
    }

    public TopicHasCategory(Topic topic, AggregateId category) {
        this.topic = topic;
        this.category = category;
    }

    public TopicHasCategory revertSnapshot(Topic topic, TopicHasCategorySnapshot snapshot) {
        this.entityId = snapshot.getSnapshotOwnerId();
        this.topic = topic;
        this.category = snapshot.getCategory();
        return this;
    }

    public TopicHasCategory mergeRevision(Topic topic, TopicHasCategoryRevision revision) {
        this.entityId = revision.getRevisionOwnerId();
        this.topic = topic;
        this.category = revision.getCategory();
        return this;
    }
}

