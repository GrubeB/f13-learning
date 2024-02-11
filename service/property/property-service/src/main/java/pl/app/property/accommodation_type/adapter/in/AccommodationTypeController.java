package pl.app.property.accommodation_type.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.property.accommodation_type.application.port.in.command.AddAccommodationCommand;
import pl.app.property.accommodation_type.application.port.in.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.application.port.in.command.RemoveAccommodationCommand;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypeController {
    public static final String resourceName = "accommodation-types";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public static final String createAccommodationTypePath = "";
    public static final String addAccommodationPath = "/add-accommodation";
    public static final String removeAccommodationPath = "/remove-accommodation";

    private final CommandGateway gateway;

    @PostMapping(path = createAccommodationTypePath)
    public ResponseEntity<UUID> createAccommodationType(@RequestBody CreateAccommodationTypeCommand command, HttpServletRequest request) {
        UUID accommodationTypeId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(accommodationTypeId, request.getRequestURI()))
                .body(accommodationTypeId);
    }
    @PostMapping(path = addAccommodationPath)
    public ResponseEntity<UUID> addAccommodation(@RequestBody AddAccommodationCommand command, HttpServletRequest request) {
        UUID accommodationId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(accommodationId, request.getRequestURI()))
                .body(accommodationId);
    }

    @PostMapping(path = removeAccommodationPath)
    public ResponseEntity<Void> removeAccommodation(@RequestBody RemoveAccommodationCommand command) {
        gateway.send(command);
        return ResponseEntity
                .noContent()
                .build();
    }

}
