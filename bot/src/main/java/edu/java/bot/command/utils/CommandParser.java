package edu.java.bot.command.utils;

import edu.java.bot.core.dto.Link;
import edu.java.bot.utils.Constants;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Objects;

@Component
public class CommandParser {
    public static final String MESSAGE_SPLITTER = " ";

    public boolean isCommand(String message, String commandName) {
        CommandWithParams commandWithParams = parseCommandWithParams(message);
        return commandWithParams.command().equals(commandName);
    }

    public CommandWithParams parseCommandWithParams(String message) {
        String[] split = message.split(MESSAGE_SPLITTER);
        if (split.length == 1) {
            return new CommandWithParams(split[0]);
        } else if (split.length > 1) {
            return new CommandWithParams(
                split[0],
                Arrays.copyOfRange(split, 1, split.length)
            );
        } else {
            return new CommandWithParams(null);
        }
    }

    public CommandWithLinks parseCommandWithLinks(String message) {
        CommandWithParams commandWithParams = parseCommandWithParams(message);
        return new CommandWithLinks(
            commandWithParams.command(),
            Arrays
                .stream(commandWithParams.params())
                .map(Link::fromURL)
                .filter(link -> !Objects.isNull(link))
                .toArray(Link[]::new)
        );
    }

    public String peelCommandName(String commandName){
        return commandName.replace("/", Constants.EMPTY_STRING);
    }
}
