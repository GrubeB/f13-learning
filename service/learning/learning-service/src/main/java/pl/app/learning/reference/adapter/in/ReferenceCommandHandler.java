package pl.app.learning.reference.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.learning.reference.application.port.in.*;
import pl.app.learning.reference.application.port.in.command.*;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class ReferenceCommandHandler {
    private final AddUserDislikeUseCase addUserDislikeUseCase;
    private final AddUserLikeUseCase addUserLikeUseCase;
    private final CreateReferenceUseCase createReferenceUseCase;
    private final DeleteReferenceUseCase deleteReferenceUseCase;
    private final UpdateReferenceUseCase updateReferenceUseCase;

    @CommandHandlingAnnotation
    public void handle(AddUserDislikeCommand command) {
        addUserDislikeUseCase.addDislike(command);
    }

    @CommandHandlingAnnotation
    public void handle(AddUserLikeCommand command) {
        addUserLikeUseCase.addLike(command);
    }

    @CommandHandlingAnnotation
    public UUID handle(CreateReferenceCommand command) {
        return createReferenceUseCase.createReference(command);
    }

    @CommandHandlingAnnotation
    public void handle(DeleteReferenceCommand command) {
        deleteReferenceUseCase.deleteReference(command);
    }

    @CommandHandlingAnnotation
    public void handle(UpdateReferenceCommand command) {
        updateReferenceUseCase.updateReference(command);
    }
}
