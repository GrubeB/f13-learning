package pl.app.learning.group.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.port.in.MergeRevisionToGroupUseCase;
import pl.app.learning.group.application.port.in.command.MergeRevisionToGroupCommand;
import pl.app.learning.group.application.port.out.GroupDomainRepositoryPort;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.query.GroupRevisionQueryService;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;
import pl.app.learning.topic_revision.query.TopicRevisionQuery;
import pl.app.learning.topic_revision.query.TopicRevisionQueryService;

@CommandHandlerAnnotation
@Component("pl.app.learning.group.application.service.GroupRevisionService")
@RequiredArgsConstructor
@Transactional
class GroupRevisionService implements
        MergeRevisionToGroupUseCase {
    private final GroupDomainRepositoryPort repository;
    private final GroupRevisionQueryService revisionQueryService;

    @Override
    @CommandHandlingAnnotation
    public void merge(MergeRevisionToGroupCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        GroupRevision revision = revisionQueryService.fetchById(command.getRevisionId());
        aggregate.mergeRevision(revision);
        repository.save(aggregate);
    }
}
