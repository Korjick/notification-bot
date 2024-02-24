package edu.java.bot.command.utils;

import edu.java.bot.core.dto.Link;

public record CommandWithLinks(String command, Link... links) {
}
