package com.github.jinahya.epost.openapi.proxy._hidden._com.springframework.web.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * Utilities related to {@link UriComponents}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
public final class _UriComponents_Utils {

    public static Optional<String> getFile(final String uri, final boolean encoded) {
        final var builder = UriComponentsBuilder.fromUriString(uri);
        final var built = builder.build(encoded);
        return built.getPathSegments().stream().reduce((ps1, ps2) -> ps2)
                .map(lps -> {
                    final var indexOfSemicolon = lps.indexOf(';');
                    if (indexOfSemicolon != -1) {
                        return lps.substring(0, indexOfSemicolon);
                    }
                    return lps;
                })
                .filter(v -> !v.isBlank())
                ;
    }

    private _UriComponents_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
