package pl.app.learning.group.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.group.query.model.GroupQuery;

import java.util.UUID;

@RestController
@RequestMapping(GroupQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GroupQueryController implements
        QueryController.DtoFetchable.Full<UUID, GroupQuery> {
    public static final String resourceName = "groups";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final GroupQueryService service;
}
