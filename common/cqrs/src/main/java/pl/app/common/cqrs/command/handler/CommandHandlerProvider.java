package pl.app.common.cqrs.command.handler;

public interface CommandHandlerProvider {
    <R, C> CommandHandler<R, C> getHandler(C command);
}
