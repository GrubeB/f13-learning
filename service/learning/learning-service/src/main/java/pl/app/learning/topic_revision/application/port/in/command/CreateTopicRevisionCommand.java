package pl.app.learning.topic_revision.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTopicRevisionCommand implements
        Serializable {
    private UUID ownerId;
    private String name;
    private String content;
    private List<Category> categories;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category implements
            Serializable {
        private UUID ownerId;
        private UUID categoryId;
    }
}