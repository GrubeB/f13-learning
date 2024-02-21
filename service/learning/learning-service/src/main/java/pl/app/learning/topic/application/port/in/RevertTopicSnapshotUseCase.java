package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.RevertTopicSnapshotCommand;

public interface RevertTopicSnapshotUseCase {
    void revertSnapshot(RevertTopicSnapshotCommand command);
}
