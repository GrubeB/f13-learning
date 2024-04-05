package pl.app.learning.comment.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.comment.query.CommentQueryService;
import pl.app.learning.comment.query.model.CommentQuery;

import java.util.UUID;

@RestController
@RequestMapping(CommentQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class CommentQueryController implements
        QueryController.DtoFetchable.Full<UUID, CommentQuery> {
    public static final String resourceName = "comments";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final CommentQueryService service;
}
