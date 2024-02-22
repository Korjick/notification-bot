package edu.java.bot.command.utils;

import edu.java.bot.command.Command;
import edu.java.bot.core.Link;
import java.util.AbstractMap;
import java.util.Map;

public class CommandUtils {
    private static final String MESSAGE_SPLITTER = " ";

    public static Map.Entry<Boolean, Link> parseCommandWithLink(Command command, String message) {
        String[] splitted = message.split(MESSAGE_SPLITTER);
        Link link = null;
        boolean accurateCommand = false;
        if(splitted.length > 1)
        {
            accurateCommand = command.name().equalsIgnoreCase(splitted[0]);
            link = Link.parseURI(splitted[1]);
        }
        return new AbstractMap.SimpleEntry<>(accurateCommand, link);
    }
}
