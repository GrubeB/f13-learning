package pl.app.learning.group.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.domain.GroupException;
import pl.app.learning.group.application.domain.snapshot.GroupSnapshot;
import pl.app.learning.group.application.port.in.RevertGroupSnapshotCase;
import pl.app.learning.group.application.port.in.command.RevertGroupSnapshotCommand;
import pl.app.learning.group.application.port.out.GroupDomainRepositoryPort;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class GroupSnapshotService implements
        RevertGroupSnapshotCase {
    private final GroupDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public void revert(RevertGroupSnapshotCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        GroupSnapshot snapshot = aggregate.getSnapshotBySnapshotNumber(command.getSnapshotNumber())
                .orElseThrow(() -> GroupException.NotFoundGroupSnapshotException.fromSnapshotNumber(command.getSnapshotNumber()));
        aggregate.revertSnapshot(snapshot);
        repository.save(aggregate);
    }
}
