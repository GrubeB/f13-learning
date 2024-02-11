package pl.app.common.cqrs.command.handler.impl;

import pl.app.common.cqrs.command.handler.CommandHandler;

import java.lang.reflect.Method;

public class SimpleCommandHandler<R, C> implements
        CommandHandler<R, C> {
    private final Object object;
    private final Method method;
    private final Class<R> returnType;
    private final Class<C> commandType;

    public SimpleCommandHandler(Object object, Method method, Class<R> returnType, Class<C> commandType) {
        this.object = object;
        this.method = method;
        this.returnType = returnType;
        this.commandType = commandType;
    }

    @Override
    public boolean canHandle(Object command) {
        return commandType.isAssignableFrom(command.getClass());
    }

    @Override
    public R handle(C command) {
        try {
            Object result = method.invoke(object, command);
            return returnType.cast(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
