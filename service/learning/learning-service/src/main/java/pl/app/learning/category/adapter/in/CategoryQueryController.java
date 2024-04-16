package pl.app.learning.category.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.category.query.model.CategoryQuery;

import java.util.UUID;

@RestController
@RequestMapping(CategoryQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class CategoryQueryController implements
        QueryController.DtoFetchable.Full<UUID, CategoryQuery> {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final CategoryQueryService service;
}
