package pl.app.learning.category.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.application.domain.CategoryStatus;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCategoryDto implements
        Serializable {
    private UUID id;
    private String name;
    private String description;
    private CategoryStatus status;
}