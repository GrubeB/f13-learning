package pl.app.learning.topic.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.topic.application.domain.TopicStatus;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTopicStatusCommand implements
        Serializable {
    private UUID topicId;
    private TopicStatus status;
}