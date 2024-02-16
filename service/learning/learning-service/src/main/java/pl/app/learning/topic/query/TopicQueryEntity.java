package pl.app.learning.topic.query;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic")
@Immutable
public class TopicQueryEntity extends BaseAuditEntity<UUID> {
    @Id
    private UUID id;
    private String name;
    private String content;
    private String category;
}

