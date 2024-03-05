package pl.app.learning.path.query.model;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import pl.app.common.model.BaseAuditEntity;
import pl.app.common.model.Identity;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.path.application.domain.ItemType;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_path_item")
public class PathItemQuery extends BaseAuditEntity<PathItemQuery, UUID> {
    @Id
    private UUID id;

    @Column(name = "item_number")
    private Long number;
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType type;

    @Any(fetch = FetchType.EAGER)
    @AnyKeyJavaClass(UUID.class)
    @AnyDiscriminatorValues({
            @AnyDiscriminatorValue(discriminator = "TOPIC", entity = TopicQuery.class),
            @AnyDiscriminatorValue(discriminator = "GROUP", entity = GroupQuery.class)
    })
    @JoinColumn(name = "entity_id")
    @Column(name = "entity_type")
    private Identity<?> entity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "path_id", nullable = false, updatable = false)
    private PathQuery path;
}
