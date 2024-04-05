package pl.app.learning.reference.query.dto;

import lombok.Data;
import pl.app.learning.reference.application.domain.ReferenceStatus;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReferenceDto implements
        Serializable {
    private UUID id;
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
    private ReferenceStatus status;
    private VotingDto voting;

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
}