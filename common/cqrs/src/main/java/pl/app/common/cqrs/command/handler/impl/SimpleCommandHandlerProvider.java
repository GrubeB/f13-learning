package pl.app.common.cqrs.command.handler.impl;

import pl.app.common.cqrs.command.handler.CommandHandlerContext;
import pl.app.common.cqrs.command.handler.CommandHandler;
import pl.app.common.cqrs.command.handler.CommandHandlerProvider;

import java.util.Optional;


public class SimpleCommandHandlerProvider implements
        CommandHandlerProvider {
    private final CommandHandlerContext commandHandlerContext;

    public SimpleCommandHandlerProvider(CommandHandlerContext commandHandlerContext) {
        this.commandHandlerContext = commandHandlerContext;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, C> CommandHandler<R, C> getHandler(C command) {
        Optional<CommandHandler<?, ?>> handler = commandHandlerContext.getHandlers().stream()
                .filter(commandHandler -> commandHandler.canHandle(command))
                .findAny();
        return (CommandHandler<R, C>) handler
                .orElseThrow(() -> new RuntimeException("not found command handler that can handle command of type: " + command.getClass()));
    }
}
