package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandUtils;
import edu.java.bot.command.utils.CommandWithLinks;
import edu.java.bot.core.utils.Link;
import edu.java.bot.core.utils.UserData;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UnTrackCommand extends Command {

    @Autowired
    public UnTrackCommand(MessageSource messageSource, Map<Long, UserData> trackHandler) {
        super(messageSource, trackHandler);
    }

    @Override
    public String name() {
        return "/untrack";
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
            data.trackLinks().remove(link);
            return new SendMessage(
                chatId,
                messageSource.getMessage("command.untrack.success", null, data.locale())
            );
        }

        return new SendMessage(
            chatId,
            messageSource.getMessage("command.untrack.not.exist", null, data.locale())
        );
    }
}
