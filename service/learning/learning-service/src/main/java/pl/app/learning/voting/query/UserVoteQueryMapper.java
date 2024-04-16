package pl.app.learning.voting.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.voting.query.dto.UserVoteDto;
import pl.app.learning.voting.query.model.UserVoteQuery;

@Getter
@Component
@RequiredArgsConstructor
public class UserVoteQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<UserVoteQuery, UserVoteDto>() {
            @Override
            protected void configure() {
                map().setDomainObject(source.getVoting().getDomainObject());
                map().setDomainObjectType(source.getVoting().getDomainObjectType());
            }
        });

        addMapper(UserVoteQuery.class, UserVoteDto.class, e -> modelMapper.map(e, UserVoteDto.class));
        addMapper(UserVoteQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(UserVoteQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
