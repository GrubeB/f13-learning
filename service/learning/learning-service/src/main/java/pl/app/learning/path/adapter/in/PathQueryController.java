package pl.app.learning.path.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.path.query.PathQueryService;
import pl.app.learning.path.query.model.PathQuery;

import java.util.UUID;

@RestController
@RequestMapping(PathQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PathQueryController implements
        QueryController.DtoFetchable.Full<UUID, PathQuery> {
    public static final String resourceName = "paths";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final PathQueryService service;
}
