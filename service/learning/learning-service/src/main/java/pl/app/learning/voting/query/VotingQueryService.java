package pl.app.learning.voting.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.voting.query.dto.VotingDto;
import pl.app.learning.voting.query.model.VotingQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class VotingQueryService implements
        QueryService.Full<UUID, VotingQuery> {
    private final VotingQueryRepository repository;
    private final VotingQueryRepository specificationRepository;
    private final VotingQueryMapper mapper;
    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("VotingDto", VotingDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
