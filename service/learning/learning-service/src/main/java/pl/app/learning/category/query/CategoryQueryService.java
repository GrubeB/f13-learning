package pl.app.learning.category.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.category.query.dto.CategoryDto;
import pl.app.learning.category.query.dto.SimpleCategoryDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class CategoryQueryService implements
        QueryService.Full<UUID, CategoryQuery> {
    private final CategoryQueryRepository repository;
    private final CategoryQueryRepository specificationRepository;
    private final CategoryQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("CategoryDto", CategoryDto.class);
        put("SimpleCategoryDto", SimpleCategoryDto.class);
        put("AggregateId", AggregateId.class);
        put("BaseDto", BaseDto.class);
    }};
}
