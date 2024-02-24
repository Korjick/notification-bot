package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandUtils;
import edu.java.bot.command.utils.CommandWithLinks;
import edu.java.bot.core.utils.Link;
import edu.java.bot.core.utils.UserData;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends Command {

    @Autowired
    public TrackCommand(MessageSource messageSource, Map<Long, UserData> trackHandler) {
        super(messageSource, trackHandler);
    }

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        Long chatId = update.message().chat().id();
        UserData data = trackHandler.get(chatId);

        String message = update.message().text();
        CommandWithLinks parsed = CommandUtils.parseCommandWithLinks(message);

        if (parsed.links().length < 1) {
            return new SendMessage(
                chatId,
                messageSource.getMessage("command.un.track.error", null, data.locale())
            );
        }

        Link link = parsed.links()[0];
        if (data.trackLinks().contains(link)) {
            return new SendMessage(
                chatId,
                messageSource.getMessage("command.track.exists", null, data.locale())
            );
        }

        data.trackLinks().add(link);
        return new SendMessage(
            chatId,
            messageSource.getMessage("command.track.success", null, data.locale())
        );
    }
}
