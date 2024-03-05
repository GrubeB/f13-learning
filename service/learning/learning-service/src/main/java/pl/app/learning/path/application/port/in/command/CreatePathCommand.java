package pl.app.learning.path.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.application.domain.ItemType;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePathCommand implements
        Serializable {
    private String name;
    private String content;
    private List<UUID> categoryIds;
    private List<Item> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item implements
            Serializable {
        private Long number;
        private ItemType type;
        private ItemEntityType entityType;
        private UUID entityId;
    }
}