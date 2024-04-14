package pl.app.learning.progress.query.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ProgressContainerDto implements
        Serializable {
    private Set<ProgressDto> progresses;
}