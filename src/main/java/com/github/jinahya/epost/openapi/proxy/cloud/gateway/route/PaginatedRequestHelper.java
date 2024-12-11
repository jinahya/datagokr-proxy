package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route;

import com.github.jinahya.epost.openapi.proxy._java.lang.invoke.LookupUtils;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class PaginatedRequestHelper {

    // -----------------------------------------------------------------------------------------------------------------
    private static final String NAME_COUNT_PER_PAGE = "countPerPage";

    private static final Map<Class<?>, Field> COUNT_PER_PAGE_FIELDS = new HashMap<>();

    private static Field countPerPageField(final Class<?> clazz, final Object instance) {
        Objects.requireNonNull(clazz, "clazz is null");
        return COUNT_PER_PAGE_FIELDS.computeIfAbsent(clazz, k -> {
            try {
                final var field = k.getDeclaredField(NAME_COUNT_PER_PAGE);
                assert Objects.equals(field.getType(), Integer.class);
                if (field.canAccess(instance)) {
                    field.setAccessible(true);
                }
                return field;
            } catch (final ReflectiveOperationException roe) {
                throw new RuntimeException("failed to get field for '" + NAME_COUNT_PER_PAGE + "' from " + k, roe);
            }
        });
    }

    private static final Map<Class<?>, VarHandle> COUNT_PER_PAGE_HANDLES = new HashMap<>();

    private static VarHandle countPerPageHandle(final Class<?> clazz, final Object instance) {
        Objects.requireNonNull(clazz, "clazz is null");
        return COUNT_PER_PAGE_HANDLES.computeIfAbsent(clazz, k -> {
            final var field = countPerPageField(k, instance);
            try {
                return LookupUtils.privateLookup(k).unreflectVarHandle(field);
            } catch (final IllegalAccessException iae) {
                throw new RuntimeException("failed to unreflect" + field, iae);
            }
        });
    }

    static Integer getCountPerPage(final Object instance) {
        Objects.requireNonNull(instance, "instance is null");
        return (Integer) countPerPageHandle(instance.getClass(), instance).get(instance);
    }

    static void setCountPerPage(final Object instance, Integer countPerPage) {
        Objects.requireNonNull(instance, "instance is null");
        countPerPageHandle(instance.getClass(), instance).set(instance, countPerPage);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final String NAME_CURRENT_PAGE = "currentPage";

    private static final Map<Class<?>, Field> CURRENT_PAGE_FIELDS = new HashMap<>();

    private static Field currentPageField(final Class<?> clazz, final Object instance) {
        Objects.requireNonNull(clazz, "clazz is null");
        return CURRENT_PAGE_FIELDS.computeIfAbsent(clazz, k -> {
            try {
                final var field = k.getDeclaredField(NAME_CURRENT_PAGE);
                assert Objects.equals(field.getType(), Integer.class);
                if (field.canAccess(instance)) {
                    field.setAccessible(true);
                }
                return field;
            } catch (final ReflectiveOperationException roe) {
                throw new RuntimeException("failed to get field for '" + NAME_CURRENT_PAGE + "' from " + k, roe);
            }
        });
    }

    private static final Map<Class<?>, VarHandle> CURRENT_PAGE_HANDLES = new HashMap<>();

    private static VarHandle currentPageHandle(final Class<?> clazz, final Object instance) {
        Objects.requireNonNull(clazz, "clazz is null");
        return CURRENT_PAGE_HANDLES.computeIfAbsent(clazz, k -> {
            final var field = currentPageField(k, instance);
            try {
                return LookupUtils.privateLookup(k).unreflectVarHandle(field);
            } catch (final IllegalAccessException iae) {
                throw new RuntimeException("failed to unreflect" + field, iae);
            }
        });
    }

    static Integer getCurrentPage(final Object instance) {
        Objects.requireNonNull(instance, "instance is null");
        return (Integer) currentPageHandle(instance.getClass(), instance).get(instance);
    }

    static void setCurrentPage(final Object instance, Integer currentPage) {
        Objects.requireNonNull(instance, "instance is null");
        currentPageHandle(instance.getClass(), instance).set(instance, currentPage);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private PaginatedRequestHelper() {
        throw new AssertionError("instantiation is not allowed");
    }
}
