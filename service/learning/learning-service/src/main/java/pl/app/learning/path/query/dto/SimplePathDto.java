package pl.app.learning.path.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.comment.query.dto.CommentDto;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.application.domain.ItemType;
import pl.app.learning.path.application.domain.PathStatus;
import pl.app.learning.progress.query.dto.ProgressDto;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePathDto implements
        Serializable {
    private UUID id;
    private String name;
    private String content;
    private PathStatus status;
    private List<SimpleCategoryDto> categories;
    private Set<PathItem> items;
    //
    private VotingDto voting;
    private Set<CommentDto> comments;
    private Set<ProgressDto> progresses;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PathItem implements
            Serializable {
        private UUID id;
        private Long number;
        private ItemType type;
        private ItemEntityType entityType;
        private UUID entityId;
    }
}
