package pl.app.learning.topic.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateTopicCommand implements
        Serializable {
    private String name;
    private String content;
    private String category;
}