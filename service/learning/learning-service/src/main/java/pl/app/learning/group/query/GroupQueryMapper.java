package pl.app.learning.group.query;

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
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.dto.SimpleGroupDto;
import pl.app.learning.group.query.model.GroupHasCategoryQuery;
import pl.app.learning.group.query.model.GroupHasGroupQuery;
import pl.app.learning.group.query.model.GroupHasTopicQuery;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.reference.query.ReferenceQueryMapper;
import pl.app.learning.topic.query.TopicQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class GroupQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final CategoryQueryMapper categoryQueryMapper;
    private final ReferenceQueryMapper referenceQueryMapper;
    private final TopicQueryMapper topicQueryMapper;

    @PostConstruct
    void init() {
        initGroupQueryToGroupDto();
        initGroupQueryToSimpleGroupDto();

        addMapper(GroupQuery.class, GroupDto.class, e -> modelMapper.map(e, GroupDto.class));
        addMapper(GroupQuery.class, SimpleGroupDto.class, e -> modelMapper.map(e, SimpleGroupDto.class));
        addMapper(GroupQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(GroupQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }

    private void initGroupQueryToSimpleGroupDto() {
        TypeMap<GroupQuery, SimpleGroupDto> typeMap = modelMapper.createTypeMap(GroupQuery.class, SimpleGroupDto.class);

        Converter<Set<GroupHasCategoryQuery>, List<SimpleCategoryDto>> categoryConverter = context -> context.getSource().stream()
                .map(GroupHasCategoryQuery::getCategory)
                .map(c -> categoryQueryMapper.map(c, SimpleCategoryDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(GroupQuery::getCategories, SimpleGroupDto::setCategories));
    }

    private void initGroupQueryToGroupDto() {
        TypeMap<GroupQuery, GroupDto> typeMap = modelMapper.createTypeMap(GroupQuery.class, GroupDto.class);

        Converter<Set<GroupHasCategoryQuery>, List<SimpleCategoryDto>> categoryConverter = context -> context.getSource().stream()
                .map(GroupHasCategoryQuery::getCategory)
                .map(c -> categoryQueryMapper.map(c, SimpleCategoryDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(GroupQuery::getCategories, GroupDto::setCategories));

        Converter<Set<GroupHasTopicQuery>, List<TopicDto>> topicConverter = context -> context.getSource().stream()
                .map(GroupHasTopicQuery::getTopic)
                .map(c -> topicQueryMapper.map(c, TopicDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(topicConverter).map(GroupQuery::getTopics, GroupDto::setTopics));

        Converter<Set<GroupHasGroupQuery>, List<SimpleGroupDto>> groupConverter = context -> context.getSource().stream()
                .map(GroupHasGroupQuery::getChildGroup)
                .map(c -> this.map(c, SimpleGroupDto.class))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(groupConverter).map(GroupQuery::getGroups, GroupDto::setGroups));
    }
}
