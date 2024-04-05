package pl.app.learning.comment.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.comment.query.dto.CommentContainerDto;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.comment.query.model.CommentQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class CommentQueryService implements
        QueryService.Full<UUID, CommentQuery> {
    private final CommentQueryRepository repository;
    private final CommentQueryRepository specificationRepository;
    private final CommentQueryMapper mapper;
    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("CommentDto", CommentDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
