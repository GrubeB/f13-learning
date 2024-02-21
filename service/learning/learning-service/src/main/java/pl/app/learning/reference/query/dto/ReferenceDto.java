package pl.app.learning.reference.query.dto;

import lombok.Data;
import pl.app.learning.reference.application.domain.ReferenceStatus;

import java.io.Serializable;
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
    private Long likesNumber;
    private Long dislikesNumber;
}