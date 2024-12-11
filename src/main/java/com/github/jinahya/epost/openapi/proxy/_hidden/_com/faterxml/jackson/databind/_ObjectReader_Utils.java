package com.github.jinahya.epost.openapi.proxy._hidden._com.faterxml.jackson.databind;

import com.fasterxml.jackson.databind.ObjectReader;

import java.util.Objects;

@SuppressWarnings({
        "java:S101", // Class names should comply with a naming convention
        "java:S119"  // Type parameter names should comply with a naming convention
})
public final class _ObjectReader_Utils {

    public static <VALUE, SOURCE> VALUE readValue(final ObjectReader reader, final Class<SOURCE> type,
                                                  final SOURCE source) {
        for (var method : ObjectReader.class.getMethods()) {
            if (!"readValue".equals(method.getName())) {
                continue;
            }
            final var parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                continue;
            }
            if (!parameterTypes[0].isAssignableFrom(type)) {
                continue;
            }
            try {
                @SuppressWarnings({"unchecked"})
                final var result = (VALUE) method.invoke(reader, source);
                return result;
            } catch (ReflectiveOperationException roe) {
                throw new RuntimeException("unable to read value from " + source + " of " + type, roe);
            }
        }
        throw new IllegalArgumentException("unable to read value from " + source + " of " + type);
    }

    public static <VALUE, SOURCE> VALUE readValueHelper(final ObjectReader reader, final Class<SOURCE> type,
                                                        final Object source) {
        Objects.requireNonNull(type, "type is null");
        return readValue(reader, type, type.cast(source));
    }

    public static <VALUE> VALUE readValue(final ObjectReader reader, final Object source) {
        Objects.requireNonNull(source, "source is null");
        return readValueHelper(reader, source.getClass(), source);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _ObjectReader_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
