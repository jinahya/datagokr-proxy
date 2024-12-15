package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Objects;

/**
 * An abstract class for binding response messages.
 *
 * @param <SELF> self type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 * @see AbstractResponseType
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings({
        "java:S119" // <SELF ...>
})
public abstract class AbstractResponseType<SELF extends AbstractResponseType<SELF>>
        extends AbstractType<SELF> {

    @Serial
    private static final long serialVersionUID = 3542834861055866296L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractResponseType<?> that)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return Objects.equals(cmmMsgHeader, that.cmmMsgHeader)
                && Objects.equals(wrapped, that.wrapped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                cmmMsgHeader,
                wrapped
        );
    }

    // ---------------------------------------------------------------------------------------------------- cmmMsgHeader
    // just for the prefab values.
    @SuppressWarnings({"unchecked"})
    public SELF cmmMsgHeader(final CmmMsgHeader cmmMsgHeader) {
        setCmmMsgHeader(cmmMsgHeader);
        return (SELF) this;
    }

    // --------------------------------------------------------------------------------------------------------- wrapped
    @SuppressWarnings({
            "unchecked"
    })
    public final SELF wrapped(final SELF wrapped) {
        setWrapped(wrapped);
        return (SELF) this;
    }

    @SuppressWarnings({"unchecked"})
    public final SELF get() {
        if (wrapped != null) {
            return wrapped;
        }
        return (SELF) this;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Valid
    @JsonProperty
    @XmlElement
    private CmmMsgHeader cmmMsgHeader;

    // -----------------------------------------------------------------------------------------------------------------
//    @Schema(hidden = true)
    @Valid
    @XmlTransient
    private SELF wrapped;
}
