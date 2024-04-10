package pl.app.learning.voting.query.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VotingDto implements
        Serializable {
    private Long likesNumber;
    private Long dislikesNumber;
}