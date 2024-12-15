package com.github.jinahya.epost.openapi.proxy.core.convert.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * An abstract class for converting {@code String} values into an {@code Enum} constants.
 *
 * @param <E> enum type parameter
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
public abstract class StringToEnumConverter<E extends Enum<E>>
        implements Converter<String, E> {

    /**
     * Creates a new instance with specified converter.
     *
     * @param converter the converter for converting from {@code String} to {@link E}.
     */
    protected StringToEnumConverter(final Function<? super String, E> converter) {
        super();
        this.converter = Objects.requireNonNull(converter, "converter is null");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Override
    public E convert(@NonNull final String source) {
//        return Optional.ofNullable(source)
//                .map(converter)
//                .orElse(null);
        return converter.apply(source);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private final Function<? super String, E> converter;
}
