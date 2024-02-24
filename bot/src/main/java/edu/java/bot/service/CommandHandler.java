package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import edu.java.bot.core.dto.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final List<Command> commands;
    private final MessageSource messageSource;
    private final Map<Long, UserData> trackHandler;
    private final Locale defaultLocale;

    public AbstractSendRequest<? extends AbstractSendRequest<?>> handle(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        long chatId = update.message().chat().id();
        return new SendMessage(
            chatId,
            messageSource.getMessage(
                "command.not.found",
                null,
                trackHandler.containsKey(chatId) ? trackHandler.get(chatId).locale() : defaultLocale
            )
        );
    }
}
