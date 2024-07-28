package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.__common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.jinahya.epost.openapi.proxy._misc.jackson.databind.ObjectReaderUtils;
import com.github.jinahya.epost.openapi.proxy._misc.xml.stream.XMLInputFactoryUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class AbstractTypeUtils {

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends AbstractType> T unmarshalNoNamespacedInstance(JAXBContext context,
                                                                           final Class<T> clazz,
                                                                           final Object source)
            throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(clazz);
        }
        return XMLInputFactoryUtils.unmarshalNoNamespacedInstance(
                context,
                clazz,
                source
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final Map<Class<?>, JAXBContext> CONTEXTS = new HashMap<>();

    private static JAXBContext context(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        return CONTEXTS.computeIfAbsent(clazz, k -> {
            try {
                return JAXBContext.newInstance(k);
            } catch (final JAXBException jaxbe) {
                throw new RuntimeException("failed to create context for " + k, jaxbe);
            }
        });
    }

    public static <T extends AbstractType> T unmarshalNoNamespacedInstance(final Class<T> clazz,
                                                                           final Object source)
            throws JAXBException {
        return unmarshalNoNamespacedInstance(context(clazz), clazz, source);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static <T extends AbstractType> T deserializeInstance(final ObjectReader reader, final Object source) {
        return ObjectReaderUtils.readValue(
                reader,
                source
        );
    }

    public static <T extends AbstractType> T deserializeInstance(final ObjectMapper mapper,
                                                                 final Class<T> clazz, final Object source) {
        Objects.requireNonNull(mapper, "mapper is null");
        return deserializeInstance(
                mapper.readerFor(clazz),
                source
        );
    }

    public static <T extends AbstractType> T deserializeInstance(final Jackson2ObjectMapperBuilder builder,
                                                                 final Class<T> clazz, final Object source) {
        Objects.requireNonNull(builder, "builder is null");
        return deserializeInstance(
                builder.build(),
                clazz,
                source
        );
    }

    public static <T extends AbstractType> T deserializeInstance(final Class<T> clazz, final Object source) {
        return deserializeInstance(
                new ObjectMapper(),
                clazz,
                source
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private AbstractTypeUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
