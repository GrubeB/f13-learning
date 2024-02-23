package pl.app.property.property.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.property.application.domain.PropertyType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private UUID id;
    private String name;
    private PropertyType propertyType;
    private LocalTime checkInFromTime;
    private LocalTime checkInToTime;
    private LocalTime checkOutFromTime;
    private LocalTime checkOutToTime;
    private PropertyDetailsDto propertyDetails;
    private BaseDto organization;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyDetailsDto implements Serializable {
        private UUID id;
        private String email;
        private String phone;
        private String website;
        private String description;
        private PropertyAddressDto propertyAddress;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyAddressDto implements Serializable {
        private UUID id;
        private String address1;
        private String address2;
        private String city;
        private String country;
        private String region;
        private String zipCode;
    }
}
