package pl.app.learning.topic_revision.query;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.topic.query.TopicQuery;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_revision")
public class TopicRevisionQuery extends BaseAuditEntity<TopicRevisionQuery, UUID> {
    @Id
    private UUID id;
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

