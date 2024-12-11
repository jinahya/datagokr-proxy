package com.github.jinahya.epost.openapi.proxy._java.lang.invoke;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class LookupUtils {

    // -----------------------------------------------------------------------------------------------------------------
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private static final Map<Class<?>, MethodHandles.Lookup> PRIVATE_LOOKUPS = new HashMap<>();

    public static MethodHandles.Lookup privateLookup(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        return PRIVATE_LOOKUPS.computeIfAbsent(clazz, k -> {
            try {
                return MethodHandles.privateLookupIn(k, LOOKUP);
            } catch (final IllegalAccessException iae) {
                throw new RuntimeException("failed to get private lookup for " + k, iae);
            }
        });
    }

    // -----------------------------------------------------------------------------------------------------------------
    private LookupUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
