package pl.app.learning.topic.application.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import pl.app.common.ddd.JpaBaseAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_topic")
public class Topic extends JpaBaseAggregateRoot {
    private String name;
    private String content;
    private String category;
    public Topic() {
        super();
    }

    public Topic(String name) {
        this.name = name;
    }

    public Topic(String name, String content, String category) {
        this.name = name;
        this.content = content;
        this.category = category;
    }
}

