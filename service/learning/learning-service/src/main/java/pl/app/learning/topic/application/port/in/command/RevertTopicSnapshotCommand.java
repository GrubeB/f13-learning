package pl.app.learning.topic.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevertTopicSnapshotCommand implements
        Serializable {
    private UUID topicId;
    private Long snapshotNumber;
}