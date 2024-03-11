package pl.app.authorization.role.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.permision.query.PermissionQueryService;
import pl.app.authorization.role.application.domain.RoleFactory;
import pl.app.authorization.role.application.port.in.*;
import pl.app.authorization.role.application.port.out.RoleDomainRepositoryPort;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.List;
import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class RoleService implements
        RemovePermissionFromRoleUseCase,
        AddPermissionToRoleUseCase,
        UpdateRoleUseCase,
        CreateRoleUseCase,
        DeleteRoleUseCase {
    private final RoleFactory factory;
    private final RoleDomainRepositoryPort repository;

    private final PermissionQueryService permissionQueryService;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateRoleCommand command) {
        var aggregate = factory.create(command.getName(), command.getPermissions());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteRoleCommand command) {
        var aggregate = repository.load(command.getName());
        aggregate.delete();
        repository.delete(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateRoleCommand command) {
        var aggregate = repository.load(command.getName());
        List<AggregateId> permissions = getPermissionsByNames(command.getPermissions());
        aggregate.setPermissions(permissions);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void addPermission(AddPermissionToRoleCommand command) {
        var aggregate = repository.load(command.getName());
        List<AggregateId> permissions = getPermissionsByNames(command.getPermissions());
        permissions.forEach(aggregate::addPermission);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removePermission(RemovePermissionFromRoleCommand command) {
        var aggregate = repository.load(command.getName());
        List<AggregateId> permissions = getPermissionsByNames(command.getPermissions());
        permissions.forEach(aggregate::removePermission);
        repository.save(aggregate);
    }

    private List<AggregateId> getPermissionsByNames(List<String> command) {
        return permissionQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.IN, command)
        )), AggregateId.class);
    }
}
