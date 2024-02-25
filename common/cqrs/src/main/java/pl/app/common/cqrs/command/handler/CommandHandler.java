package pl.app.common.cqrs.command.handler;

import java.lang.reflect.InvocationTargetException;

/*
* Object hold reference to method
* */
public interface CommandHandler<R, C> {
    boolean canHandle(Object command);
    R handle(C command);
}
