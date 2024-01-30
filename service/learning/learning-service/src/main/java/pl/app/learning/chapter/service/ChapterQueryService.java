package pl.app.learning.chapter.service;


import pl.app.common.service.QueryService;
import pl.app.learning.chapter.model.ChapterEntity;

import java.util.UUID;

public interface ChapterQueryService extends
        QueryService.SimpleFetchable.Full<UUID, ChapterEntity> {
}
