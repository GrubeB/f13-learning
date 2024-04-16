package pl.app.learning.group.query;

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
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.dto.SimpleGroupDto;
import pl.app.learning.group.query.model.GroupHasCategoryQuery;
import pl.app.learning.group.query.model.GroupHasGroupQuery;
import pl.app.learning.group.query.model.GroupHasTopicQuery;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class GroupQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<GroupQuery, SimpleGroupDto>() {
            @Override
            protected void configure() {
                using((Converter<Set<GroupHasCategoryQuery>, List<SimpleCategoryDto>>) context -> context.getSource().stream()
                        .map(GroupHasCategoryQuery::getCategory)
                        .map(c -> modelMapper.map(c, SimpleCategoryDto.class))
                        .toList()
                ).map(source.getCategories()).setCategories(null);
            }
        });
        modelMapper.addMappings(new PropertyMap<GroupQuery, GroupDto>() {
            @Override
            protected void configure() {
                using((Converter<Set<GroupHasCategoryQuery>, List<SimpleCategoryDto>>) context -> context.getSource().stream()
                        .map(GroupHasCategoryQuery::getCategory)
                        .map(c -> modelMapper.map(c, SimpleCategoryDto.class))
                        .toList()
                ).map(source.getCategories()).setCategories(null);

                using((Converter<Set<GroupHasTopicQuery>, List<TopicDto>>) context -> context.getSource().stream()
                        .map(GroupHasTopicQuery::getTopic)
                        .map(c -> modelMapper.map(c, TopicDto.class))
                        .toList()
                ).map(source.getTopics()).setTopics(null);

                using((Converter<Set<GroupHasGroupQuery>, List<SimpleGroupDto>>) context -> context.getSource().stream()
                        .map(GroupHasGroupQuery::getChildGroup)
                        .map(c -> modelMapper.map(c, SimpleGroupDto.class))
                        .toList()
                ).map(source.getGroups()).setGroups(null);

                map(source.getComment().getComments()).setComments(null);
                map(source.getReference().getReferences()).setReferences(null);
                map(source.getProgress().getProgresses()).setProgresses(null);
            }
        });

        addMapper(GroupQuery.class, GroupDto.class, e -> modelMapper.map(e, GroupDto.class));
        addMapper(GroupQuery.class, SimpleGroupDto.class, e -> modelMapper.map(e, SimpleGroupDto.class));
        addMapper(GroupQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(GroupQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
