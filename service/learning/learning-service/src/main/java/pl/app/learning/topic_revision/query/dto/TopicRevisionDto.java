package pl.app.learning.topic_revision.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.shared.dto.BaseDto;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRevisionDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private BaseDto topic;
}
