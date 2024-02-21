package pl.app.learning.category.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.application.domain.CategoryStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryDto implements
        Serializable {
    private UUID id;
    private String name;
    private String description;
    private CategoryStatus status;
    private List<SimpleCategoryDto> parents;
    private List<SimpleCategoryDto> children;

    public CategoryDto(UUID id, String name, String description, CategoryStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
}