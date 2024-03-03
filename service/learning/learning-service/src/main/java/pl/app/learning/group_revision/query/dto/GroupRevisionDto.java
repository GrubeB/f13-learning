package pl.app.learning.group_revision.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.group.application.domain.GroupStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRevisionDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private GroupStatus status;
    private List<UUID> categories;
    private List<UUID> topics;
    private List<UUID> groups;
}
