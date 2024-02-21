package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.UUID;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_topic_has_category")
public class TopicHasCategory extends BaseJpaAuditDomainEntity<TopicHasCategory> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private Topic topic;

    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @SuppressWarnings("unused")
    protected TopicHasCategory() {
        super();
    }

    public TopicHasCategory(Topic topic, AggregateId category) {
        this.topic = topic;
        this.categoryId = category.getId();
    }
}

