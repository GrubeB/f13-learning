package pl.app.learning.reference.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.time.LocalDate;
@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReferenceCommand implements
        Serializable {
    private String author;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private String link;
}