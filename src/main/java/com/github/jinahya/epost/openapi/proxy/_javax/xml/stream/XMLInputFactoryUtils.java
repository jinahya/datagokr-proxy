package com.github.jinahya.epost.openapi.proxy._javax.xml.stream;

import com.github.jinahya.epost.openapi.proxy._javax.xml.stream.util.NoNamespaceStreamReaderDelegate;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.util.Objects;

// https://stackoverflow.com/a/7206666/330457
public final class XMLInputFactoryUtils {

    // -----------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({
            "java:S119"
    })
    public static <SOURCE> XMLStreamReader createXMLStreamReader(final XMLInputFactory factory,
                                                                 final Class<SOURCE> type, final SOURCE source) {
        for (var method : XMLInputFactory.class.getMethods()) {
            if (!method.getName().equals("createXMLStreamReader")) {
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
                return (XMLStreamReader) method.invoke(factory, source);
            } catch (ReflectiveOperationException roe) {
                throw new RuntimeException("unable to create xml stream reader for " + source, roe);
            }
        }
        throw new IllegalArgumentException("unable to create xml stream reader for " + source);
    }

    @SuppressWarnings({
            "java:S119"
    })
    private static <SOURCE> XMLStreamReader createXMLStreamReaderHelper(final XMLInputFactory factory,
                                                                        final Class<SOURCE> type, final Object source) {
        Objects.requireNonNull(type, "type is null");
        return createXMLStreamReader(
                factory,
                type,
                type.cast(source)
        );
    }

    public static XMLStreamReader createXMLStreamReader(final XMLInputFactory factory, final Object source) {
        Objects.requireNonNull(source, "source is null");
        return createXMLStreamReaderHelper(
                factory,
                source.getClass(),
                source
        );
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newFactory();

    public static <T> T unmarshalNoNamespacedInstance(final JAXBContext context, final Class<T> type,
                                                      final Object source)
            throws JAXBException {
        Objects.requireNonNull(context, "context is null");
        Objects.requireNonNull(source, "source is null");
        synchronized (XML_INPUT_FACTORY) {
            return context.createUnmarshaller()
                    .unmarshal(new NoNamespaceStreamReaderDelegate(createXMLStreamReader(XML_INPUT_FACTORY, source)),
                               type)
                    .getValue();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private XMLInputFactoryUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
