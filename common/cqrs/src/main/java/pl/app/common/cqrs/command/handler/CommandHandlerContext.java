package pl.app.common.cqrs.command.handler;

import java.util.Set;

/*
* Command handlers container
* */
public interface CommandHandlerContext {
    void registerCommandHandling(CommandHandler<?, ?> commandHandler);
    void registerCommandHandler(Object commandListener);
    Set<CommandHandler<?, ?>> getHandlers();
}
