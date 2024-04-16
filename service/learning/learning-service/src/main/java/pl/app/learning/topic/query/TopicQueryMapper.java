package pl.app.learning.topic.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.category.query.CategoryQueryMapper;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.group.query.model.GroupHasCategoryQuery;
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
        modelMapper.addMappings(new PropertyMap<TopicQuery, TopicDto>() {
            @Override
            protected void configure() {
                using((Converter<Set<TopicHasCategoryQuery>, List<SimpleCategoryDto>>) context -> context.getSource().stream()
                        .map(TopicHasCategoryQuery::getCategory)
                        .map(c -> modelMapper.map(c, SimpleCategoryDto.class))
                        .toList()
                ).map(source.getCategories()).setCategories(null);
                map(source.getComment().getComments()).setComments(null);
                map(source.getReference().getReferences()).setReferences(null);
                map(source.getProgress().getProgresses()).setProgresses(null);
            }
        });
        addMapper(TopicQuery.class, TopicDto.class, e -> modelMapper.map(e, TopicDto.class));
        addMapper(TopicQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(TopicQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
