package pl.app.authorization.user.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.role.query.RoleQueryService;
import pl.app.authorization.user.application.domain.UserFactory;
import pl.app.authorization.user.application.port.in.*;
import pl.app.authorization.user.application.port.in.command.AddRoleToUserCommand;
import pl.app.authorization.user.application.port.in.command.ChangePasswordCommand;
import pl.app.authorization.user.application.port.in.command.RegisterUserCommand;
import pl.app.authorization.user.application.port.in.command.RemoveRoleFromUserCommand;
import pl.app.authorization.user.application.port.out.UserDomainRepositoryPort;
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
class UserService implements
        ChangePasswordUseCase,
        AddRoleToUserUseCase,
        RemoveRoleFromUserUseCase,
        RegisterUserUseCase {
    private final UserFactory factory;
    private final UserDomainRepositoryPort repository;
    private final RoleQueryService roleQueryService;

    @Override
    @CommandHandlingAnnotation
    public UUID register(RegisterUserCommand command) {
        var aggregate = factory.create(command.getEmail(), command.getPassword(), command.getRoles());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void addRole(AddRoleToUserCommand command) {
        var aggregate = repository.load(new AggregateId(command.getUserId()));
        List<AggregateId> roles = getRolesByNames(command.getRoles());
        roles.forEach(aggregate::addRole);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeRole(RemoveRoleFromUserCommand command) {
        var aggregate = repository.load(new AggregateId(command.getUserId()));
        List<AggregateId> roles = getRolesByNames(command.getRoles());
        roles.forEach(aggregate::removeRole);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void changePassword(ChangePasswordCommand command) {
        var aggregate = repository.load(new AggregateId(command.getUserId()));
        aggregate.changePassword(command.getCurrentPassword(), command.getNewPassword());
        repository.save(aggregate);
    }

    private List<AggregateId> getRolesByNames(List<String> roleNames) {
        return roleQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.IN, roleNames)
        )), AggregateId.class);
    }
}
