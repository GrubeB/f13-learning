package pl.app.authorization.permision.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.permision.query.PermissionQueryService;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.query_controller.QueryController;

import java.util.UUID;

@RestController
@RequestMapping(PermissionQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class PermissionQueryController implements
        QueryController.DtoFetchable.DtoFetchableBySearchCriteria<UUID, PermissionQuery> {
    public static final String resourceName = "permissions";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final PermissionQueryService service;

    @GetMapping("/{name}")
    public ResponseEntity<?> fetchByNameAndDto(@PathVariable String name,
                                              Dto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().fetchByName(name, dto));
    }
}
