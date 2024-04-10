package pl.app.learning.comment.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.comment.query.model.CommentQuery;

@Getter
@Component
@RequiredArgsConstructor
public class CommentQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(CommentQuery.class, CommentDto.class, e -> modelMapper.map(e, CommentDto.class));
        addMapper(CommentQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(CommentQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
