package pl.app.learning.comment.query;

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
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.comment.query.dto.CommentContainerDto;
import pl.app.learning.comment.query.model.CommentContainerQuery;
import pl.app.learning.topic.query.dto.TopicDto;
import pl.app.learning.topic.query.model.TopicHasCategoryQuery;
import pl.app.learning.topic.query.model.TopicQuery;
import pl.app.learning.voting.query.VotingQueryMapper;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class CommentContainerQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;
    private final VotingQueryMapper votingQueryMapper;

    @PostConstruct
    void init() {
        addMapper(CommentContainerQuery.class, CommentContainerDto.class, e -> modelMapper.map(e, CommentContainerDto.class));
        addMapper(CommentContainerQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(CommentContainerQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
