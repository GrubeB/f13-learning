package pl.app.property.accommodation_type.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.property.accommodation_type.application.port.in.command.AddAccommodationCommand;
import pl.app.property.accommodation_type.application.port.in.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.application.port.in.command.RemoveAccommodationCommand;
import pl.app.property.accommodation_type.query.AccommodationQueryService;
import pl.app.property.accommodation_type.query.AccommodationTypeQueryService;
import pl.app.property.accommodation_type.query.dto.AccommodationDto;
import pl.app.property.accommodation_type.query.dto.AccommodationTypeDto;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypeController {
    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    private final AccommodationTypeQueryService accommodationTypeQueryService;
    private final AccommodationQueryService accommodationQueryService;

    @PostMapping
    public ResponseEntity<AccommodationTypeDto> createAccommodationType(@RequestBody CreateAccommodationTypeCommand command, HttpServletRequest request) {
        UUID accommodationTypeId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(accommodationTypeId, request.getRequestURI()))
                .body(accommodationTypeQueryService.fetchById(accommodationTypeId, AccommodationTypeDto.class));
    }

    public static final String addAccommodationPath = "/add-accommodation";

    @PostMapping(path = addAccommodationPath)
    public ResponseEntity<AccommodationDto> addAccommodation(@RequestBody AddAccommodationCommand command, HttpServletRequest request) {
        UUID accommodationId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(accommodationId, request.getRequestURI()))
                .body(accommodationQueryService.fetchById(accommodationId, AccommodationDto.class));
    }

    public static final String removeAccommodationPath = "/remove-accommodation";

    @PostMapping(path = removeAccommodationPath)
    public ResponseEntity<Void> removeAccommodation(@RequestBody RemoveAccommodationCommand command) {
        gateway.send(command);
        return ResponseEntity
                .noContent()
                .build();
    }

}
