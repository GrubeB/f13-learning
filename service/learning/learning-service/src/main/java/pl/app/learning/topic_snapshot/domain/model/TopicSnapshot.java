package pl.app.learning.topic_snapshot.domain.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicStatus;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_topic_snapshot")
public class TopicSnapshot extends BaseJpaSnapshotDomainEntity<Topic, TopicSnapshot> {
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<TopicHasCategorySnapshot> categories = new LinkedHashSet<>();

    public void setCategories(Set<TopicHasCategorySnapshot> categories) {
        if (this.categories != categories) {
            this.categories.forEach(e -> e.setTopic(null));
            this.categories.clear();
            categories.forEach(this::addCategory);
        }
    }

    public void addCategory(TopicHasCategorySnapshot category) {
        this.categories.add(category);
        category.setTopic(this);
        category.setSnapshotNumber(this.snapshotNumber);
    }
}

