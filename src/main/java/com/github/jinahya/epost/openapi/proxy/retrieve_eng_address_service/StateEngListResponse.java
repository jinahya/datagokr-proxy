package com.github.jinahya.epost.openapi.proxy.retrieve_eng_address_service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.jinahya.epost.openapi.proxy._common.AbstractSelfWrappingResponseType;
import com.github.jinahya.epost.openapi.proxy._common.AbstractType;
import com.github.jinahya.epost.openapi.proxy._common.AbstractTypeUtils;
import com.github.jinahya.epost.openapi.proxy._common.CmmMsgHeader;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.io.Serial;
import java.util.List;

@JsonRootName(StateEngListResponse.ROOT_NAME)
@XmlRootElement(name = StateEngListResponse.ROOT_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class StateEngListResponse
        extends AbstractSelfWrappingResponseType<StateEngListResponse> {

    @Serial
    private static final long serialVersionUID = 4043100661470226062L;

    // -----------------------------------------------------------------------------------------------------------------
    static final String ROOT_NAME = "StateEngListResponse";

    private static final String NAME_STATE_ENG_LIST = "stateEngList";

    @Setter
    @Getter
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class StateEngList
            extends AbstractType {

        @Serial
        private static final long serialVersionUID = 1923480486008916847L;

        @NotBlank
        @JsonProperty
        @XmlElement
        private String stateEngName;
    }

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ---------------------------------------------------------------------------------------------------- cmmMsgHeader
    // just for the prefab values.
    StateEngListResponse cmmMsgHeader(final CmmMsgHeader cmmMsgHeader) {
        setCmmMsgHeader(cmmMsgHeader);
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @NotNull
    private CmmMsgHeader cmmMsgHeader;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty(NAME_STATE_ENG_LIST)
    @XmlElement(name = NAME_STATE_ENG_LIST)
    private List<@Valid @NotNull StateEngList> stateEngList;

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @JsonProperty(ROOT_NAME)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private StateEngListResponse wrapped;

    // -----------------------------------------------------------------------------------------------------------------
    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(StateEngListResponse.class);
        } catch (final JAXBException jaxbe) {
            throw new ExceptionInInitializerError(jaxbe);
        }
    }

    public static StateEngListResponse unmarshalInstance(final Object source) throws JAXBException {
        return AbstractTypeUtils.unmarshalNoNamespacedInstance(
                JAXB_CONTEXT,
                StateEngListResponse.class,
                source
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static StateEngListResponse deserializeInstance(final ObjectReader reader, final Object source) {
        return AbstractTypeUtils.deserializeInstance(reader, source);
    }

    public static StateEngListResponse deserializeInstance(final ObjectMapper mapper, final Object source) {
        return AbstractTypeUtils.deserializeInstance(
                mapper,
                StateEngListResponse.class,
                source
        );
    }

    public static StateEngListResponse deserializeInstance(final Object source) {
        return AbstractTypeUtils.deserializeInstance(
                StateEngListResponse.class,
                source
        );
    }
}
