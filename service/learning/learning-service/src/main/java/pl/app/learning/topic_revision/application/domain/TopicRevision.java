package pl.app.learning.topic_revision.application.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.model.BaseRevisionEntity;
import pl.app.learning.topic.application.domain.Topic;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@AggregateRootAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "t_topic_revision")
public class TopicRevision extends BaseRevisionEntity<Topic, UUID, TopicRevision, UUID> {
    @Column(name = "topic_revision_name")
    private String name;
    @Column(name = "topic_revision_content")
    private String content;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<TopicHasCategoryRevision> categories = new LinkedHashSet<>();

    public void setCategories(Set<TopicHasCategoryRevision> categories) {
        if (this.categories != categories) {
            this.categories.forEach(e -> e.setTopic(null));
            this.categories.clear();
            categories.forEach(this::addCategory);
        }
    }

    public void addCategory(TopicHasCategoryRevision category) {
        this.categories.add(category);
        category.setTopic(this);
    }
}

