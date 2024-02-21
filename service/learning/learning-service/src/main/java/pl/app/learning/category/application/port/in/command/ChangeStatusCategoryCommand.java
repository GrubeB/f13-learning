package pl.app.learning.category.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.category.application.domain.CategoryStatus;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusCategoryCommand implements
        Serializable {
    private UUID categoryId;
    private CategoryStatus status;
}