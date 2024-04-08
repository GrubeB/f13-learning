package pl.app.learning.voting.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.voting.query.dto.UserVoteDto;
import pl.app.learning.voting.query.dto.VotingDto;
import pl.app.learning.voting.query.model.UserVoteQuery;
import pl.app.learning.voting.query.model.VotingQuery;

@Getter
@Component
@RequiredArgsConstructor
public class UserVoteQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        addMapper(UserVoteQuery.class, UserVoteDto.class, this::mapToUserVoteDto);
        addMapper(UserVoteQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(UserVoteQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
    private UserVoteDto mapToUserVoteDto(UserVoteQuery entity){
        return new UserVoteDto(
                entity.getUserId(),
                entity.getType(),
                entity.getVoting().getDomainObject(),
                entity.getVoting().getDomainObjectType()
        );
    }
}
