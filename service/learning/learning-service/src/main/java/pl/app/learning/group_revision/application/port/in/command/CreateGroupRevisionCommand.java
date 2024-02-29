package pl.app.learning.group_revision.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.group.application.domain.GroupStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRevisionCommand implements
        Serializable {
    private UUID ownerId;
    private String name;
    private String content;
    private GroupStatus status;
    private List<Category> categories;
    private List<Topic> topics;
    private List<Group> groups;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category implements
            Serializable {
        private UUID ownerId;
        private UUID categoryId;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Topic implements
            Serializable {
        private UUID ownerId;
        private UUID topicId;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Group implements
            Serializable {
        private UUID ownerId;
        private UUID groupId;
    }
}