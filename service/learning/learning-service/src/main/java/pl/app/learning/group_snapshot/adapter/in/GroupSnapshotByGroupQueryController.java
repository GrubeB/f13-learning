package pl.app.learning.group_snapshot.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.group_snapshot.query.GroupSnapshotQueryService;
import pl.app.learning.group_snapshot.query.model.GroupSnapshotQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(GroupSnapshotByGroupQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class GroupSnapshotByGroupQueryController implements
        QueryController.SimpleFetchableWithFilter.Full<UUID, GroupSnapshotQuery> {

    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;
    public final GroupSnapshotQueryService service;
    private final Map<String, String> parentFilterMap = new LinkedHashMap<>() {{
        put("groupId", "snapshotOwnerId");
    }};
}
