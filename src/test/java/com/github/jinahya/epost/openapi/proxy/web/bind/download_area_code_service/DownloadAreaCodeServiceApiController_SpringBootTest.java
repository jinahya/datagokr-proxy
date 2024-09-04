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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service._DownloadAreaCodeServiceApiConstants.REQUEST_URI_AREA_CODE_INFO;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class DownloadAreaCodeServiceApiController_SpringBootTest
        extends _ApiController_SpringBootTest<
                DownloadAreaCodeServiceApiController, DownloadAreaCodeServiceApiService> {

    // -----------------------------------------------------------------------------------------------------------------
    @BeforeEach
    void a() {
        when(service().exchange(notNull())).thenAnswer(i -> {
            final var request = i.getArgument(0, AreaCodeInfoRequest.class);
            final var dwldSe = request.getDwldSe();
            try (final var resource = AreaCodeInfoResponse.class.getResourceAsStream(
                    "getAreaCodeInfo_response" + dwldSe + ".xml")) {
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
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Tests {@link DownloadAreaCodeServiceApiController#readAreaCodeInfo(ServerWebExchange, String)} method.
     *
     * @param dwldSe the value for {@code dwldSe} parameter.
     */
    @DisplayName("GET " + _DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE)
    @EnumSource(AreaCodeInfoRequest.DwldSe.class)
    @ParameterizedTest
    void __(final AreaCodeInfoRequest.DwldSe dwldSe) {
        // ------------------------------------------------------------------------------------------------------- given
        final var exchange = MockServerWebExchange.from(
                MockServerHttpRequest
                        .get(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE, dwldSe.value())
                        .build()
        );
        // -------------------------------------------------------------------------------------------------------- when
        final var result = controllerInstance()
                .readAreaCodeInfo(exchange, dwldSe.value())
                .switchIfEmpty(Mono.error(new RuntimeException("empty")));
        // -------------------------------------------------------------------------------------------------------- then
        result.doOnNext(this::validate)
                .block();
    }
}