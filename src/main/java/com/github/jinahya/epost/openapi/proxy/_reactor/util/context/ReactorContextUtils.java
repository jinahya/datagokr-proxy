package com.github.jinahya.epost.openapi.proxy._reactor.util.context;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.net.MalformedURLException;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * A utility class for {@link Context}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
// https://github.com/reactor/reactor-core/issues/2572
// https://www.pkslow.com/docs/en/spring-webflux-get-request/#webfilter-fetch-and-save
// https://gist.github.com/Elyorbe/c7cc5f28d0fced86dbd054dce0a9e206
public final class ReactorContextUtils {

    // -----------------------------------------------------------------------------------------------------------------
    private static final Class<ServerHttpRequest> KEY_REQUEST = ServerHttpRequest.class;

    /**
     * Puts specified request to specified context.
     *
     * @param context the context.
     * @param request the request to put.
     * @return a new context to which the {@code request} put.
     * @see Context#put(Object, Object)
     * @see #getRequest()
     */
    public static Context putRequest(final Context context, final ServerHttpRequest request) {
        return context.put(KEY_REQUEST, request);
    }

    /**
     * Returns the {@code request} added via {@link #putRequest(Context, ServerHttpRequest)}.
     *
     * @return the {@code request} added via {@link #putRequest(Context, ServerHttpRequest)}
     * @see Context#get(Object)
     * @see #putRequest(Context, ServerHttpRequest)
     */
    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(
                c -> Mono.just(
                        c.get(KEY_REQUEST) // throws if key not found!
                )
        );
    }

    public static Mono<String> getRequestBaseUrl() {
        return getRequest()
                .map(r -> {
                    try {
                        final var url = r.getURI().toURL();
                        return url.getProtocol() + "://" + url.getAuthority();
                    } catch (final MalformedURLException murle) {
                        throw new RuntimeException("failed to get url from " + r, murle);
                    }
                });
    }

    public static Mono<UriComponentsBuilder> getUriComponentsBuilderFromRequestBaseUrl() {
        return getRequestBaseUrl().map(UriComponentsBuilder::fromHttpUrl);
    }

    public static Mono<UriComponentsBuilder> applyUriComponentsBuilderFromRequestBaseUrl(
            final UnaryOperator<UriComponentsBuilder> operator) {
        Objects.requireNonNull(operator, "operator is null");
        return getUriComponentsBuilderFromRequestBaseUrl()
                .map(operator);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private ReactorContextUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
