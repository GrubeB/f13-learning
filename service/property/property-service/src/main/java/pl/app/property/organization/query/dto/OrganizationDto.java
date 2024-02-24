package pl.app.property.organization.query.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto implements Serializable {
    private UUID id;
    private String name;
}
