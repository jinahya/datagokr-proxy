package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.Element;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyAttribute;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.namespace.QName;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * An abstract type class.
 *
 * @param <SELF> self type parameter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractType<SELF extends AbstractType<SELF>>
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1503731084647974030L;

    // ------------------------------------------------------------------------------------------ STATIC_FACTORY_METHODS
    protected static <T extends AbstractType<?>> T of(final Supplier<? extends T> initializer) {
        final var instance = Objects.requireNonNull(initializer.get(), "null initialized from " + initializer);
        return instance;
    }

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // ------------------------------------------------------------------------------------------------ java.lang.Object
    @Override
    public String toString() {
        return super.toString() + '{' +
                "unknownAttributes=" + unknownAttributes +
                ",unknownElements=" + unknownElements +
                ",unknownProperties=" + unknownProperties +
                '}';
    }

    // ----------------------------------------------------------------------------------------- Jakarta Bean Validation
    //@AssertTrue(message = "unknown attributes should be empty")
    private boolean isUnknownAttributesEmpty() {
        return unknownAttributes == null || unknownAttributes.isEmpty();
    }

    //@AssertTrue(message = "unknown elements should be empty")
    private boolean isUnknownElementsEmpty() {
        return unknownElements == null || unknownElements.isEmpty();
    }

    // ----------------------------------------------------------------------------------------------- unknownAttributes
    public Map<QName, Object> getUnknownAttributes() {
        if (unknownAttributes == null) {
            unknownAttributes = new HashMap<>();
        }
        return unknownAttributes;
    }

    // ------------------------------------------------------------------------------------------------- unknownElements
    public List<Element> getUnknownElements() {
        if (unknownElements == null) {
            unknownElements = new ArrayList<>();
        }
        return unknownElements;
    }

    // ----------------------------------------------------------------------------------------------- unknownProperties
    @JsonIgnore
    public Map<String, Object> getUnknownProperties() {
        if (unknownProperties == null) {
            unknownProperties = new HashMap<>();
        }
        return unknownProperties;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Size(max = 0, message = "no unknown attributes are expected")
    @JsonIgnore
    @XmlAnyAttribute
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Map<QName, Object> unknownAttributes;

    @Size(max = 0, message = "no unknown elements are expected")
    @JsonIgnore
    @XmlAnyElement
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private List<Element> unknownElements;

    // -----------------------------------------------------------------------------------------------------------------
//    @Size(max = 0, message = "no unknown properties are expected")
    @JsonAnySetter
    @XmlTransient
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Map<String, Object> unknownProperties;
}
