package pl.app.learning.path.query.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.annotations.AnyDiscriminatorValues;
import org.hibernate.annotations.AnyKeyJavaClass;
import pl.app.common.model.Identity;
import pl.app.learning.category.query.dto.CategoryDto;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.application.domain.ItemType;
import pl.app.learning.path.application.domain.PathStatus;
import pl.app.learning.path.query.model.PathHasCategoryQuery;
import pl.app.learning.path.query.model.PathItemQuery;
import pl.app.learning.path.query.model.PathQuery;
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.application.domain.TopicStatus;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicQuery;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private PathStatus status;
    private Set<SimpleCategoryDto> categories;
    private Set<PathItemTopic> topics;
    private Set<PathItemGroup> groups;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PathItemTopic implements
            Serializable {
        private UUID id;
        private Long number;
        private ItemType type;
        private ItemEntityType entityType;
        private UUID entityId;
        private TopicDto topic;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PathItemGroup implements
            Serializable {
        private UUID id;
        private Long number;
        private ItemType type;
        private ItemEntityType entityType;
        private UUID entityId;
        private GroupDto group;
    }
}
