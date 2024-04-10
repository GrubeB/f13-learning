package pl.app.learning.comment.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_comment_container")
public class CommentContainerQuery extends BaseAuditEntity<CommentContainerQuery, UUID> {
    @Id
    private UUID id;
    @OneToMany(mappedBy = "container")
    private Set<CommentQuery> comments = new LinkedHashSet<>();

    @Column(name = "domain_object_id")
    private UUID domainObject;
    @Column(name = "domain_object_type")
    private DomainObjectType domainObjectType;

}
