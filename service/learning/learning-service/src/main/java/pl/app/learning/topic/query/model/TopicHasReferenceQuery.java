package pl.app.learning.topic.query.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.learning.reference.query.ReferenceQuery;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_has_reference")
public class TopicHasReferenceQuery extends BaseJpaAuditDomainEntity<TopicHasReferenceQuery> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private TopicQuery topic;
    @ManyToOne(optional = false)
    @JoinColumn(name = "reference_id", nullable = false, updatable = false)
    private ReferenceQuery reference;

}

