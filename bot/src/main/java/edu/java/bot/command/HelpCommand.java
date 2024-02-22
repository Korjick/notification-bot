package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.core.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HelpCommand extends Command {

    private final List<Command> commands;

    @Override
    public String name() {
        return "/help";
    }

    @Override
    protected boolean handleCommand(Bot bot, Update update) {
        if (update.message().text().equals(name())) {
            List<String> commandsName = commands.stream().map(Command::name).collect(Collectors.toList());
            commandsName.add(name());

            bot.execute(new SendMessage(
                update.message().chat().id(),
                String.format(
                    "Доступные комманды: %s",
                    Arrays.toString(commandsName.toArray()))
            ));
            return true;
        }
        return false;
    }
}
