package pl.app.learning.voting.query.dto;

import lombok.Data;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.UUID;

@Data
public class VotingDto implements
        Serializable {
    private Long likesNumber;
    private Long dislikesNumber;
}