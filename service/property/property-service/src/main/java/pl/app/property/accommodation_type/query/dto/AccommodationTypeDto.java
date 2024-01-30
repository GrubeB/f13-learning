package pl.app.property.accommodation_type.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsDto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeDto implements Serializable {
    private UUID accommodationTypeId;
    private Set<AccommodationDto> accommodations;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationDto implements Serializable {
        private UUID accommodationId;
        private String name;
        private String description;
    }
}
