package pl.app.authorization.role.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.role.query.RoleQueryService;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.common.dto_criteria.Dto;
import pl.app.common.query_controller.QueryController;

import java.util.UUID;

@RestController
@RequestMapping(RoleQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class RoleQueryController implements
        QueryController.DtoFetchable.DtoFetchableBySearchCriteria<UUID, RoleQuery> {
    public static final String resourceName = "roles";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final RoleQueryService service;
    @GetMapping("/{name}")
    public ResponseEntity<?> fetchByNameAndDto(@PathVariable String name,
                                               Dto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getService().fetchByName(name, dto));
    }
}
