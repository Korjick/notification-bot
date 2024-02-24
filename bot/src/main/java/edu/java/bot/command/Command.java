package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandUtils;
import edu.java.bot.core.utils.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public abstract class Command {
    protected final MessageSource messageSource;
    protected final Map<Long, UserData> trackHandler;

    public abstract String name();

    public abstract SendMessage handleCommand(Update update);

    public boolean supports(Update update) {
        return CommandUtils.isCommand(update.message().text(), name()) &&
            trackHandler.containsKey(update.message().chat().id());
    }
}
