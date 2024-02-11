package pl.app.common.cqrs.command.handler;

/*
* Object hold reference to method
* */
public interface CommandHandler<R, C> {
    boolean canHandle(Object command);
    R handle(C command);
}
