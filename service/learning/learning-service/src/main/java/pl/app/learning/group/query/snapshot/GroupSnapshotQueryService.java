package pl.app.learning.group.query.snapshot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.learning.group.query.snapshot.model.GroupSnapshotQuery;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class GroupSnapshotQueryService implements
        QueryService.SimpleFetchable.Full<UUID, GroupSnapshotQuery> {
    private final GroupSnapshotQueryRepository repository;
    private final GroupSnapshotQueryRepository specificationRepository;
}
