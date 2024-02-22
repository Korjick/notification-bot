package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.Bot;
import edu.java.bot.core.Link;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand extends Command {

    private final Map<Long, Set<Link>> trackHandler;

    @Override
    public String name() {
        return "/list";
    }

    @Override
    protected boolean handleCommand(Bot bot, Update update) {
        Long chatId = update.message().chat().id();
        if (update.message().text().equals(name())) {
            if(trackHandler.containsKey(chatId)) {
                bot.execute(
                    new SendMessage(
                        chatId,
                        Arrays.toString(trackHandler.get(chatId).toArray())));
            } else {
                bot.execute(
                    new SendMessage(chatId,
                        "Пользователь не подписан на обновление ресурсов"));
            }

            return true;
        }
        return false;
    }
}
