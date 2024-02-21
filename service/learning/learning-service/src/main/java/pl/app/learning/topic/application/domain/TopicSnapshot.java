package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_topic_snapshot")
public class TopicSnapshot extends BaseJpaSnapshotDomainEntity<Topic, TopicSnapshot> {
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;

    public TopicSnapshot() {
        super();
    }

    public TopicSnapshot(Topic snapshotOwnerId, String name, String content, TopicStatus status) {
        super(snapshotOwnerId);
        this.name = name;
        this.content = content;
        this.status = status;
    }
}

