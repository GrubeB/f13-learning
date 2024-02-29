package pl.app.learning.group.application.port.in.command;

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
public class UpdateGroupCommand implements
        Serializable {
    private UUID groupId;
    private String name;
    private String content;
    private List<UUID> categoryIds;
    private List<UUID> topicIds;
    private List<UUID> groupIds;
}