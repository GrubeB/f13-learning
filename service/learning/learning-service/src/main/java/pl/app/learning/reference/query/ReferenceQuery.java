package pl.app.learning.reference.query;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.reference.application.domain.ReferenceStatus;
import pl.app.learning.voting.query.model.VotingQuery;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_reference")
public class ReferenceQuery extends BaseAuditEntity<ReferenceQuery, UUID> {
    @Id
    private UUID id;
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
    private ReferenceStatus status;
    @OneToOne
    @JoinColumn(name = "reference_voting_id")
    private VotingQuery referenceVoting;
}

