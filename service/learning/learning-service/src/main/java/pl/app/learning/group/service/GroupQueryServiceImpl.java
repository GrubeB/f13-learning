package pl.app.learning.group.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.group.persistance.GroupRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class GroupQueryServiceImpl implements
        GroupQueryService {
    private final GroupRepository repository;
    private final GroupRepository specificationRepository;
}
