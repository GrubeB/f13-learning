package pl.app.learning.voting.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.voting.query.dto.VotingDto;
import pl.app.learning.voting.query.model.VotingQuery;

@Getter
@Component
@RequiredArgsConstructor
public class VotingQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(VotingQuery.class, VotingDto.class, e -> modelMapper.map(e, VotingDto.class));
        addMapper(VotingQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(VotingQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
