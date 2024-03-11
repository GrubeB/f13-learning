package pl.app.authorization.user.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.user.query.UserQueryService;
import pl.app.authorization.user.query.model.UserQuery;
import pl.app.common.query_controller.QueryController;

import java.util.UUID;

@RestController
@RequestMapping(UserQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class UserQueryController implements
        QueryController.DtoFetchable.Full<UUID, UserQuery> {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final UserQueryService service;
}
