package com.github.jinahya.epost.openapi.proxy._hidden._javax.xml.stream;

import com.github.jinahya.epost.openapi.proxy._hidden._javax.xml.stream.util._NoNamespace_StreamReaderDelegate;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.util.Objects;

/**
 * Utilities for {@link XMLInputFactory}.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
// https://stackoverflow.com/a/7206666/330457
public final class _XMLInputFactory_Utils {

    // -----------------------------------------------------------------------------------------------------------------
    public static <T> XMLStreamReader createXMLStreamReader(final XMLInputFactory factory, final Class<T> type,
                                                            final T source) {
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

    private static <T> XMLStreamReader createXMLStreamReaderHelper(final XMLInputFactory factory, final Class<T> type,
                                                                   final Object source) {
        assert factory != null;
        assert type != null;
        assert source != null;
        return createXMLStreamReader(
                factory,
                type,
                type.cast(source)
        );
    }

    /**
     * Creates an XML stream reader from specified arguments.
     *
     * @param factory an XML input factory.
     * @param source  a source to read.
     * @return an XML stream reader.
     */
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

    /**
     * Unmarshals an instance of specified type from specified source with no namespaces.
     *
     * @param context a JAXB context.
     * @param type    the type of instance.
     * @param source  the source to unmarshal.
     * @param <T>     object type parameter
     * @return an object unmarshaled from {@code source}.
     * @throws JAXBException if failed to unmarshal.
     */
    public static <T> T unmarshalNoNamespacedInstance(final JAXBContext context, final Class<T> type,
                                                      final Object source)
            throws JAXBException {
        Objects.requireNonNull(context, "context is null");
        Objects.requireNonNull(source, "source is null");
        synchronized (XML_INPUT_FACTORY) {
            final var reader = new _NoNamespace_StreamReaderDelegate(createXMLStreamReader(XML_INPUT_FACTORY, source));
            return context.createUnmarshaller()
                    .unmarshal(reader, type)
                    .getValue();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private _XMLInputFactory_Utils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
