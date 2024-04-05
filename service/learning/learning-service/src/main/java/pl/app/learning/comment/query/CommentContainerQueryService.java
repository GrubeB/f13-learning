package pl.app.learning.comment.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.comment.query.dto.CommentContainerDto;
import pl.app.learning.comment.query.model.CommentContainerQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class CommentContainerQueryService implements
        QueryService.Full<UUID, CommentContainerQuery> {
    private final CommentContainerQueryRepository repository;
    private final CommentContainerQueryRepository specificationRepository;
    private final CommentContainerQueryMapper mapper;
    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("CommentContainerDto", CommentContainerDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
