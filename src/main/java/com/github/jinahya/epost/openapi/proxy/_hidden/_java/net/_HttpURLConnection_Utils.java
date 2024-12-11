package com.github.jinahya.epost.openapi.proxy._hidden._java.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
public final class _HttpURLConnection_Utils {

    private static final int RESPONSE_CODE_200_OK = 200;

    private static final int RESPONSE_CODE_301_MOVED_PERMANENTLY = 301;

    private static final int RESPONSE_CODE_302_FOUND = 302;

    private static final int RESPONSE_CODE_303_SEE_OTHER = 303;

    private static boolean is3xx(final int responseCode) {
        return responseCode == RESPONSE_CODE_301_MOVED_PERMANENTLY
                || responseCode == RESPONSE_CODE_302_FOUND
                || responseCode == RESPONSE_CODE_303_SEE_OTHER;
    }

    /**
     * {@code GET}s content of a resource located by specified URL to specified target file.
     *
     * @param source the URL to {@code GET}.
     * @param target the target file to which the content is saved.
     * @return the number of bytes written to the {@code target}.
     * @throws IOException if an I/O error occurs.
     */
    public static long get(final URL source, final File target) throws IOException {
        Objects.requireNonNull(source, "source is null");
        Objects.requireNonNull(target, "target is null");
        final var connection = source.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);
        if (connection instanceof HttpURLConnection hurlc) {
            hurlc.setRequestMethod("GET");
            hurlc.setConnectTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(1L)));
            hurlc.setReadTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(2L)));
            final var responseCode = hurlc.getResponseCode();
            if (is3xx(responseCode)) {
                final var location = hurlc.getHeaderField("Location"); // equalsIgnoreCase checked
                if (location == null) {
                    throw new IOException("3XX with no Location header");
                }
                return get(URI.create(location).toURL(), target);
            }
            if (responseCode != RESPONSE_CODE_200_OK) {
                throw new IOException("unexpected response code: " + responseCode + " for " + source);
            }
        }
        try {
            connection.connect();
            var bytes = 0L;
            final var size = 8192;
            try (var input = new BufferedInputStream(connection.getInputStream(), size);
                 var output = new FileOutputStream(target)) {
                final var buf = new byte[size];
//                final var b = new ArrayList<Integer>();
                for (int r; (r = input.read(buf)) != -1; ) {
                    output.write(buf, 0, r);
                    bytes += r;
//                    b.add(r);
                }
//                System.out.println("stats: " + b.stream().mapToInt(Integer::intValue).summaryStatistics());
                output.flush();
            }
            return bytes;
        } finally {
            if (connection instanceof HttpURLConnection hurlc) {
                hurlc.disconnect();
            }
        }
    }

    private _HttpURLConnection_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
