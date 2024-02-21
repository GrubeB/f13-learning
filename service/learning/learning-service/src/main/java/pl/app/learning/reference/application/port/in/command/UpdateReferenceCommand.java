package pl.app.learning.reference.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReferenceCommand implements
        Serializable {
    private UUID referenceId;
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
}