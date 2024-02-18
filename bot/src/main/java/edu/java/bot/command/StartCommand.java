package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand extends InitialCommand {

    private final Bot bot;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    protected boolean handleCommand(Update update) {
        if (update.message().text().equals(name())) {
            bot.execute(new SendMessage(update.message().chat().id(),
                String.format("Hello, %s", update.message().chat().firstName())));
            return true;
        }
        return false;
    }
}
