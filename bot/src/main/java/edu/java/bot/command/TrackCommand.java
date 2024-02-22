package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandUtils;
import edu.java.bot.core.Bot;
import edu.java.bot.core.Link;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand extends Command {

    private final Map<Long, Set<Link>> trackHandler;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    protected boolean handleCommand(Bot bot, Update update) {
        String message = update.message().text();
        Long chatId = update.message().chat().id();

        Map.Entry<Boolean, Link> parsed = CommandUtils.parseCommandWithLink(this, message);
        if (parsed.getKey() && parsed.getValue() != null) {
            if (!trackHandler.containsKey(chatId))
                trackHandler.put(chatId, new HashSet<>());
            bot.execute(
                new SendMessage(
                    chatId,
                    trackHandler.get(chatId).add(parsed.getValue()) ?
                        "Ваша ссылка добавлена для отслеживания"
                        : "Такая ссылка уже находится в списке отслеживания"));
            return true;
        }
        return false;
    }
}
