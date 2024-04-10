package pl.app.learning.voting.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.voting.application.domain.UserVoteType;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_voting_vote")
public class UserVoteQuery extends BaseAuditEntity<UserVoteQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "vote_type")
    private UserVoteType type;

    @ManyToOne
    @JoinColumn(name = "voting_id")
    private VotingQuery voting;
}
