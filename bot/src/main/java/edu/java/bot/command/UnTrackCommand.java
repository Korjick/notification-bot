package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.utils.CommandUtils;
import edu.java.bot.core.Bot;
import edu.java.bot.core.Link;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnTrackCommand extends Command {

    private final Map<Long, Set<Link>> trackHandler;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    protected boolean handleCommand(Bot bot, Update update) {
        String message = update.message().text();
        Long chatId = update.message().chat().id();

        Map.Entry<Boolean, Link> parsed = CommandUtils.parseCommandWithLink(this, message);
        if (parsed.getKey() && parsed.getValue() != null) {
            if (trackHandler.containsKey(chatId)) {
                bot.execute(
                    new SendMessage(
                        chatId,
                        trackHandler.get(chatId).remove(parsed.getValue()) ?
                            "Ссылка была успешна удалена с отслеживания"
                            : "Данная ссылка не отслеживалась пользователем"
                    ));
            } else {
                bot.execute(
                    new SendMessage(
                        chatId,
                        "Пользователь не добавил ссылок для отслеживания"
                    ));
            }
            return true;
        }

        return false;
    }

}
