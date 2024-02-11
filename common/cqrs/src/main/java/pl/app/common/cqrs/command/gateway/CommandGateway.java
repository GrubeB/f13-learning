package pl.app.common.cqrs.command.gateway;

public interface CommandGateway {
    <R, C> R send(C command);

    <C> void sendAsync(C command);
}
