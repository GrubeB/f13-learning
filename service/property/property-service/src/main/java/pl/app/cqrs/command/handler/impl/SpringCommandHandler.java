package pl.app.cqrs.command.handler.impl;

import org.springframework.beans.factory.BeanFactory;
import pl.app.cqrs.command.handler.CommandHandler;

import java.lang.reflect.Method;

public class SpringCommandHandler<R, C> implements
        CommandHandler<R, C> {
    private final String beanName;
    private final Method method;
    private final Class<R> returnType;
    private final Class<C> commandType;

    private final BeanFactory beanFactory;

    public SpringCommandHandler(String beanName, Method method, Class<R> returnType, Class<C> commandType, BeanFactory beanFactory) {
        this.beanName = beanName;
        this.method = method;
        this.returnType = returnType;
        this.commandType = commandType;
        this.beanFactory = beanFactory;
    }


    @Override
    public boolean canHandle(Object command) {
        return commandType.isAssignableFrom(command.getClass());
    }

    @Override
    public R handle(C command) {
        Object bean = beanFactory.getBean(beanName);
        try {
            Object result = method.invoke(bean, command);
            return returnType.cast(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
