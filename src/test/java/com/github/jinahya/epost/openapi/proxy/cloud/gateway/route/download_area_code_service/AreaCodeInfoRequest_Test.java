package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestTypeTest;
import nl.jqno.equalsverifier.api.SingleTypeEqualsVerifierApi;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AreaCodeInfoRequest_Test
        extends AbstractPairedRequestTypeTest<AreaCodeInfoRequest, AreaCodeInfoResponse> {

    AreaCodeInfoRequest_Test() {
        super(AreaCodeInfoRequest.class, AreaCodeInfoResponse.class);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    protected SingleTypeEqualsVerifierApi<AreaCodeInfoRequest> __equals(
            final SingleTypeEqualsVerifierApi<AreaCodeInfoRequest> verifierApi) {
        return super.__equals(verifierApi);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Nested
    class DwldSeTest {

        @Test
        void valueOfValue_IllegalArgumentException_ValueIsUnknown() {
            assertThatThrownBy(() -> AreaCodeInfoRequest.DwldSe.valueOfValue("unknown"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @EnumSource(AreaCodeInfoRequest.DwldSe.class)
        @ParameterizedTest
        void valueOfValue__(final AreaCodeInfoRequest.DwldSe expected) {
            final var actual = AreaCodeInfoRequest.DwldSe.valueOfValue(expected.text());
            assertThat(actual).isSameAs(expected);
        }
    }
}
