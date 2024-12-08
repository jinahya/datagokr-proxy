package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy._TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Slf4j
// https://github.com/junit-team/junit5/issues/1477
class AreaCodeInfoUtils_Test {

    private static Stream<String> getResNameStream() {
        return Stream.of(
                "_zipcode_DB.zip",
                "_areacd_chgaddr_DB.zip",
                "_areacd_rangeaddr_DB.zip",
                "_areacd_pobox_DB.zip"
        );
    }

    private static Stream<URL> getResourceUrlStream() {
        return getResNameStream()
                .map(AreaCodeInfoUtils_Test.class::getResource)
                .filter(Objects::nonNull);
    }

    static Stream<File> getResourceFileStream() {
        return getResourceUrlStream()
                .map(v -> {
                    try {
                        return v.toURI();
                    } catch (final URISyntaxException urise) {
                        throw new RuntimeException("failed to get URI from " + v, urise);
                    }
                })
                .map(File::new);
    }

    private static List<String> getEntryNameList(final ZipFile zipFile) {
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(
                                zipFile.entries().asIterator(),
                                Spliterator.ORDERED
                        ),
                        false
                )
                .filter(e -> !e.isDirectory())
                .map(ZipEntry::getName)
                .filter(n -> n.endsWith(".txt"))
                .toList();
    }

    private static Stream<Arguments> getResourceFileAndEntryNameArgumentsStream() {
        return getResourceFileStream()
                .flatMap(f -> {
                    try (var zipFile = new ZipFile(f, AreaCodeInfoUtils.CHARSET)) {
                        return getEntryNameList(zipFile)
                                .stream()
                                .map(n -> arguments(Named.of(f.getName(), f), n));
                    } catch (final IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                });
    }

    // -------------------------------------------------------------------------------------------------------- download
    @Tag(_TestConstants.TAG_LONG_RUNNING)
    @Disabled("takes to long, baby")
    @DisplayName("extract(stream, consumer)")
    @MethodSource({"getResNameStream"})
    @ParameterizedTest
    void download__(final String resName) throws IOException {
        try (var resource = getClass().getResourceAsStream(resName)) {
            assumeTrue(resource != null, () -> "null resource for " + resource);
            final var flags = new HashMap<String, Boolean>();
            AreaCodeInfoUtils.extract(
                    resource,
                    (n, m) -> {
                        if (flags.compute(n, (k, v) -> v == null)) {
                            log.debug("n: {}, m: {}", n, m);
                        }
                    });
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Tag(_TestConstants.TAG_LONG_RUNNING)
    @Disabled("takes to long, baby")
    @DisplayName("extract(stream, consumer)")
    @MethodSource({"getResNameStream"})
    @ParameterizedTest
    void extract__(final String resName) throws IOException {
        try (var resource = getClass().getResourceAsStream(resName)) {
            assumeTrue(resource != null, () -> "null resource for " + resource);
            final var flags = new HashMap<String, Boolean>();
            AreaCodeInfoUtils.extract(
                    resource,
                    (n, m) -> {
                        if (flags.compute(n, (k, v) -> v == null)) {
                            log.debug("n: {}, m: {}", n, m);
                        }
                    });
        }
    }

    @Disabled("takes to long, baby")
    @DisplayName("extract(source, target)")
    @MethodSource({"getResourceFileStream"})
    @ParameterizedTest
    void extract(final File source, @TempDir final File target) throws IOException {
        AreaCodeInfoUtils.extract(source, target);
    }
}
