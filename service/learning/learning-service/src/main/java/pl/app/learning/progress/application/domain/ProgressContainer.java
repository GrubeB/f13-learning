package pl.app.learning.progress.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.*;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_progress_container")
public class ProgressContainer extends BaseJpaAuditDomainAggregateRoot<ProgressContainer> {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Progress> progresses = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "domain_object_id", nullable = false, updatable = false))
    })
    private AggregateId domainObject;
    @Column(name = "domain_object_type", nullable = false, updatable = false)
    private DomainObjectType domainObjectType;

    public ProgressContainer(AggregateId domainObject, DomainObjectType domainObjectType) {
        super();
        this.domainObject = domainObject;
        this.domainObjectType = domainObjectType;
    }

    @SuppressWarnings("unused")
    protected ProgressContainer() {
        super();
    }

    public void setUserProgress(UUID userId, ProgressType type) {
        Optional<Progress> progressOptional = getUserProgress(userId);
        if (progressOptional.isPresent()) {
            progressOptional.get().setProgressType(type);
        } else {
            Progress newProgress = new Progress(userId, type, this);
            this.progresses.add(newProgress);
        }
    }

    private Optional<Progress> getUserProgress(UUID userId) {
        return this.progresses.stream()
                .filter(e -> Objects.equals(e.getUserId(), userId))
                .findAny();
    }

}

