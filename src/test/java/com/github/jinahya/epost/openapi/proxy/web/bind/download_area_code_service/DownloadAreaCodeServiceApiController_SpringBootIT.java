package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiController_SpringBootIT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class DownloadAreaCodeServiceApiController_SpringBootIT
        extends _ApiController_SpringBootIT<DownloadAreaCodeServiceApiController, DownloadAreaCodeServiceApiService> {

    // ----------------------------------------------------------------------------------------------- /.../areaCodeInfo
    @DisplayName("GET /.../areaCodeInfo")
    @Test
    void readAreaCodeInfo__() {
        webTestClient()
                .get()
                .uri(b -> b.path(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_AREA_CODE_INFO).build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_NDJSON)
                .expectBodyList(new TypeReferences.EntityModelType<AreaCodeInfoResponse>() {
                })
                .consumeWith(r -> {
                    final var responseBody = r.getResponseBody();
                    assert responseBody != null;
                    assertThat(responseBody.stream().map(EntityModel::getContent))
                            .doesNotContainNull()
                            .allSatisfy(c -> {
                                assertValid(c);
                            });
                })
        ;
    }

    // -------------------------------------------------------------------------------------- /.../areaCodeInfo/{dwldSe}
    @DisplayName("GET /.../areaCodeInfo/{dwldSe}")
    @EnumSource(AreaCodeInfoRequest.DwldSe.class)
    @ParameterizedTest
    void readAreaCodeInfo__(final AreaCodeInfoRequest.DwldSe dwldSe) {
        webTestClient()
                .get()
                .uri(b -> b.path(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_DWLD_SE).build(dwldSe.text()))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaTypes.HAL_JSON)
                .expectBody(new TypeReferences.EntityModelType<AreaCodeInfoResponse>() {
                })
                .consumeWith(r -> {
                    final var responseBody = r.getResponseBody();
                    assert responseBody != null;
                    final var content = responseBody.getContent();
                    assertThat(content).isNotNull();
                    assertValid(content);
                })
        ;
    }

    // --------------------------------------------------------------------------------------- /.../{dwldSe}/fileContent
    @Disabled("takes too long, baby")
    @DisplayName("GET /.../areaCodeInfo/{dwldSe}/fileContent")
    @EnumSource(AreaCodeInfoRequest.DwldSe.class)
    @ParameterizedTest
    void readAreaCodeInfoFileContent__(final AreaCodeInfoRequest.DwldSe dwldSe) {
        // https://stackoverflow.com/a/41639534/330457
        LoggingSystem.get(ClassLoader.getSystemClassLoader())
                .setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.ERROR);
        webTestClient()
                .get()
                .uri(b -> b.path(_DownloadAreaCodeServiceApiConstants.REQUEST_URI_FILE_CONTENT).build(dwldSe.text()))
                .exchange()
                .expectStatus().isOk()
                .returnResult(DataBuffer.class)
                .consumeWith(r -> {
                    r.getResponseBody()
                            .doOnNext(DataBufferUtils::release)
                            .blockLast();
                })
        ;
    }
}