package pl.app.learning.group_revision.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.application.domain.GroupRevisionException;
import pl.app.learning.group_revision.application.port.out.GroupRevisionDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class GroupRevisionPersistenceAdapter implements
        GroupRevisionDomainRepositoryPort {
    private final GroupRevisionRepository repository;

    @Override
    public GroupRevision load(AggregateId aggregateId) {
        return repository.findById(aggregateId.getId())
                .orElseThrow(() -> GroupRevisionException.NotFoundGroupRevisionException.fromId(aggregateId.getId()));
    }

    @Override
    public void save(GroupRevision aggregate) {
        repository.saveAndFlush(aggregate);
    }

    @Override
    public void delete(AggregateId aggregateId) {
        repository.deleteById(aggregateId.getId());
    }
}
