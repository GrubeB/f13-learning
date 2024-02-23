package pl.app.property.organization.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.organization.application.domain.Organization;
import pl.app.property.organization.query.OrganizationQueryService;

import java.util.UUID;

@RestController
@RequestMapping(OrganizationQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class OrganizationQueryController implements
        QueryController.DtoFetchable.Full<UUID, Organization> {
    public static final String resourceName = "organizations";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final OrganizationQueryService service;
}
