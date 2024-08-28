package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * A utility class for extracting downloaded db files.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Slf4j
public final class AreaCodeInfoUtils {

    static final Charset CHARSET = Charset.forName("EUC-KR");

    static final String DELIMITER = "\\|";

    public static <R> R extract(final File file, final Function<? super ZipFile, ? extends R> function)
            throws IOException {
        if (!Objects.requireNonNull(file, "file is null").isFile()) {
            throw new IllegalArgumentException("file is not a regular file: " + file);
        }
        Objects.requireNonNull(function, "function is null");
        try (var zipFile = new ZipFile(file, CHARSET)) {
            return function.apply(zipFile);
        }
    }

    public static void extract(final InputStream stream,
                               final Consumer<? super String[]> headerConsumer,
                               final Consumer<? super String[]> rowConsumer)
            throws IOException {
        Objects.requireNonNull(stream, "stream is null");
        Objects.requireNonNull(rowConsumer, "rowConsumer is null");
        final var reader = new BufferedReader(new InputStreamReader(stream));
        final var first = reader.readLine();
        if (first.isEmpty()) {
            return;
        }
        headerConsumer.accept(first.split(DELIMITER));
        reader.lines()
                .map(l -> l.split(DELIMITER))
                .forEach(rowConsumer);
    }

    public static void extract(final InputStream stream,
                               final Consumer<? super Map<String, String>> consumer)
            throws IOException {
        Objects.requireNonNull(stream, "stream is null");
        Objects.requireNonNull(consumer, "consumer is null");
        final var reader = new BufferedReader(new InputStreamReader(stream));
        final var first = reader.readLine();
        if (first.isEmpty()) {
            return;
        }
        final var keys = List.of(first.split("\\|"));
        reader.lines()
                .map(l -> {
                    final var i = keys.iterator();
                    return Arrays.stream(l.split("\\|"))
                            .map(v -> new AbstractMap.SimpleEntry<>(i.next(), v))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y,
                                                      LinkedHashMap::new));
                })
                .forEach(consumer);
    }

    private static void extract(final InputStream stream, final String name,
                                final BiConsumer<? super String, ? super Map<String, String>> consumer)
            throws IOException {
        Objects.requireNonNull(consumer, "consumer is null");
        extract(
                stream,
                m -> consumer.accept(name, m)
        );
    }

    /**
     * Extracts specified db file stream, and accepts entry names of {@code *.txt} and rows to specified consumer.
     *
     * @param stream   the db file stream to extract.
     * @param consumer the consumer.
     * @throws IOException if an I/O error occurs.
     */
    public static void extract(final InputStream stream,
                               final BiConsumer<? super String, ? super Map<String, String>> consumer)
            throws IOException {
        Objects.requireNonNull(stream, "stream is null");
        Objects.requireNonNull(consumer, "consumer is null");
        try (var zis = new ZipInputStream(stream, CHARSET)) {
            for (ZipEntry entry; (entry = zis.getNextEntry()) != null; zis.closeEntry()) {
                final var name = entry.getName();
                if (!name.endsWith(".txt")) {
                    continue;
                }
                extract(zis, name, consumer);
            }
        }
    }

    /**
     * Extracts a specific entry of specified zip file, and accepts each sub-entry to specified consumer.
     *
     * @param file     the zip file to extract.
     * @param name     the entry name to extract. e.g. {@code 강원특별자치도.txt}.
     * @param consumer the consumer accepts sub-entries.
     * @throws IOException if an I/O error occurs.
     * @see #extract(File, Predicate, BiConsumer)
     */
    public static void extract(final File file, final String name, final Consumer<? super Map<String, String>> consumer)
            throws IOException {
        if (!Objects.requireNonNull(file, "file is null").isFile()) {
            throw new IllegalArgumentException("file is not a regular file: " + file);
        }
        Objects.requireNonNull(name, "name is null");
        try (var zipFile = new ZipFile(file, CHARSET)) {
            final var entry = zipFile.getEntry(name);
            if (entry == null) {
                throw new IllegalArgumentException("no entry for '" + name + "'");
            }
            try (final var stream = zipFile.getInputStream(entry)) {
                extract(stream, consumer);
            }
        }
    }

    /**
     * Extracts all entries of specified zip file, and accepts each sub-entry to specified consumer.
     *
     * @param file      the zip file to extract.
     * @param predicate the predicate for filtering entries.
     * @param consumer  the consumer accepts sub-entries.
     * @throws IOException if an I/O error occurs.
     * @see #extract(File, String, Consumer)
     */
    @SuppressWarnings({
            "java:S112" // new RuntimeException
    })
    public static void extract(final File file, final Predicate<? super ZipEntry> predicate,
                               final BiConsumer<? super ZipEntry, ? super Map<String, String>> consumer)
            throws IOException {
        if (!Objects.requireNonNull(file, "file is null").isFile()) {
            throw new IllegalArgumentException("file is not a regular file: " + file);
        }
        Objects.requireNonNull(predicate, "predicate is null");
        try (var zipFile = new ZipFile(file, CHARSET)) {
            zipFile.stream().filter(predicate).forEach(e -> {
                try (final var stream = zipFile.getInputStream(e)) {
                    extract(stream, m -> consumer.accept(e, m));
                } catch (final IOException ioe) {
                    throw new RuntimeException("failed to extract " + file, ioe);
                }
            });
        }
    }

    /**
     * Extracts all tested entries from specified zip file to specified target directory.
     *
     * @param source    the zip file to be extracted.
     * @param target    the target directory to which the {@code source} is extracted.
     * @param predicate predicate tests each entry's name.
     * @throws IOException if an I/O error occurs.
     */
    public static void extract(final File source, final File target, final Predicate<? super String> predicate)
            throws IOException {
        if (!Objects.requireNonNull(source, "source is null").isFile()) {
            throw new IllegalArgumentException("source is not a normal file: " + source);
        }
        if (!Objects.requireNonNull(target, "target is null").isDirectory()) {
            throw new IllegalArgumentException("target is not a directory: " + target);
        }
        Objects.requireNonNull(predicate, "predicate is null");
        try (var zipFile = new ZipFile(source, CHARSET)) {
            for (var e = zipFile.entries(); e.hasMoreElements(); ) {
                final var entry = e.nextElement();
                final var file = new File(target, entry.getName());
                if (entry.isDirectory()) {
                    if (!file.isDirectory() && !file.mkdirs()) {
                        throw new RuntimeException("failed to mkdir: " + file);
                    }
                    continue;
                }
                if (!predicate.test(file.getName())) {
                    log.debug("skipping {}", file);
                    continue;
                }
                final var bytes = Files.copy(zipFile.getInputStream(entry), file.toPath(),
                                             StandardCopyOption.REPLACE_EXISTING);
                log.debug("{}: {}", entry.getName(), bytes);
            }
        }
    }

    /**
     * Extracts all entries from specified zip file to specified target directory.
     *
     * @param source the zip file to be extracted.
     * @param target the target directory to which the {@code source} is extracted.
     * @throws IOException if an I/O error occurs.
     */
    public static void extract(final File source, final File target)
            throws IOException {
        extract(source, target, n -> true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AreaCodeInfoUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
