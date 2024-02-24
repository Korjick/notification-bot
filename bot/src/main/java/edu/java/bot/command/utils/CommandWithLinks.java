package edu.java.bot.command.utils;

import edu.java.bot.core.utils.Link;

public record CommandWithLinks(String command, Link... links) {
}
