package edu.java.bot.core;

import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.command.Command;
import edu.java.bot.command.InitialCommand;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Bootstrapper {

    private final Bot bot;
    private final InitialCommand initialCommand;
    private final List<Command> commandList;

    @PostConstruct
    public void start() {

        List<Command> filtered = commandList.stream().filter(c -> !c.equals(initialCommand)).toList();
        Command current = initialCommand;
        for (Command command : filtered) {
            current.setNext(command);
            current = command;
        }

        bot.setUpdatesListener(updates -> {
            updates.forEach(initialCommand::handle);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
