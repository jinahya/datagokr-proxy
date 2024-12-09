package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractRequestTypeTestUtils;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._RouteSpringBootIT;
import com.github.jinahya.epost.openapi.proxy.web.reactive.function.client.WebClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

@DisplayName("/getAreaCodeInfo")
@Slf4j
class GetAreaCodeInfo_SpringBootIT
        extends _RouteSpringBootIT {

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<AreaCodeInfoRequest> getRequestStream() {
        return AbstractRequestTypeTestUtils.mapMediaType(
                Stream.of(AreaCodeInfoRequest.DwldSe.values())
//                        .map(AreaCodeInfoRequest.DwldSe::value)
                        .<AreaCodeInfoRequest>map(AreaCodeInfoRequest::of)
        );
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // -----------------------------------------------------------------------------------------------------------------
    @Disabled
    @MethodSource({
            "getRequestStream"
    })
    @ParameterizedTest
    void __(final AreaCodeInfoRequest request) {
        final var response = exchange(request);
        assertValid(response);
        assertSucceeded(response);
    }

    @Disabled("takes too long")
    @Test
    void __() {
        Flux.fromStream(getRequestStream())
                .doOnNext(r -> {
                    log.debug("request: {}", r);
                })
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .map(this::exchange)
                .doOnNext(r -> {
                    assertValid(r);
                    assertSucceeded(r);
                })
                .doOnNext(r -> {
                    log.debug("response: {}", r);
                })
                .map(AreaCodeInfoResponse::getFile)
                .doOnNext(f -> {
                    log.debug("file: {}", f);
                })
                .flatMap(f -> {
                    final var flags = new HashMap<String, Boolean>();
                    return WebClientUtils.download(
                            f,
                            p -> {
                                try (var stream = new FileInputStream(p.toFile())) {
                                    AreaCodeInfoUtils.extract(
                                            stream,
                                            (n, m) -> {
                                                if (flags.compute(n, (k, v) -> v == null)) {
                                                    log.debug("n: {}, m: {}", n, m);
                                                }
                                            }
                                    );
                                } catch (final IOException ioe) {
                                    throw new RuntimeException(ioe);
                                }
                            }
                    );
                })
                .sequential()
                .blockLast()
        ;
    }
}
