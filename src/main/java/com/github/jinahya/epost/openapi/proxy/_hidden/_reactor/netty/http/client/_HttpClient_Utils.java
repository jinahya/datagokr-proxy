package com.github.jinahya.epost.openapi.proxy._hidden._reactor.netty.http.client;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
public final class _HttpClient_Utils {

//    public static <R> R get(final String uri, final Function<? super ByteBufFlux, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        return function.apply(
//                HttpClient.create()
//                        .followRedirect(true)
//                        .get()
//                        .uri(uri)
//                        .responseContent()
//        );
//    }

    public static <R> Mono<R> get(final String uri, final Function<? super InputStream, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return HttpClient.create()
                .get()
                .uri(uri)
                .responseContent() // ByteBufFlux
                .aggregate() // ByteBufMono
                .asInputStream() // Mono<InputStream>
                .map(v -> {
                    try (v) {
                        return function.apply(v);
                    } catch (final IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                });
    }

//    public static <R> Mono<R> get(final String uri, final Function<? super InputStream, ? extends R> function) {
//        Objects.requireNonNull(function, "function is null");
//        return HttpClient.create()
//                .get()
//                .uri(uri)
//                .responseContent() // ByteBufFlux
//                .aggregate() // ByteBufMono
//                .asInputStream() // Mono<InputStream>
//                .map(v -> {
//                    try (v) {
//                        return function.apply(v);
//                    } catch (final IOException ioe) {
//                        throw new RuntimeException(ioe);
//                    }
//                });
//    }

    // -----------------------------------------------------------------------------------------------------------------
    private _HttpClient_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
