package pl.app.learning.topic.query;


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
import pl.app.learning.category.query.CategoryQuery;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_has_category")
public class TopicHasCategoryQuery extends BaseJpaAuditDomainEntity<TopicHasCategoryQuery> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    private TopicQuery topic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false, updatable = false)
    private CategoryQuery category;
}

