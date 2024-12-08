package com.github.jinahya.epost.openapi.proxy.web.bind;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@SuppressWarnings({
        "java;S101", // Class names should comply with a naming convention
        "java:S119"
})
// https://github.com/spring-projects/spring-boot/issues/41862
abstract class __ApiController {

    // -----------------------------------------------------------------------------------------------------------------
    protected static void beforeCommit(final ServerHttpResponse response,
                                       final Consumer<? super ServerHttpResponse> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        response.beforeCommit(() -> {
            consumer.accept(response);
            return Mono.empty();
        });
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
}
