package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import java.util.List;
import java.util.Objects;
import edu.java.bot.core.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final List<Command> commandList;

    public void handle(Bot bot, Update update) {
        for (Command command : commandList) {
            if (command.supports(update))
                bot.execute(command.handleCommand(update));
        }
    }
}
