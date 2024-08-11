package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.__common.AbstractPairedRequestType;
import jakarta.validation.constraints.NotBlank;
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
public class CityEngListRequest
        extends AbstractPairedRequestType<CityEngListRequest, CityEngListResponse> {

    @Serial
    private static final long serialVersionUID = 2981550532310902459L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    public static CityEngListRequest of(final String serviceKey, final String stateEngName) {
        final CityEngListRequest instance = new CityEngListRequest();
        instance.setServiceKey(serviceKey);
        instance.setStateEngName(stateEngName);
        return instance;
    }

    public static CityEngListRequest from(final StateEngListRequest stateEngListRequest,
                                          final StateEngListResponse.StateEngList stateEngList) {
        Objects.requireNonNull(stateEngListRequest, "stateEngListRequest is null");
        Objects.requireNonNull(stateEngList, "stateEngList is null");
        final CityEngListRequest instance = new CityEngListRequest();
        instance.setServiceKey(stateEngListRequest.getServiceKey());
        instance.setStateEngName(stateEngList.getStateEngName());
        return instance;
    }

    static CityEngListRequest from(final StateEngListResponse.StateEngList stateEngList) {
        return from(
                stateEngList.getParent().getRequestInstance(),
                stateEngList
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final BiConsumer<? super CityEngListRequest, ? super UriBuilder> URI_CONSUMER = (s, b) -> {
        b.path(_RetrieveEngAddressServiceConstants.REQUEST_URI_GET_CITY_LIST)
                .queryParam(_RetrieveEngAddressServiceConstants.PARAM_STATE_ENG_NAME, s.getStateEngName())
        ;
    };

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    public CityEngListRequest() {
        super(CityEngListResponse.class);
        setUriConsumer(
                URI_CONSUMER,
                true
        );
    }

    // ---------------------------------------------------------------------------------------------------- stateEngName
    public CityEngListRequest stateEngName(final String stateEngName) {
        setStateEngName(stateEngName);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotBlank
    private String stateEngName;
}
