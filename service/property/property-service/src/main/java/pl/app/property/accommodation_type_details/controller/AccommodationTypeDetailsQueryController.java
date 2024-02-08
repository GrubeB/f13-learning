package pl.app.property.accommodation_type_details.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.property.accommodation_type_details.model.AccommodationTypeDetailsEntity;
import pl.app.property.accommodation_type_details.service.AccommodationTypeDetailsQueryService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeDetailsQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeDetailsQueryController implements
        QueryController.DtoFetchable.Full<UUID, AccommodationTypeDetailsEntity> {
    public static final String resourceName = "accommodation-type-details";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final AccommodationTypeDetailsQueryService service;
}