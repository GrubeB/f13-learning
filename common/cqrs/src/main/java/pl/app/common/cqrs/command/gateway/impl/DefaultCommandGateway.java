package pl.app.common.cqrs.command.gateway.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.app.common.cqrs.command.bus.CommandBus;
import pl.app.common.cqrs.command.gateway.CommandGateway;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DefaultCommandGateway implements
        CommandGateway {
    private final Logger logger = LoggerFactory.getLogger(DefaultCommandGateway.class);
    private final CommandBus synchronousCommandBus;
    private final CommandBus asynchronousCommandBus;

    public DefaultCommandGateway(CommandBus synchronousCommandBus, CommandBus asynchronousCommandBus) {
        this.synchronousCommandBus = synchronousCommandBus;
        this.asynchronousCommandBus = asynchronousCommandBus;
    }

    @Override
    public <R, C> R send(C command) {
        logger.debug("dispatching command of type: " + command.getClass().getSimpleName() + "\t class: " + command.getClass().getName());
        logger.trace("command: " + command);
        CompletableFuture<R> dispatch = this.synchronousCommandBus.dispatch(command);
        try {
            return dispatch.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <C> void sendAsync(C command) {
        logger.debug("dispatching command of type: " + command.getClass().getSimpleName() + "\t class: " + command.getClass().getName());
        logger.trace("command: " + command);
        this.asynchronousCommandBus.dispatch(command);
    }
}
