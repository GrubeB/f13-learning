package pl.app.learning.voting.query.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_voting")
public class VotingQuery extends BaseAuditEntity<VotingQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "likes_number")
    private Long likesNumber;
    @Column(name = "dislikes_number")
    private Long dislikesNumber;
    @Column(name = "domain_object_id")
    private UUID domainObject;
    @Column(name = "domain_object_type")
    private DomainObjectType domainObjectType;
}
