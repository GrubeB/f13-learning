package pl.app.learning.path.query;

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
import pl.app.learning.group.query.GroupQueryMapper;
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.query.dto.PathDto;
import pl.app.learning.path.query.dto.SimplePathDto;
import pl.app.learning.path.query.model.PathHasCategoryQuery;
import pl.app.learning.path.query.model.PathItemQuery;
import pl.app.learning.path.query.model.PathQuery;
import pl.app.learning.topic.query.TopicQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.List;
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
        modelMapper.addMappings(new PropertyMap<PathQuery, SimplePathDto>() {
            @Override
            protected void configure() {
                using((Converter<Set<PathHasCategoryQuery>, List<SimpleCategoryDto>>) context -> context.getSource().stream()
                        .map(PathHasCategoryQuery::getCategory)
                        .map(c -> modelMapper.map(c, SimpleCategoryDto.class))
                        .toList()
                ).map(source.getCategories()).setCategories(null);
                map(source.getComment().getComments()).setComments(null);
                map(source.getProgress().getProgresses()).setProgresses(null);

                using((Converter<Set<PathItemQuery>, Set<SimplePathDto.PathItem>>) context -> context.getSource().stream()
                        .map(item -> {
                            if (item.getEntity() instanceof TopicQuery topicQuery) {
                                return new SimplePathDto.PathItem(
                                        item.getId(),
                                        item.getNumber(),
                                        item.getType(),
                                        ItemEntityType.TOPIC,
                                        topicQuery.getId()
                                );
                            } else if (item.getEntity() instanceof GroupQuery groupQuery) {
                                return new SimplePathDto.PathItem(
                                        item.getId(),
                                        item.getNumber(),
                                        item.getType(),
                                        ItemEntityType.GROUP,
                                        groupQuery.getId()
                                );
                            }
                            return null;
                        })
                        .collect(Collectors.toSet())
                ).map(source.getItems()).setItems(null);
            }
        });
        modelMapper.addMappings(new PropertyMap<PathQuery, PathDto>() {
            @Override
            protected void configure() {
                using((Converter<Set<PathHasCategoryQuery>, List<SimpleCategoryDto>>) context -> context.getSource().stream()
                        .map(PathHasCategoryQuery::getCategory)
                        .map(c -> modelMapper.map(c, SimpleCategoryDto.class))
                        .toList()
                ).map(source.getCategories()).setCategories(null);
                map(source.getComment().getComments()).setComments(null);
                map(source.getProgress().getProgresses()).setProgresses(null);
                using((Converter<Set<PathItemQuery>, Set<PathDto.PathItemTopic>>) context -> context.getSource().stream()
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
                        .collect(Collectors.toSet())
                ).map(source.getItems()).setTopics(null);
                using((Converter<Set<PathItemQuery>, Set<PathDto.PathItemGroup>>) context -> context.getSource().stream()
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
                        .collect(Collectors.toSet())
                ).map(source.getItems()).setGroups(null);
            }
        });

        addMapper(PathQuery.class, PathDto.class, e -> modelMapper.map(e, PathDto.class));
        addMapper(PathQuery.class, SimplePathDto.class, e -> modelMapper.map(e, SimplePathDto.class));
        addMapper(PathQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(PathQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
