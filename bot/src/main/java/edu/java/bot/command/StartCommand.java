package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandParser;
import edu.java.bot.core.dto.UserData;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends Command {

    private final Locale defaultLocale;

    @Autowired
    public StartCommand(MessageSource messageSource, Map<Long, UserData> trackHandler, CommandParser commandParser, Locale defaultLocale) {
        super(messageSource, trackHandler, commandParser);
        this.defaultLocale = defaultLocale;
    }

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String userFirstName = update.message().chat().firstName();

        if (!trackHandler.containsKey(chatId)) {
            trackHandler.put(
                chatId,
                new UserData(
                    defaultLocale,
                    new HashSet<>()
                )
            );
        }

        return new SendMessage(
            chatId,
            messageSource.getMessage(
                "command.start",
                new Object[] {userFirstName},
                trackHandler.get(chatId).locale()
            )
        );
    }

    @Override
    public boolean supports(Update update) {
        return commandParser.isCommand(update.message().text(), name());
    }
}
