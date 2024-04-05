package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.CreateVotingCommand;

import java.util.UUID;

public interface CreateVotingUseCase {
    UUID create(CreateVotingCommand command);
}
