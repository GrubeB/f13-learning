package pl.app.authorization.user.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.role.query.RoleQueryService;
import pl.app.authorization.user.application.domain.UserFactory;
import pl.app.authorization.user.application.port.in.*;
import pl.app.authorization.user.application.port.in.command.*;
import pl.app.authorization.user.application.port.out.UserDomainRepositoryPort;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;
import pl.app.file.file.application.domain.File;
import pl.app.file.file.application.port.in.CreateFileFromBase64Command;
import pl.app.file.file.application.port.in.FileService;

import java.util.List;
import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class UserAvatarService implements
        SetAvatarUseCase {
    private final UserDomainRepositoryPort repository;
    private final FileService fileService;

    @Override
    @CommandHandlingAnnotation
    public void setAvatar(SetAvatarCommand command) {
        var aggregate = repository.load(new AggregateId(command.getUserId()));
        File file = fileService.createFile(new CreateFileFromBase64Command(command.getFileBase64(), command.getFileName()));
        aggregate.setAvatar(file.getId());
        repository.save(aggregate);
    }
}
