package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._retrieve_eng_address_service;

import com.github.jinahya.epost.openapi.proxy._common.AbstractRequestTypeTestUtils;
import com.github.jinahya.epost.openapi.proxy._common.AbstractSelfWrappingResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._SpringBootIT;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("/getRoadAddressSearch")
//@Import(
//        value = {
//                ValidationAutoConfiguration.class
//        }
//)
//@ContextConfiguration(
//        classes = {
//                Application.class
//        }
//)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class GetRoadAddressSearch_SpringBootIT
        extends _SpringBootIT {

    static RoadAddressEngSearchListResponse exchange(final WebTestClient client,
                                                     final RoadAddressEngSearchListRequest request,
                                                     final String cacheControl) {
        final var requestSpec = client.get().uri(b -> request.set(b).build());
        Optional.ofNullable(request.getAccept()).ifPresent(requestSpec::accept);
        Optional.ofNullable(cacheControl).ifPresent(cc -> {
            requestSpec.header(HttpHeaders.CACHE_CONTROL, cc);
        });
        // -------------------------------------------------------------------------------------------------------- when
        final var responseSpec = requestSpec.exchange();
        // -------------------------------------------------------------------------------------------------------- then
        responseSpec.expectStatus().isOk();
        Optional.ofNullable(request.getAccept()).ifPresent(a -> {
            responseSpec.expectHeader().contentTypeCompatibleWith(a);
        });
        final var responseBody = Optional.ofNullable(
                        responseSpec
                                .expectBody(RoadAddressEngSearchListResponse.class)
                                .returnResult()
                                .getResponseBody()
                )
                .map(AbstractSelfWrappingResponseType::get)
                .orElseThrow();
        assertThat(responseBody.getCmmMsgHeader()).isNotNull().satisfies(h -> {
            assertThat(h.isSucceeded()).isTrue();
            log.debug("responseTime: {}", h.getResponseTime());
            log.debug("responseTimeAsLocalDateTime: {}", h.getResponseTimeAsLocalDateTime());
        });
        responseBody.getRoadAddressEngSearchList().forEach(e -> {
            log.debug("roadEngFirstName: {}", e);
        });
        return responseBody;
    }

    static RoadAddressEngSearchListResponse verify(final RoadAddressEngSearchListResponse response,
                                                   final Validator validator) {
        assertThat(response).isNotNull().satisfies(r -> {
            assertThat(validator.validate(r)).isEmpty();
        });
        assertThat(response.getCmmMsgHeader()).isNotNull().satisfies(h -> {
            assertThat(h.isSucceeded()).isTrue();
            log.debug("responseTime: {}", h.getResponseTime());
            log.debug("responseTimeAsLocalDateTime: {}", h.getResponseTimeAsLocalDateTime());
        });
        return response;
    }

    // -----------------------------------------------------------------------------------------------------------------
    static Stream<RoadAddressEngSearchListRequest> getRequestStream() {
        return AbstractRequestTypeTestUtils.mapMediaType(
                Stream.of(
                        RoadAddressEngSearchListRequest.builder()
                                .stateEngName("Jeollanam-do")
                                .cityEngName("Naju-si")
                                .roadEngFirstName("D")
                                .roadEngName("Daeho-gil")
                                .keyword("45-40")
                                .countPerPage(2)
                                .currentPage(1)
                                .build()
                )
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @MethodSource({
            "getRequestStream"
    })
    @ParameterizedTest
    void __(final RoadAddressEngSearchListRequest request) {
        final var response = exchange(webTestClient(), request, "no-cache");
        verify(response, validator());
    }
}
