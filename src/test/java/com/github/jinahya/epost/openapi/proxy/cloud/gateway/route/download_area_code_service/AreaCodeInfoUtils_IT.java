package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy._TestConstants;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.CmmMsgHeader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Slf4j
class AreaCodeInfoUtils_IT {

    private static Stream<String> getFileStream() {
        return AreaCodeInfoResponse_TestUtils.getFileStream();
    }

    @Tag(_TestConstants.TAG_LONG_RUNNING)
    @Disabled("takes to long, baby")
    @DisplayName("extract(stream, consumer)")
    @MethodSource({"getFileStream"})
    @ParameterizedTest
    void download__(final String file, @TempDir final File tempDir) throws IOException {
        // ------------------------------------------------------------------------------------------------------- given
        final var response = mock(AreaCodeInfoResponse.class);
        final var cmmMsgHeader = mock(CmmMsgHeader.class);
        given(cmmMsgHeader.isSucceeded()).willReturn(true);
        given(response.getCmmMsgHeader()).willReturn(cmmMsgHeader);
        given(response.getFile()).willReturn(file);
        final var target = File.createTempFile("tmp", null, tempDir);
        // -------------------------------------------------------------------------------------------------------- when
        final var bytes = AreaCodeInfoUtils.downloadFile(response, target);
        // -------------------------------------------------------------------------------------------------------- then
        log.debug("bytes: {}", bytes);
    }
}
