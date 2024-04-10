package pl.app.learning.reference.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReferenceCommand implements
        Serializable {
    private UUID domainObjectId;
    private DomainObjectType domainObjectType;
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
}