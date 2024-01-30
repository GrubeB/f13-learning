package pl.app.property.accommodation_type_details.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.command_controller.CommandController;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsCreateDto;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.model.AccommodationTypeDetailsEntity;
import pl.app.property.accommodation_type_details.service.AccommodationTypeDetailsService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeDetailsController.resourcePath)
@RequiredArgsConstructor
@Getter
public class AccommodationTypeDetailsController implements
        CommandController.Updatable.DtoUpdatable<UUID, AccommodationTypeDetailsEntity, AccommodationTypeDetailsDto, AccommodationTypeDetailsDto>,
        CommandController.Deletable.SimpleDeletable<UUID, AccommodationTypeDetailsEntity> {
    public static final String resourceName = "accommodation-type-details";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public final AccommodationTypeDetailsService service;
}
