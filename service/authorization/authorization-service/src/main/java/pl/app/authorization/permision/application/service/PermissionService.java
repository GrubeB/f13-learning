package pl.app.authorization.permision.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.permision.application.domain.PermissionFactory;
import pl.app.authorization.permision.application.port.in.CreatePermissionUseCase;
import pl.app.authorization.permision.application.port.in.DeletePermissionUseCase;
import pl.app.authorization.permision.application.port.in.command.CreatePermissionCommand;
import pl.app.authorization.permision.application.port.in.command.DeletePermissionCommand;
import pl.app.authorization.permision.application.port.out.PermissionDomainRepositoryPort;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class PermissionService implements
        CreatePermissionUseCase,
        DeletePermissionUseCase {
    private final PermissionFactory factory;
    private final PermissionDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreatePermissionCommand command) {
        var aggregate = factory.create(command.getName());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeletePermissionCommand command) {
        var aggregate = repository.load(command.getName());
        aggregate.delete();
        repository.delete(aggregate);
    }
}
