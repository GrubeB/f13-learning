package pl.app.learning.voting.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.*;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_voting")
public class Voting extends BaseJpaAuditDomainAggregateRoot<Voting> {
    @Column(name = "likes_number")
    private Long likesNumber;
    @Column(name = "dislikes_number")
    private Long dislikesNumber;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "voting", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserVote> votes = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "domain_object_id", nullable = false, updatable = false))
    })
    private AggregateId domainObject;
    @Column(name = "domain_object_type", nullable = false, updatable = false)
    private DomainObjectType domainObjectType;

    public Voting(AggregateId domainObject, DomainObjectType domainObjectType) {
        super();
        this.domainObject = domainObject;
        this.domainObjectType = domainObjectType;
        this.likesNumber = 0L;
        this.dislikesNumber = 0L;
    }

    @SuppressWarnings("unused")
    protected Voting() {
        super();
    }

    public boolean addLike(AggregateId userId) {
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

    public boolean removeLike(AggregateId userId) {
        if (!this.votes.removeIf(vote -> Objects.equals(vote.getUserId(), userId.getId()))) {
            return false;
        }
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    public boolean removeLikeAndDislike(AggregateId userId) {
        if (!this.votes.removeIf(vote -> Objects.equals(vote.getUserId(), userId.getId()))
                && !this.votes.removeIf(vote -> Objects.equals(vote.getUserId(), userId.getId()))) {
            return false;
        }
        calculateLikesNumberAndDislikesNumber();
        return true;
    }

    public boolean addDislike(AggregateId userId) {
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

    public boolean removeDislike(AggregateId userId) {
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

