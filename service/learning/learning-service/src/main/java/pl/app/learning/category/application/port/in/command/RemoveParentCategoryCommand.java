package pl.app.learning.category.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveParentCategoryCommand implements
        Serializable {
    private UUID categoryId;
    private UUID parentCategoryId;
}