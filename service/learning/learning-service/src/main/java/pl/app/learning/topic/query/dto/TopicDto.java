package pl.app.learning.topic.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.application.domain.TopicStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private TopicStatus status;
    private List<SimpleCategoryDto> categories;
    private List<ReferenceDto> references;
}
