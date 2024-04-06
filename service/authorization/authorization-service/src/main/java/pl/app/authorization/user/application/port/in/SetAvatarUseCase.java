package pl.app.authorization.user.application.port.in;

import pl.app.authorization.user.application.port.in.command.SetAvatarCommand;

public interface SetAvatarUseCase {
    void setAvatar(SetAvatarCommand command);
}
