package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpCommand extends Command {

    private final Bot bot;
    private final List<Command> commands;

    @Override
    public String name() {
        return "/help";
    }

    @Override
    protected boolean handleCommand(Update update) {
        if (update.message().text().equals(name())) {
            bot.execute(new SendMessage(
                update.message().chat().id(),
                String.format(
                    "Доступные комманды: %s",
                    Arrays.toString(commands.stream().map(Command::name).toArray()))
            ));
            return true;
        }
        return false;
    }
}
