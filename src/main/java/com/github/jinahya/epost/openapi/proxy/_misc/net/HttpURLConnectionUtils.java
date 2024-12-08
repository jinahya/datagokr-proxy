package com.github.jinahya.epost.openapi.proxy._misc.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class HttpURLConnectionUtils {

    private static final int RESPONSE_CODE_200_OK = 200;

    private static final int RESPONSE_CODE_301_MOVED_PERMANENTLY = 301;

    private static final int RESPONSE_CODE_302_FOUND = 302;

    private static final int RESPONSE_CODE_303_SEE_OTHER = 303;

    private static boolean is3xx(final int responseCode) {
        return responseCode == RESPONSE_CODE_301_MOVED_PERMANENTLY
                || responseCode == RESPONSE_CODE_302_FOUND
                || responseCode == RESPONSE_CODE_303_SEE_OTHER;
    }

    public static long get(final URL source, final File target) throws IOException {
        Objects.requireNonNull(source, "source is null");
        Objects.requireNonNull(target, "target is null");
        final var connection = source.openConnection();
        if (connection instanceof HttpURLConnection httpurlc) {
            httpurlc.setRequestMethod("GET");
            httpurlc.setConnectTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(1L)));
            httpurlc.setReadTimeout(Math.toIntExact(TimeUnit.SECONDS.toMillis(2L)));
            final var responseCode = httpurlc.getResponseCode();
            if (is3xx(responseCode)) {
                final var location = httpurlc.getHeaderField("Location");
                if (location == null) {
                    throw new IOException("3XX with no Location header");
                }
                return get(URI.create(location).toURL(), target);
            }
            if (responseCode != RESPONSE_CODE_200_OK) {
                throw new IOException("unexpected response code: " + responseCode);
            }
        }
        var bytes = 0L;
        try {
            connection.connect();
            try (var input = connection.getInputStream();
                 var output = new FileOutputStream(target)) {
                final var buffer = new byte[8192];
                for (int r; (r = input.read(buffer)) != -1; ) {
                    output.write(buffer, 0, r);
                    bytes += r;
                }
                output.flush();
            }
        } finally {
            if (connection instanceof HttpURLConnection httpurlc) {
                httpurlc.disconnect();
            }
        }
        return bytes;
    }

    private HttpURLConnectionUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
