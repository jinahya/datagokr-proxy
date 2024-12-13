package com.github.jinahya.epost.openapi.proxy.web.bind;

import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public E convert(final String source) {
        return Optional.ofNullable(source)
                .map(converter)
                .orElse(null);
    }

    private final Function<? super String, E> converter;
}
