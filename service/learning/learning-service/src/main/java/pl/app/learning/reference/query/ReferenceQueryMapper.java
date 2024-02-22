package pl.app.learning.reference.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.reference.query.dto.ReferenceDto;

@Getter
@Component
@RequiredArgsConstructor
public class ReferenceQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        TypeMap<ReferenceQuery, ReferenceDto> typeMap = modelMapper.createTypeMap(ReferenceQuery.class, ReferenceDto.class);
        typeMap.addMapping(r -> r.getReferenceVoting().getDislikesNumber(), ReferenceDto::setDislikesNumber);
        typeMap.addMapping(r -> r.getReferenceVoting().getLikesNumber(), ReferenceDto::setLikesNumber);

        addMapper(ReferenceQuery.class, ReferenceDto.class, e -> modelMapper.map(e, ReferenceDto.class));
        addMapper(ReferenceQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(ReferenceQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
