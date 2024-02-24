package pl.app.property.accommodation_type_details.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.accommodation_type_details.application.domain.AccommodationTypeDetails;
import pl.app.property.accommodation_type_details.query.AccommodationTypeDetailsQueryService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeDetailsQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeDetailsQueryController implements
        QueryController.DtoFetchable.Full<UUID, AccommodationTypeDetails> {
    public static final String resourceName = "accommodation-type-details";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final AccommodationTypeDetailsQueryService service;
}
