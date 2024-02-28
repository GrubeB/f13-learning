package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.RevertGroupSnapshotCommand;

public interface RevertGroupSnapshotCase {
    void revert(RevertGroupSnapshotCommand command);
}
