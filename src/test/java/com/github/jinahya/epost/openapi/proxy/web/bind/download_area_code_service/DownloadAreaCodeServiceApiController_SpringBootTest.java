package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractTypeUtils;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController_SpringBootTest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service._DownloadAreaCodeServiceApiConstants.REQUEST_URI_AREA_CODE_INFO;
import static com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service._DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

/**
 * Tests {@link DownloadAreaCodeServiceApiController}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@ContextConfiguration(classes = {
        DownloadAreaCodeServiceApiController.class
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class DownloadAreaCodeServiceApiController_SpringBootTest
        extends _ApiController_SpringBootTest<DownloadAreaCodeServiceApiController, DownloadAreaCodeServiceApiService> {

    // -----------------------------------------------------------------------------------------------------------------
    @BeforeEach
    void a() {
        when(serviceInstance().exchange(notNull())).thenAnswer(i -> {
            final var request = i.getArgument(0, AreaCodeInfoRequest.class);
            final var dwldSe = request.getDwldSe();
            try (final var resource = AreaCodeInfoResponse.class.getResourceAsStream(
                    "getAreaCodeInfo_response" + dwldSe.text() + ".xml")) {
                final var response = AbstractTypeUtils.unmarshalNoNamespacedInstance(
                        AreaCodeInfoResponse.class, resource);
                return Mono.just(response.requestInstance(request).get());
            }
        });
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link DownloadAreaCodeServiceApiController#readAreaCodeInfo(ServerWebExchange)} method.
     */
    @DisplayName("GET " + REQUEST_URI_AREA_CODE_INFO)
    @Test
    void __() {
        // ------------------------------------------------------------------------------------------------------- given
        final var exchange = MockServerWebExchange.from(
                MockServerHttpRequest
                        .get(REQUEST_URI_AREA_CODE_INFO)
                        .build()
        );
        // -------------------------------------------------------------------------------------------------------- when
        final var result = controllerInstance()
                .readAreaCodeInfo(exchange)
                .switchIfEmpty(Mono.error(new RuntimeException("empty")));
        // -------------------------------------------------------------------------------------------------------- then
        result.doOnNext(this::validate)
                .blockLast();

        final var duration =
                StepVerifier.create(controllerInstance().readAreaCodeInfo(exchange))
                        .expectNextMatches(e -> e.getContent().getRequestDwldSe() == AreaCodeInfoRequest.DwldSe._1)
                        .expectNextMatches(e -> e.getContent().getRequestDwldSe() == AreaCodeInfoRequest.DwldSe._2)
                        .expectNextMatches(e -> e.getContent().getRequestDwldSe() == AreaCodeInfoRequest.DwldSe._3)
                        .expectNextMatches(e -> e.getContent().getRequestDwldSe() == AreaCodeInfoRequest.DwldSe._4)
                        .expectComplete()
                        .verify();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests
     * {@link DownloadAreaCodeServiceApiController#readAreaCodeInfo(ServerWebExchange, AreaCodeInfoRequest.DwldSe)}
     * method.
     *
     * @param dwldSe the value for {@code dwldSe} parameter.
     */
    @DisplayName("GET " + REQUEST_URI_DWLD_SE)
    @EnumSource(AreaCodeInfoRequest.DwldSe.class)
    @ParameterizedTest
    void __(final AreaCodeInfoRequest.DwldSe dwldSe) {
        // ------------------------------------------------------------------------------------------------------- given
        final var exchange = MockServerWebExchange.from(
                MockServerHttpRequest
                        .get(REQUEST_URI_DWLD_SE, dwldSe.text())
                        .build()
        );
        // -------------------------------------------------------------------------------------------------------- when
        final var result = controllerInstance()
                .readAreaCodeInfo(exchange, dwldSe)
                .switchIfEmpty(Mono.error(new RuntimeException("empty")));
        // -------------------------------------------------------------------------------------------------------- then
        result.doOnNext(this::validate)
                .block();

        final var duration = StepVerifier.create(controllerInstance().readAreaCodeInfo(exchange, dwldSe))
                .expectNextMatches(e -> e.getContent().getRequestDwldSe() == dwldSe)
                .expectComplete()
                .verify();
    }
}