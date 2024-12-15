package _com.springframework.web.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
class _UriComponentsTest {

    @ValueSource(strings = {
            "http://www.daum.net",
            "http://www.daum.net/",
            "http://www.daum.net/a.zip",
            "http://www.daum.net/a/b/c.zip",
            "http://www.daum.net/a/b/c.zip?d=e",
            "http://www.daum.net/a/b/c.zip#d?e=f",
            "http://www.daum.net/a/b/%EA%B0%80%EB%82%98%EB%8B%A4.zip#dd?e=f",
            "http://www.daum.net/a/b/%EA%B0%80%EB%82%98%EB%8B%A4.zip;a=b#dd?e=f"
    })
    @ParameterizedTest
    void __1(final String uri) {
        final var builder = UriComponentsBuilder.fromUriString(uri);
        final var built = builder.build(true);
        final var path = built.getPath();
        log.debug("---------------------");
        log.debug("uri: {}", uri);
        log.debug("path: {}", path);
        final var pathSegments = built.getPathSegments();
        if (!pathSegments.isEmpty()) {
            final var lastPathSegment = pathSegments.getLast();
            log.debug("lastPathSegment: '{}'", lastPathSegment);
        }
    }
}
