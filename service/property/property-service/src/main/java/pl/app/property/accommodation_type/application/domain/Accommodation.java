package pl.app.property.accommodation_type.application.domain;


import lombok.Getter;
import pl.app.common.ddd.BaseEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

import java.util.UUID;

@EntityAnnotation
@Getter
public class Accommodation extends BaseEntity {
    private String name;
    private String description;

    public Accommodation(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }
    public Accommodation(UUID entityId, String name, String description) {
        super(entityId);
        this.name = name;
        this.description = description;
    }
}
