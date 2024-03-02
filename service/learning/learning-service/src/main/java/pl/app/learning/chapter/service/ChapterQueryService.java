package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.learning.chapter.model.Chapter;
import pl.app.learning.chapter.persistance.ChapterRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ChapterQueryService implements
        QueryService.SimpleFetchable.Full<UUID, Chapter> {
    private final ChapterRepository repository;
    private final ChapterRepository specificationRepository;
}
