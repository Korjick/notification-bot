package edu.java.bot.core.utils;

import java.util.Locale;
import java.util.Set;

public record UserData(
    Locale locale,
    Set<Link> trackLinks) {
}
