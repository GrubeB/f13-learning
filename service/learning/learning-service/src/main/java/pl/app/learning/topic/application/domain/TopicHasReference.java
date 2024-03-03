package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_topic_has_reference")
public class TopicHasReference extends BaseJpaAuditDomainEntity<TopicHasReference> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private Topic topic;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "reference_id", nullable = false, updatable = false))
    })
    private AggregateId reference;

    @SuppressWarnings("unused")
    protected TopicHasReference() {
        super();
    }

    public TopicHasReference(Topic topic, AggregateId reference) {
        this.topic = topic;
        this.reference = reference;
    }
}

