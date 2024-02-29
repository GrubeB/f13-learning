package pl.app.learning.group.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_group")
public class GroupHasGroupQuery extends BaseAuditEntity<GroupHasGroupQuery, UUID> {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id_1", nullable = false, updatable = false)
    private GroupQuery group;

    @OneToOne
    @JoinColumn(name = "group_id_2")
    private GroupQuery childGroup;
}

