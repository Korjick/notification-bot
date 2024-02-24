package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandParser;
import edu.java.bot.core.dto.UserData;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends Command {

    @Autowired
    public ListCommand(MessageSource messageSource, CommandParser commandParser, Map<Long, UserData> trackHandler) {
        super(messageSource, trackHandler, commandParser);
    }

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        UserData data = trackHandler.get(chatId);

        if (!data.trackLinks().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            data.trackLinks().forEach(link -> stringBuilder.append("\n%s".formatted(link.url())));
            stringBuilder.insert(
                0,
                messageSource.getMessage("command.list.exist", null, data.locale())
            );
            return new SendMessage(chatId, stringBuilder.toString());
        } else {
            return new SendMessage(
                chatId,
                messageSource.getMessage("command.list.empty", null, data.locale())
            );
        }
    }
}
