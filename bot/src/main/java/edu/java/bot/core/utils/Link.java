package edu.java.bot.core.utils;

import edu.java.bot.utils.Constants;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public record Link(
    String url,
    String authority,
    String path,
    Map<String, String> queries,
    String scheme) {

    private static final String QUERIES_SPLITTER = "&";
    private static final String QUERY_KEY_VALUE_SPLITTER = "=";

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    public static Link parseURL(String url) {
        try {
            URL uri = URI.create(url).toURL();

            return new Link(
                url,
                uri.getAuthority(),
                uri.getPath(),
                Arrays.stream(
                        Objects.requireNonNullElse(uri.getQuery(), Constants.EMPTY_STRING)
                            .split(QUERIES_SPLITTER))
                            .map(s -> s.split(QUERY_KEY_VALUE_SPLITTER))
                            .filter(s -> s.length > 1)
                            .collect(Collectors.toMap(k -> k[0], v -> v[1])),
                uri.getProtocol()
            );
        } catch (Exception e) {
            return null;
        }
    }
}
