package pl.app.learning.reference.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.comment.query.model.CommentQuery;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_reference_container")
public class ReferenceContainerQuery extends BaseAuditEntity<ReferenceContainerQuery, UUID> {
    @Id
    private UUID id;
    @OneToMany(mappedBy = "container")
    private Set<ReferenceQuery> references = new LinkedHashSet<>();

    @Column(name = "domain_object_id")
    private UUID domainObject;
    @Column(name = "domain_object_type")
    private DomainObjectType domainObjectType;

}
