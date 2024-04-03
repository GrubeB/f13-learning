package pl.app.learning.reference.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.*;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_reference_voting")
public class ReferenceVoting extends BaseJpaAuditDomainEntity<ReferenceVoting> {
    private Long likesNumber;
    private Long dislikesNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "referenceVoting", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserVote> votes = new LinkedHashSet<>();

    public ReferenceVoting() {
        super();
        this.likesNumber = 0L;
        this.dislikesNumber = 0L;
    }

    public boolean addUserLike(AggregateId userId) {
        Optional<UserVote> voteOptional = getUserVote(userId.getId());
        if (voteOptional.isPresent() && UserVoteType.LIKE.equals(voteOptional.get().getType())) {
            return false;
        }
        UserVote vote = voteOptional.orElseGet(() -> {
            UserVote newVote = new UserVote(userId.getId(), UserVoteType.LIKE, this);
            votes.add(newVote);
            return newVote;
        });
        vote.setType(UserVoteType.LIKE);
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    public boolean removeUserLike(AggregateId userId) {
        if (!this.votes.removeIf(vote -> Objects.equals(vote.getUserId(), userId.getId()))) {
            return false;
        }
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    public boolean addUserDislike(AggregateId userId) {
        Optional<UserVote> voteOptional = getUserVote(userId.getId());
        if (voteOptional.isPresent() && UserVoteType.DISLIKE.equals(voteOptional.get().getType())) {
            return false;
        }
        UserVote vote = voteOptional.orElseGet(() -> {
            UserVote newVote = new UserVote(userId.getId(), UserVoteType.DISLIKE, this);
            votes.add(newVote);
            return newVote;
        });
        vote.setType(UserVoteType.DISLIKE);
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    public boolean removeUserDislike(AggregateId userId) {
        if (!this.votes.removeIf(vote -> Objects.equals(vote.getUserId(), userId.getId()))) {
            return false;
        }
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    private Optional<UserVote> getUserVote(UUID userId) {
        return this.votes.stream()
                .filter(vote -> Objects.equals(vote.getUserId(), userId))
                .findAny();
    }
    private void calculateLikesNumberAndDislikesNumber() {
        this.likesNumber = votes.stream().filter(v -> UserVoteType.LIKE.equals(v.getType())).count();
        this.dislikesNumber = votes.stream().filter(v -> UserVoteType.DISLIKE.equals(v.getType())).count();
    }

}

