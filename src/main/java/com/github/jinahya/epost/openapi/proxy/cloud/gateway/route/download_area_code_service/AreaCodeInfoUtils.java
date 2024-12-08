package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy._misc.net.HttpURLConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
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

    // -------------------------------------------------------------------------------------------------------- download

    /**
     * Downloads specified response's {@link AreaCodeInfoResponse#getFile() file} to specified file.
     *
     * @param response the response whose {@link AreaCodeInfoResponse#getFile() file} is downloaded.
     * @param target   the target file to which {@link AreaCodeInfoResponse#getFile() response.file} is downloaded.
     * @return the number of bytes downloaded to {@code target}.
     */
    public static long downloadFile(final AreaCodeInfoResponse response, final File target)
            throws IOException {
        Objects.requireNonNull(response, "response is null");
        final var header = response.getCmmMsgHeader();
        if (header == null) {
            throw new IllegalArgumentException("response.cmmMsgHeader is null: " + response);
        }
        if (!header.isSucceeded()) {
            throw new IllegalArgumentException("response.cmmMsgHeader is not succeeded: " + header);
        }
        final var responseFile = response.getFile();
        if (responseFile == null) {
            throw new IllegalArgumentException("response.file is null");
        }
        Objects.requireNonNull(target, "target is null");
        final var url = URI.create(responseFile).toURL();
        return HttpURLConnectionUtils.get(url, target);
    }

    // -----------------------------------------------------------------------------------------------------------------
    static void extract(final InputStream entryStream, final Consumer<? super String[]> headerConsumer,
                        final Consumer<? super String[]> rowConsumer)
            throws IOException {
        Objects.requireNonNull(entryStream, "entryStream is null");
        Objects.requireNonNull(headerConsumer, "headerConsumer is null");
        Objects.requireNonNull(rowConsumer, "rowConsumer is null");
        final var reader = new BufferedReader(new InputStreamReader(entryStream));
        final var first = reader.readLine();
        if (first.isEmpty()) {
            return;
        }
        headerConsumer.accept(first.split(DELIMITER));
        reader.lines()
                .map(l -> l.split(DELIMITER))
                .forEach(rowConsumer);
    }

    private static void extract(final InputStream stream, final Consumer<? super Map<String, String>> consumer)
            throws IOException {
        assert stream != null;
        assert consumer != null;
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
    static void extract(final InputStream stream,
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

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Extracts all tested entries from specified zip file to specified target directory.
     *
     * @param source    the zip file to be extracted.
     * @param target    the target directory into which the {@code source} is extracted.
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
            }
        }
    }

    /**
     * Extracts all entries from specified zip file to specified target directory.
     *
     * @param source the zip file to extract.
     * @param target the target directory into which the {@code source} is extracted.
     * @throws IOException if an I/O error occurs.
     */
    public static void extract(final File source, final File target) throws IOException {
        extract(source, target, n -> true);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AreaCodeInfoUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
