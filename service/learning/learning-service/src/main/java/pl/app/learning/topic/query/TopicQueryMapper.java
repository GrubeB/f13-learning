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
import pl.app.learning.reference.query.dto.ReferenceDto;
import pl.app.learning.topic.query.dto.TopicDto;

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
        TypeMap<TopicQuery, TopicDto> typeMap = modelMapper.createTypeMap(TopicQuery.class, TopicDto.class);
        Converter<Set<TopicHasCategoryQuery>, List<SimpleCategoryDto>> categoryConverter = context -> context.getSource().stream()
                .map(TopicHasCategoryQuery::getCategory)
                .map(c -> categoryQueryMapper.map(c, SimpleCategoryDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(TopicQuery::getCategories, TopicDto::setCategories));
        Converter<Set<TopicHasReferenceQuery>, List<ReferenceDto>> referenceConverter = context -> context.getSource().stream()
                .map(TopicHasReferenceQuery::getReference)
                .map(c -> referenceQueryMapper.map(c, ReferenceDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(referenceConverter).map(TopicQuery::getReferences, TopicDto::setReferences));

        addMapper(TopicQuery.class, TopicDto.class, e -> modelMapper.map(e, TopicDto.class));
        addMapper(TopicQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
