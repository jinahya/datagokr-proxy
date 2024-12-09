package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriBuilder;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AreaCodeInfoRequest
        extends AbstractPairedRequestType<AreaCodeInfoRequest, AreaCodeInfoResponse> {

    @Serial
    private static final long serialVersionUID = 7432210876083090440L;

    // ---------------------------------------------------------------------------------------------------------- dwldSe
    public static final String PROPERTY_DWLD_SE = "dwldSe";

    /**
     * 우편번호 전체. The value is {@value}.
     */
    public static final String DWLD_SE_1 = "1";

    /**
     * 우편번호 변경분. The value is {@value}.
     */
    public static final String DWLD_SE_2 = "2";

    /**
     * 범위 주소. The value is {@value}.
     */
    public static final String DWLD_SE_3 = "3";

    /**
     * 사서함 주소. The value is {@value}.
     */
    public static final String DWLD_SE_4 = "4";

    @XmlEnum(String.class)
    @SuppressWarnings({
            "java:S115" // _1(...)
    })
    public enum DwldSe {

        /**
         * 우편번호 전체.
         */
        @XmlEnumValue(DWLD_SE_1)
        _1(DWLD_SE_1),

        /**
         * 우편번호 변경분.
         */
        @XmlEnumValue(DWLD_SE_2)
        _2(DWLD_SE_2),

        /**
         * 범위 주소.
         */
        @XmlEnumValue(DWLD_SE_3)
        _3(DWLD_SE_3),

        /**
         * 사서함 주소.
         */
        @XmlEnumValue(DWLD_SE_4)
        _4(DWLD_SE_4);

        // -------------------------------------------------------------------------------------------------------------

        /**
         * Finds the constant whose {@link #value() value} property matches specified value.
         *
         * @param value the value of {@link #value() value} property to match.
         * @return the constant whose {@link #value() value} property matches {@code value}.
         */
        public static DwldSe valueOfValue(final String value) {
            Objects.requireNonNull(value, "value is null");
            for (final DwldSe v : values()) {
                if (Objects.equals(v.value(), value)) {
                    return v;
                }
            }
            throw new IllegalArgumentException("no value for '" + value + "'");
        }

        // ------------------------------------------------------------------------------------------------ CONSTRUCTORS
        DwldSe(final String value) {
            this.value = Objects.requireNonNull(value, "value is null");
        }

        // ------------------------------------------------------------------------------------------------------- value

        /**
         * Returns the {@code value} of this constant.
         *
         * @return the {@code value} of this constant.
         */
        @JsonValue
        public String value() {
            return value;
        }

        // -------------------------------------------------------------------------------------------------------------
        private final String value;
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    /**
     * Creates a new instance with specified {@value #PROPERTY_DWLD_SE} property value.
     *
     * @param dwldSe the value for the {@value #PROPERTY_DWLD_SE} property.
     * @return a new instance with {@code dwldSe}.
     */
    public static AreaCodeInfoRequest of(final String dwldSe) {
        return of(AreaCodeInfoRequest::new)
                .dwldSe(dwldSe);
    }

    public static AreaCodeInfoRequest of(final DwldSe dwldSe) {
        Objects.requireNonNull(dwldSe, "dwldSe is null");
        return of(dwldSe.value());
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final BiConsumer<? super AreaCodeInfoRequest, ? super UriBuilder> URI_BUILDER = (s, b) -> {
        b.path(_DownloadAreaCodeServiceConstants.REQUEST_URI_GET_AREA_CODE_INFO)
                .queryParam(_DownloadAreaCodeServiceConstants.PARAM_NAME_DWLD_SE, s.getDwldSe())
        ;
    };

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    public AreaCodeInfoRequest() {
        super(AreaCodeInfoResponse.class);
        setUriConfigurer(
                URI_BUILDER,
                true
        );
    }

    // ---------------------------------------------------------------------------------------------------------- dwldSe

    /**
     * Replaces current value of {@value #PROPERTY_DWLD_SE} property with specified value, and returns this instance.
     *
     * @param dwldSe new value for the {@value #PROPERTY_DWLD_SE} property.
     * @return this instance.
     */
    public AreaCodeInfoRequest dwldSe(final String dwldSe) {
        setDwldSe(dwldSe);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    // TODO: Use the enum!
    private String dwldSe;
}
