package pl.app.learning.voting.adapter.in;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.query_controller.QueryController;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;
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

    @GetMapping("/users/{userId}")
    ResponseEntity<Page<?>> fetchByUser(@PathVariable UUID userId, @Valid SearchCriteria searchCriteria, Pageable pageable, Dto dto) {
        searchCriteria.addCriteria(new SearchCriteriaItem("userId", Operator.EQUAL, userId.toString()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchByCriteria(searchCriteria, pageable, dto));
    }
}
