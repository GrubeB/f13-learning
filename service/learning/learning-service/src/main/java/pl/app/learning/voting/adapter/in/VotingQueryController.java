package pl.app.learning.voting.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.voting.query.UserVoteQueryService;
import pl.app.learning.voting.query.model.UserVoteQuery;

import java.util.UUID;

@RestController
@RequestMapping(VotingQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class VotingQueryController implements
        QueryController.DtoFetchable.Full<UUID, UserVoteQuery> {
    public static final String resourceName = "votes";
    public static final String resourcePath = "/api/v1/" + resourceName;
    private final UserVoteQueryService service;
}
