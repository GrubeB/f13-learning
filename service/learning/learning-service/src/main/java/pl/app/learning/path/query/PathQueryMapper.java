package pl.app.learning.path.query;

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
import pl.app.learning.group.query.GroupQueryMapper;
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.query.dto.PathDto;
import pl.app.learning.path.query.model.PathHasCategoryQuery;
import pl.app.learning.path.query.model.PathItemQuery;
import pl.app.learning.path.query.model.PathQuery;
import pl.app.learning.topic.query.TopicQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class PathQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final CategoryQueryMapper categoryQueryMapper;
    private final TopicQueryMapper topicQueryMapper;
    private final GroupQueryMapper groupQueryMapper;

    @PostConstruct
    void init() {
        initPathQueryToPathDto();

        addMapper(PathQuery.class, PathDto.class, e -> modelMapper.map(e, PathDto.class));
        addMapper(PathQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(PathQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }

    private void initPathQueryToPathDto() {
        TypeMap<PathQuery, PathDto> typeMap = modelMapper.createTypeMap(PathQuery.class, PathDto.class);

        Converter<Set<PathHasCategoryQuery>, Set<SimpleCategoryDto>> categoryConverter = context -> context.getSource().stream()
                .map(PathHasCategoryQuery::getCategory)
                .map(c -> categoryQueryMapper.map(c, SimpleCategoryDto.class))
                .collect(Collectors.toSet());
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(PathQuery::getCategories, PathDto::setCategories));

        Converter<Set<PathItemQuery>, Set<PathDto.PathItemTopic>> pathItemTopicConverter = context -> context.getSource().stream()
                .filter(item -> item.getEntity() instanceof TopicQuery)
                .map(item -> {
                    TopicQuery topicQuery = (TopicQuery) item.getEntity();
                    return new PathDto.PathItemTopic(
                            item.getId(),
                            item.getNumber(),
                            item.getType(),
                            ItemEntityType.TOPIC,
                            topicQuery.getId(),
                            topicQueryMapper.map(topicQuery, TopicDto.class)
                    );
                })
                .collect(Collectors.toSet());
        typeMap.addMappings(mapper -> mapper.using(pathItemTopicConverter).map(PathQuery::getItems, PathDto::setTopics));

        Converter<Set<PathItemQuery>, Set<PathDto.PathItemGroup>> pathItemGroupConverter = context -> context.getSource().stream()
                .filter(item -> item.getEntity() instanceof GroupQuery)
                .map(item -> {
                    GroupQuery groupQuery = (GroupQuery) item.getEntity();
                    return new PathDto.PathItemGroup(
                            item.getId(),
                            item.getNumber(),
                            item.getType(),
                            ItemEntityType.GROUP,
                            groupQuery.getId(),
                            groupQueryMapper.map(groupQuery, GroupDto.class)
                    );
                })
                .collect(Collectors.toSet());
        typeMap.addMappings(mapper -> mapper.using(pathItemGroupConverter).map(PathQuery::getItems, PathDto::setGroups));
    }
}
