package pl.app.learning.reference.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;

import java.time.LocalDate;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_reference")
public class Reference extends BaseJpaAuditDomainAggregateRoot<Reference> {
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
    private ReferenceStatus status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "reference_voting_id"))
    })
    private AggregateId voting;

    @SuppressWarnings("unused")
    protected Reference() {
        super();
    }

    public Reference(String author, String title, LocalDate publicationDate, String description, String link) {
        this.author = author;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
        this.link = link;
        this.status = ReferenceStatus.UNVERIFIED;
    }

    public void changeStatus(ReferenceStatus status) {
        this.status = status;
    }

    public void updateReferenceInfo(String author, String title, LocalDate publicationDate, String description, String link) {
        this.author = author;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
        this.link = link;
    }
    public void setVoting(AggregateId voting){
        this.voting = voting;
    }
}

