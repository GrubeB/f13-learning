package pl.app.learning.group.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.group.application.domain.GroupStatus;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.query.dto.TopicDto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGroupDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private GroupStatus status;
    private List<SimpleCategoryDto> categories;
}
