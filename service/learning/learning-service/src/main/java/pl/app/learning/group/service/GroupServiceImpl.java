package pl.app.learning.group.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.persistance.ChapterRepository;
import pl.app.learning.group.persistance.GroupRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class GroupServiceImpl implements
        GroupService {
    private final GroupRepository repository;
}
