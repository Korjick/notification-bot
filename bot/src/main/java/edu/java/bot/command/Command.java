package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import edu.java.bot.command.utils.CommandParser;
import edu.java.bot.core.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public abstract class Command {
    protected final MessageSource messageSource;
    protected final Map<Long, UserData> trackHandler;
    protected final CommandParser commandParser;

    public abstract String name();
    public abstract AbstractSendRequest<? extends AbstractSendRequest<?>> handle(Update update);

    public boolean supports(Update update) {
        return commandParser.isCommand(update.message().text(), name()) &&
            trackHandler.containsKey(update.message().chat().id());
    }
}
