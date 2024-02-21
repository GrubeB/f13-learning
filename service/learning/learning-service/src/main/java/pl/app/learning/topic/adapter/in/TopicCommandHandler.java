package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.learning.topic.application.port.in.*;
import pl.app.learning.topic.application.port.in.command.*;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class TopicCommandHandler {
    private final CreateTopicUseCase createTopicUseCase;

    private final AddCategoryToTopicUseCase addCategoryToTopicUseCase;
    private final AddReferenceToTopicUseCase addReferenceToTopicUseCase;
    private final ChangeTopicStatusUseCase changeTopicStatusUseCase;
    private final DeleteTopicUseCase deleteTopicUseCase;
    private final RemoveCategoryFromTopicUseCase removeCategoryFromTopicUseCase;
    private final RemoveReferenceFromTopicUseCase removeReferenceFromTopicUseCase;
    private final RevertTopicSnapshotUseCase revertTopicSnapshotUseCase;

    @CommandHandlingAnnotation
    public UUID handle(CreateTopicCommand command) {
        return createTopicUseCase.createTopic(command);
    }


    @CommandHandlingAnnotation
    public void handle(AddCategoryToTopicCommand command) {
        addCategoryToTopicUseCase.addCategory(command);
    }
    @CommandHandlingAnnotation
    public void handle(AddReferenceToTopicCommand command) {
        addReferenceToTopicUseCase.addReference(command);
    }
    @CommandHandlingAnnotation
    public void handle(ChangeTopicStatusCommand command) {
        changeTopicStatusUseCase.changeStatus(command);
    }
    @CommandHandlingAnnotation
    public void handle(DeleteTopicCommand command) {
        deleteTopicUseCase.deleteTopic(command);
    }
    @CommandHandlingAnnotation
    public void handle(RemoveCategoryFromTopicCommand command) {
        removeCategoryFromTopicUseCase.removeCategory(command);
    }
    @CommandHandlingAnnotation
    public void handle(RemoveReferenceFromTopicCommand command) {
        removeReferenceFromTopicUseCase.removeReference(command);
    }
    @CommandHandlingAnnotation
    public void handle(RevertTopicSnapshotCommand command) {
        revertTopicSnapshotUseCase.revertSnapshot(command);
    }
}
