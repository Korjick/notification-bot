package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.Bot;
import java.util.List;
import lombok.RequiredArgsConstructor;

public abstract class Command {
    private Command next;

    public abstract String name();
    protected abstract boolean handleCommand(Bot bot, Update update);

    @RequiredArgsConstructor
    public static class Handler {

        private final Command root;

        public void handle(Bot bot, Update update) {
            Command current = root;
            while (current != null) {
                if (current.handleCommand(bot, update))
                    break;

                current = current.next;
            }
        }

        public static Handler build(Command first, List<Command> chain) {
            Command current = first;
            for (Command command : chain) {
                current.next = command;
                current = command;
            }

            return new Handler(first);
        }
    }
}
