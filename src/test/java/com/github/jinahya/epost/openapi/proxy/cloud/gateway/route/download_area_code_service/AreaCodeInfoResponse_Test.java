package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseTypeTest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractTypeUtils;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class AreaCodeInfoResponse_Test
        extends AbstractPairedResponseTypeTest<AreaCodeInfoResponse, AreaCodeInfoRequest> {

    AreaCodeInfoResponse_Test() {
        super(AreaCodeInfoResponse.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    protected SingleTypeEqualsVerifierApi<AreaCodeInfoResponse> __equals(
            final SingleTypeEqualsVerifierApi<AreaCodeInfoResponse> verifierApi) {
        return super.__equals(verifierApi);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<String> getXmlResNameStream() {
        return Stream.of(
                "getAreaCodeInfo_response1.xml",
                "getAreaCodeInfo_response2.xml",
                "getAreaCodeInfo_response3.xml",
                "getAreaCodeInfo_response4.xml"
        );
    }

    @MethodSource({"getXmlResNameStream"})
    @ParameterizedTest
    void __xml(final String resName) throws Throwable {
        final var unmarshalled = applyResourceAsStreamChecked(
                resName,
                r -> AbstractTypeUtils.unmarshalNoNamespacedInstance(AreaCodeInfoResponse.class, r)
        );
        verifyValid(unmarshalled);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<String> getJsonResNameStream() {
        return Stream.of(
                "getAreaCodeInfo_response1.json",
                "getAreaCodeInfo_response2.json",
                "getAreaCodeInfo_response3.json",
                "getAreaCodeInfo_response4.json"
        );
    }

    @MethodSource({"getJsonResNameStream"})
    @ParameterizedTest
    void __json(final String resName) throws Throwable {
        final var unmarshalled = applyResourceAsStreamChecked(
                resName,
                r -> AbstractTypeUtils.deserializeInstance(AreaCodeInfoResponse.class, r)
        );
        verifyValid(unmarshalled.get());
    }
}
