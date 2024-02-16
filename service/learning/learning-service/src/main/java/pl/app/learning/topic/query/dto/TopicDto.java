package pl.app.learning.topic.query.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopicDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private String category;
}
