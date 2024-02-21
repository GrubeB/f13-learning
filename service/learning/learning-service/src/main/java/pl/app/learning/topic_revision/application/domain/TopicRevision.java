package pl.app.learning.topic_revision.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.learning.topic.query.TopicQuery;

@AggregateRootAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_revision")
public class TopicRevision extends BaseJpaAuditDomainAggregateRoot<TopicRevision> {
    @Column(name = "topic_revision_name")
    private String name;
    @Column(name = "topic_revision_content")
    private String content;

    @OneToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "topic_revision_id", referencedColumnName = "id", nullable = false)
    })
    private TopicQuery topic;

}

