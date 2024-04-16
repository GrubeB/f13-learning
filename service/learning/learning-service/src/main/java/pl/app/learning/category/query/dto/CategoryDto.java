package pl.app.learning.category.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.application.domain.CategoryStatus;
import pl.app.learning.voting.query.dto.VotingDto;

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
    private VotingDto voting;
}