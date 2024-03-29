package pl.app.learning.topic_revision.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRevisionDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private List<UUID> categories;
}
