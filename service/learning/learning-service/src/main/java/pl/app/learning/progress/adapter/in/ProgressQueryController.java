package pl.app.learning.progress.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.progress.query.ProgressQueryService;
import pl.app.learning.progress.query.model.ProgressQuery;

import java.util.UUID;

@RestController
@RequestMapping(ProgressQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ProgressQueryController implements
        QueryController.DtoFetchable.Full<UUID, ProgressQuery> {
    public static final String resourceName = "progresses";
    public static final String resourcePath = "/api/v1/" + resourceName;
    private final ProgressQueryService service;
}
