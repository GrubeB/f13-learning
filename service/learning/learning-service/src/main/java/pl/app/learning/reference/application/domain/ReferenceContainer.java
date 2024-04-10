package pl.app.learning.reference.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_reference_container")
public class ReferenceContainer extends BaseJpaAuditDomainAggregateRoot<ReferenceContainer> {

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Reference> references = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "domain_object_id", nullable = false, updatable = false))
    })
    private AggregateId domainObject;
    @Column(name = "domain_object_type", nullable = false, updatable = false)
    private DomainObjectType domainObjectType;

    public ReferenceContainer(AggregateId domainObject, DomainObjectType domainObjectType) {
        super();
        this.domainObject = domainObject;
        this.domainObjectType = domainObjectType;
    }

    @SuppressWarnings("unused")
    protected ReferenceContainer() {
        super();
    }

    public void addReference(Reference reference) {
        reference.setContainer(this);
        this.references.add(reference);
    }

    public void removeReference(AggregateId reference) {
        this.references.removeIf(e -> Objects.equals(e.getAggregateId(), reference));
    }

    public void updateReference(AggregateId reference, String author, String title, LocalDate publicationDate, String description, String link) {
        getReference(reference)
                .ifPresent(e -> e.updateReferenceInfo(author, title, publicationDate, description, link));
    }

    public Optional<Reference> getReference(AggregateId reference) {
        return this.references.stream()
                .filter(e -> Objects.equals(reference, e.getAggregateId()))
                .findAny();
    }
}

