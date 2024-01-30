package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.persistance.ChapterRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class ChapterServiceImpl implements
        ChapterService {
    private final ChapterRepository repository;
}
