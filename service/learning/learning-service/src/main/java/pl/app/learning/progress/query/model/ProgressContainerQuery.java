package pl.app.learning.progress.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_progress_container")
public class ProgressContainerQuery extends BaseAuditEntity<ProgressContainerQuery, UUID> {
    @Id
    private UUID id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "container")
    private final Set<ProgressQuery> progresses = new LinkedHashSet<>();

    @Column(name = "domain_object_id")
    private UUID domainObject;
    @Column(name = "domain_object_type")
    private DomainObjectType domainObjectType;
}
