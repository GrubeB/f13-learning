package pl.app.learning.topic_snapshot.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.topic.application.domain.TopicStatus;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicSnapshotDto implements
        Serializable {
    private UUID id;
    protected Long snapshotNumber;
    protected UUID snapshotOwnerId;
    private String name;
    private String content;
    private TopicStatus status;
}
