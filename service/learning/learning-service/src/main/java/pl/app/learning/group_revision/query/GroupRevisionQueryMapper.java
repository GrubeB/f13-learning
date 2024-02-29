package pl.app.learning.group_revision.query;

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
import pl.app.learning.group.query.dto.SimpleGroupDto;
import pl.app.learning.group_revision.application.domain.GroupHasCategoryRevision;
import pl.app.learning.group_revision.application.domain.GroupHasGroupRevision;
import pl.app.learning.group_revision.application.domain.GroupHasTopicRevision;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.query.dto.GroupRevisionDto;
import pl.app.learning.reference.query.ReferenceQueryMapper;
import pl.app.learning.topic.query.TopicQueryMapper;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Component
@RequiredArgsConstructor
public class GroupRevisionQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final CategoryQueryMapper categoryQueryMapper;
    private final ReferenceQueryMapper referenceQueryMapper;
    private final TopicQueryMapper topicQueryMapper;

    @PostConstruct
    void init() {
        initGroupRevisionToGroupRevisionDto();

        addMapper(GroupRevision.class, GroupRevisionDto.class, e -> modelMapper.map(e, GroupRevisionDto.class));
        addMapper(GroupRevision.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(GroupRevision.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
    private void initGroupRevisionToGroupRevisionDto() {
        TypeMap<GroupRevision, GroupRevisionDto> typeMap = modelMapper.createTypeMap(GroupRevision.class, GroupRevisionDto.class);

        Converter<Set<GroupHasCategoryRevision>, List<UUID>> categoryConverter = context -> context.getSource().stream()
                .map(GroupHasCategoryRevision::getCategory)
                .map(AggregateId::getId)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(categoryConverter).map(GroupRevision::getCategories, GroupRevisionDto::setCategories));

        Converter<Set<GroupHasTopicRevision>, List<UUID>> topicConverter = context -> context.getSource().stream()
                .map(GroupHasTopicRevision::getTopic)
                .map(AggregateId::getId)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(topicConverter).map(GroupRevision::getTopics, GroupRevisionDto::setTopics));

        Converter<Set<GroupHasGroupRevision>, List<UUID>> groupConverter = context -> context.getSource().stream()
                .map(GroupHasGroupRevision::getChildGroup)
                .map(AggregateId::getId)
                .toList();
        typeMap.addMappings(mapper -> mapper.using(groupConverter).map(GroupRevision::getGroups, GroupRevisionDto::setGroups));
    }
}
