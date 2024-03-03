package pl.app.learning.group_revision.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.query.GroupRevisionQueryService;

import java.util.UUID;

@RestController
@RequestMapping(GroupRevisionQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GroupRevisionQueryController implements
        QueryController.DtoFetchable.Full<UUID, GroupRevision> {
    public static final String resourceName = "group-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final GroupRevisionQueryService service;
}
