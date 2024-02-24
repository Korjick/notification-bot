package edu.java.bot.command.utils;

import edu.java.bot.core.utils.Link;
import edu.java.bot.utils.Constants;
import java.util.Arrays;
import java.util.Objects;

public class CommandUtils {
    public static final String MESSAGE_SPLITTER = " ";

    public static boolean isCommand(String message, String commandName) {
        CommandWithParams commandWithParams = CommandUtils.parseCommandWithParams(message);
        return commandWithParams.command().equals(commandName);
    }

    public static CommandWithParams parseCommandWithParams(String message) {
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

    public static CommandWithLinks parseCommandWithLinks(String message) {
        CommandWithParams commandWithParams = parseCommandWithParams(message);
        return new CommandWithLinks(
            commandWithParams.command(),
            Arrays
                .stream(commandWithParams.params())
                .map(Link::parseURL)
                .filter(link -> !Objects.isNull(link))
                .toArray(Link[]::new)
        );
    }

    public static String peelCommandName(String commandName){
        return commandName.replace("/", Constants.EMPTY_STRING);
    }
}
