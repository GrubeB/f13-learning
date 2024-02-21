package pl.app.learning.reference.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_reference_voting")
public class ReferenceVotingQuery extends BaseAuditEntity<ReferenceQuery, UUID> {
    @Id
    private UUID id;
    private Long likesNumber;
    private Long dislikesNumber;
}
