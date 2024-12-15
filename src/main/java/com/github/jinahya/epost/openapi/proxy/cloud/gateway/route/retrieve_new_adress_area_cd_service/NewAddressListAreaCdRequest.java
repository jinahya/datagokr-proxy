package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.PaginatedRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._RouteConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriBuilder;

import java.io.Serial;
import java.util.Objects;
import java.util.function.BiConsumer;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewAddressListAreaCdRequest
        extends AbstractPairedRequestType<NewAddressListAreaCdRequest, NewAddressListAreaCdResponse>
        implements PaginatedRequest<NewAddressListAreaCdRequest> {

    @Serial
    private static final long serialVersionUID = -4766029866023904965L;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String SEARCH_SE_DONG = "dong";

    public static final String SEARCH_SE_ROAD = "road";

    public static final String SEARCH_SE_POST = "post";

    //    @Schema(enumAsRef = true)
    @XmlEnum(String.class)
    public enum SearchSe {

        /**
         * 동(읍/면)명.
         */
        @XmlEnumValue(SEARCH_SE_DONG)
        DONG(SEARCH_SE_DONG),

        /**
         * 도로명[default].
         */
        @XmlEnumValue(SEARCH_SE_ROAD)
        ROAD(SEARCH_SE_ROAD),

        /**
         * 우편번호
         */
        @XmlEnumValue(SEARCH_SE_POST)
        POST(SEARCH_SE_POST);

        // -------------------------------------------------------------------------------------------------------------
        public static SearchSe valueOfText(final String text) {
            Objects.requireNonNull(text, "text is null");
            for (final SearchSe value : values()) {
                if (value.text.equals(text)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("no value for '" + text + "'");
        }

        // -------------------------------------------------------------------------------------------------------------
        SearchSe(final String text) {
            this.text = text;
        }

        // -------------------------------------------------------------------------------------------------------------
        @JsonValue
        public String text() {
            return text;
        }

        // -------------------------------------------------------------------------------------------------------------
        private final String text;
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    public static NewAddressListAreaCdRequest of(final String serviceKey, final SearchSe searchSe, final String srchwrd,
                                                 final Integer countPerPage, final Integer currentPage) {
        final var instance = AbstractRequestType.of(NewAddressListAreaCdRequest::new, serviceKey);
        instance.setSearchSe(searchSe);
        instance.setSrchwrd(srchwrd);
        instance.setCountPerPage(countPerPage);
        instance.setCurrentPage(currentPage);
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final BiConsumer<? super NewAddressListAreaCdRequest, ? super UriBuilder> URI_BUILDER = (s, b) -> {
        b.path(_RetrieveNewAdressAreaCdServiceConstants.REQUEST_URI_GET_NEW_ADDRESS_LIST_AREA_CD)
                .queryParam(_RetrieveNewAdressAreaCdServiceConstants.PARAM_SEARCH_SE, s.getSearchSe().text())
                .queryParam(_RetrieveNewAdressAreaCdServiceConstants.PARAM_SRCHWRD, s.getSrchwrd())
                .queryParam(_RouteConstants.REQUEST_PARAM_COUNT_PER_PAGE, s.getCountPerPage())
                .queryParam(_RouteConstants.REQUEST_PARAM_CURRENT_PAGE, s.getCurrentPage())
        ;
    };

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    public NewAddressListAreaCdRequest() {
        super(NewAddressListAreaCdResponse.class);
        setUriConfigurer(
                URI_BUILDER,
                true
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
//    @Parameter(description = "검색구분; dong: 동(읍/면)명, road:도로명, post: 우편번호", required = true)
    @NotNull
    private SearchSe searchSe;

    //    @Parameter(description = "검색어", required = true)
    @NotBlank
    private String srchwrd;

    // -----------------------------------------------------------------------------------------------------------------
//    @Parameter(description = _RouteConstants.PARAMETER_DESCRIPTION_COUNT_PER_PAGE, required = true)
    @Positive
    @NotNull
    private Integer countPerPage;

    //    @Parameter(description = _RouteConstants.PARAMETER_DESCRIPTION_CURRENT_PAGE, required = true)
    @Positive
    @NotNull
    private Integer currentPage;
}
