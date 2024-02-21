package pl.app.learning.reference.application.port.in;

import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.port.in.command.CreateReferenceCommand;

import java.util.UUID;

public interface CreateReferenceUseCase {
    UUID createReference(CreateReferenceCommand command);
}
