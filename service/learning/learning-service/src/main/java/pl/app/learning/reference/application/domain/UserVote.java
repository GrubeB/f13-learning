package pl.app.learning.reference.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.UUID;

@EntityAnnotation
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_reference_voting_vote")
public class UserVote extends BaseJpaAuditDomainEntity<UserVote> {
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "vote_type", nullable = false)
    private UserVoteType type;

    @ManyToOne
    @JoinColumn(name = "reference_voting_id")
    private ReferenceVoting referenceVoting;
}

