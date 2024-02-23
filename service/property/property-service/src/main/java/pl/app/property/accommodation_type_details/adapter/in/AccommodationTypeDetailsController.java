package pl.app.property.accommodation_type_details.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetails;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.in.command.UpdateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeDetailsController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeDetailsController implements
        CommandController.Updatable.DtoUpdatable<UUID, AccommodationTypeDetails, UpdateAccommodationTypeDetailsCommand, AccommodationTypeDetailsDto> {

    public static final String resourceName = "accommodation-type-details";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final AccommodationTypeDetailsService service;
}
