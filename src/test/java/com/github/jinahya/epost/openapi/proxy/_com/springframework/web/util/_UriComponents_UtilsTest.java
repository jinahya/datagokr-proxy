package com.github.jinahya.epost.openapi.proxy._com.springframework.web.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
class _UriComponents_UtilsTest {

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
        final var file = _UriComponents_Utils.getFile(uri, true);
        log.debug("---------------------");
        log.debug("uri: {}", uri);
        log.debug("file: {}", file);
    }

    @ValueSource(strings = {
            "http://www.daum.net",
            "http://www.daum.net/",
            "http://www.daum.net/a.zip",
            "http://www.daum.net/a/b/c.zip",
            "http://www.daum.net/a/b/c.zip?d=e",
            "http://www.daum.net/a/b/c.zip#d?e=f",
            "http://www.daum.net/a/b/가나다.zip#dd?e=f",
            "http://www.daum.net/a/b/가나다.zip;a=b#dd?e=f"
    })
    @ParameterizedTest
    void __2(final String uri) {
        final var file = _UriComponents_Utils.getFile(uri, false);
        log.debug("---------------------");
        log.debug("uri: {}", uri);
        log.debug("file: {}", file);
    }
}