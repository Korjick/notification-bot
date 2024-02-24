package edu.java.bot.core.dto;

import java.util.Locale;
import java.util.Set;

public record UserData(
    Locale locale,
    Set<Link> trackLinks) {
}
