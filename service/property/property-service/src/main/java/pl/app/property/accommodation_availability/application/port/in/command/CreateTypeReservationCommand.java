package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.ddd.AggregateId;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTypeReservationCommand implements Serializable {
    private AggregateId accommodationTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
}