package pl.app.learning.reference.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.reference.query.ReferenceQuery;
import pl.app.learning.reference.query.ReferenceQueryService;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class ReferenceQueryController implements
        QueryController.DtoFetchable.Full<UUID, ReferenceQuery> {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final ReferenceQueryService service;
}
