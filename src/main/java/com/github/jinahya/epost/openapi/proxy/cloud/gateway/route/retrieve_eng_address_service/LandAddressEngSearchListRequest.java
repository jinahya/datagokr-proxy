package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedRequestType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.PaginatedRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._RouteConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.util.UriBuilder;

import java.io.Serial;
import java.util.Optional;
import java.util.function.BiConsumer;

@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LandAddressEngSearchListRequest
        extends AbstractPairedRequestType<LandAddressEngSearchListRequest, LandAddressEngSearchListResponse>
        implements PaginatedRequest<LandAddressEngSearchListRequest> {

    @Serial
    private static final long serialVersionUID = -1827246138318731047L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    public static LandAddressEngSearchListRequest of(final @Nullable String serviceKey, final String stateEngName,
                                                     final String cityEngName,
                                                     @Nullable final String districtEngFirstName,
                                                     final String districtEngName, final @Nullable String keyword,
                                                     final Integer countPerPage, final Integer currentPage) {
        final LandAddressEngSearchListRequest instance = new LandAddressEngSearchListRequest();
        instance.setServiceKey(serviceKey);
        instance.setStateEngName(stateEngName);
        instance.setCityEngName(cityEngName);
        instance.setDistrictEngFirstName(districtEngFirstName);
        instance.setDistrictEngName(districtEngName);
        instance.setKeyword(keyword);
        instance.setCountPerPage(countPerPage);
        instance.setCurrentPage(currentPage);
        return instance;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final BiConsumer<? super LandAddressEngSearchListRequest, ? super UriBuilder> URI_BUILDER =
            (s, b) -> {
                b.path(_RetrieveEngAddressServiceConstants.REQUEST_URI_GET_LAND_ADDRESS_SEARCH)
                        .queryParam(_RetrieveEngAddressServiceConstants.PARAM_STATE_ENG_NAME, s.stateEngName)
                        .queryParam(_RetrieveEngAddressServiceConstants.PARAM_CITY_ENG_NAME, s.cityEngName)
                        .queryParamIfPresent(_RetrieveEngAddressServiceConstants.PARAM_DISTRICT_ENG_FIRST_NAME,
                                             Optional.ofNullable(s.districtEngFirstName))
                        .queryParam(_RetrieveEngAddressServiceConstants.PARAM_DISTRICT_ENG_NAME, s.districtEngName)
                        .queryParamIfPresent(_RetrieveEngAddressServiceConstants.PARAM_KEYWORD,
                                             Optional.ofNullable(s.keyword))
                        .queryParam(_RouteConstants.REQUEST_PARAM_COUNT_PER_PAGE, s.countPerPage)
                        .queryParam(_RouteConstants.REQUEST_PARAM_CURRENT_PAGE, s.currentPage);
            };

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    public LandAddressEngSearchListRequest() {
        super(LandAddressEngSearchListResponse.class);
        setUriConfigurer(
                URI_BUILDER,
                true
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String stateEngName;

    @NotBlank
    private String cityEngName;

    @Size(max = 1)
//    @NotBlank
    private String districtEngFirstName;

    @NotBlank
    private String districtEngName;

    // -----------------------------------------------------------------------------------------------------------------
    private String keyword;

    // -----------------------------------------------------------------------------------------------------------------
    @Positive
    @NotNull
    private Integer countPerPage;

    @Positive
    @NotNull
    private Integer currentPage;
}
