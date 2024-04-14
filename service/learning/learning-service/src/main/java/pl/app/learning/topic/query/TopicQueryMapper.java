package pl.app.learning.topic.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.category.query.CategoryQueryMapper;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.reference.query.ReferenceQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicHasCategoryQuery;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class TopicQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final CategoryQueryMapper categoryQueryMapper;
    private final ReferenceQueryMapper referenceQueryMapper;

    @PostConstruct
    void init() {
        addMapper(TopicQuery.class, TopicDto.class, e -> modelMapper.map(e, TopicDto.class));
        addMapper(TopicQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
