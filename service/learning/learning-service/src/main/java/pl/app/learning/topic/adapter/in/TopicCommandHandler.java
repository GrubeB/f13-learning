package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.port.in.CreateTopicUseCase;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class TopicCommandHandler {
    private final CreateTopicUseCase createTopicUseCase;

    @CommandHandlingAnnotation
    public AggregateId handle(CreateTopicCommand command) {
        return createTopicUseCase.createTopic(command);
    }
}
