package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractPairedResponseType;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.AbstractResponseElementType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

//@JsonRootName(StateEngListResponse.ROOT_NAME)
@XmlRootElement(name = StateEngListResponse.ROOT_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateEngListResponse
        extends AbstractPairedResponseType<StateEngListResponse, StateEngListRequest> {

    @Serial
    private static final long serialVersionUID = 4043100661470226062L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String ROOT_NAME = "StateEngListResponse";

    // -----------------------------------------------------------------------------------------------------------------
    private static final String NAME_STATE_ENG_LIST = "stateEngList";

    @Setter
    @Getter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    public static class StateEngList
            extends AbstractResponseElementType<StateEngList, StateEngListResponse> {

        @Serial
        private static final long serialVersionUID = 1923480486008916847L;

        // -----------------------------------------------------------------------------------------------------------------
        public static StateEngList of(final String stateEngName) {
            final var instance = new StateEngList();
            instance.setStateEngName(stateEngName);
            return instance;
        }

        // -------------------------------------------------------------------------------------------------------------
//        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @JsonProperty(required = true)
        @XmlElement(required = true)
        private String stateEngName;
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    /**
     * Creates a new instance.
     */
    public StateEngListResponse() {
        super(StateEngListRequest.class);
    }

    // ---------------------------------------------------------------------------------------------- super.cmmMsgHeader

    // --------------------------------------------------------------------------------------------------- super.wrapped
    @JsonProperty(ROOT_NAME)
    @Override
    public StateEngListResponse getWrapped() {
        return super.getWrapped();
    }

    // -----------------------------------------------------------------------------------------------------------------
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty(NAME_STATE_ENG_LIST)
    @XmlElement(name = NAME_STATE_ENG_LIST)
    private List<@Valid @NotNull StateEngList> stateEngList;
}
