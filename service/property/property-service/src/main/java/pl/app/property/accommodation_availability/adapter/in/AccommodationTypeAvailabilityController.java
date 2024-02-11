package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.cqrs.command.gateway.CommandGateway;
import pl.app.property.accommodation_availability.application.port.in.command.*;

@RestController
@RequestMapping(AccommodationTypeAvailabilityController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityController {
    public static final String resourceName = "accommodation-type-availabilities";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public static final String isAccommodationAvailablePath = "/accommodation-availability";
    public static final String isAccommodationTypeAvailablePath = "/accommodation-type-availability";
    public static final String createAccommodationRestrictionPath = "/create-restriction";
    public static final String createAccommodationTypeReservationPath = "/create-type-reservation";
    public static final String removeAccommodationReservationPath = "/remove-reservation";
    public static final String removeAccommodationTypeReservationPath = "/remove-type-reservation";
    public static final String changeAccommodationReservationStatusPath = "/change-reservation-status";
    public static final String manualAssignPath = "/manual-assign";
    public static final String automaticAssignPath = "/automatic-assign";
    public static final String unassignPath = "/unassign";

    private final CommandGateway gateway;

    @PostMapping(path = isAccommodationAvailablePath)
    public ResponseEntity<Boolean> isAccommodationAvailable(@RequestBody IsAccommodationAvailableCommand command) {
        return ResponseEntity
                .ok(gateway.send(command));
    }

    @PostMapping(path = isAccommodationTypeAvailablePath)
    public ResponseEntity<Boolean> isAccommodationTypeAvailable(@RequestBody IsAccommodationTypeAvailableCommand command) {
        return ResponseEntity
                .ok(gateway.send(command));
    }

    @PostMapping(path = createAccommodationRestrictionPath)
    public ResponseEntity<Void> createAccommodationReservation(@RequestBody CreateRestrictionCommand command) {
        gateway.send(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = createAccommodationTypeReservationPath)
    public ResponseEntity<Void> createAccommodationTypeReservation(@RequestBody CreateTypeReservationCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = removeAccommodationReservationPath)
    public ResponseEntity<Void> removeAccommodationReservation(@RequestBody RemoveRestrictionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = removeAccommodationTypeReservationPath)
    public ResponseEntity<Void> removeAccommodationTypeReservation(@RequestBody RemoveTypeReservationCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = changeAccommodationReservationStatusPath)
    public ResponseEntity<Void> changeAccommodationReservationStatus(@RequestBody ChangeRestrictionStatusCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = manualAssignPath)
    public ResponseEntity<Void> manualAssign(@RequestBody ManualAssignTypeReservationCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = automaticAssignPath)
    public ResponseEntity<Void> automaticAssign(@RequestBody AutomaticAssignTypeReservationCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(path = unassignPath)
    public ResponseEntity<Void> unassign(@RequestBody UnassignTypeReservationCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .noContent()
                .build();
    }
}
