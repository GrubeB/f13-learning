package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.persistance.ChapterRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class ChapterQueryServiceImpl implements
        ChapterQueryService {
    private final ChapterRepository repository;
    private final ChapterRepository specificationRepository;
}
