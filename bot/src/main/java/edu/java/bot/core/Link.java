package edu.java.bot.core;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public record Link(
    String fullURL,
    String authority,
    String path,
    Map<String, String> queries,
    String scheme) {

    private static final String QUERIES_SPLITTER = "&";
    private static final String QUERY_KEY_VALUE_SPLITTER = "=";

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(fullURL, link.fullURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullURL);
    }

    public static Link parseURI(String uriString) {
        try {
            URI uri = URI.create(uriString);
            return new Link(
                uriString,
                uri.getAuthority(),
                uri.getPath(),
                Objects.isNull(uri.getQuery()) ?
                    null :
                    Arrays.stream(uri.getQuery().split(QUERIES_SPLITTER))
                    .map(s -> s.split(QUERY_KEY_VALUE_SPLITTER))
                    .filter(s -> s.length > 1)
                    .collect(Collectors.toMap(k -> k[0], v -> v[1])),
                uri.getScheme()
            );
        } catch (Exception e) {
            return null;
        }
    }
}
