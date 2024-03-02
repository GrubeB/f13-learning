package pl.app.learning.topic_snapshot.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.learning.topic_snapshot.query.model.TopicSnapshotQuery;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class TopicSnapshotQueryService implements
        QueryService.SimpleFetchable.Full<UUID, TopicSnapshotQuery> {
    private final TopicSnapshotQueryRepository repository;
    private final TopicSnapshotQueryRepository specificationRepository;
}
