package com.github.jinahya.epost.openapi.proxy._javax.xml.stream.util;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

// https://stackoverflow.com/a/7206666/330457
public class NoNamespaceStreamReaderDelegate
        extends StreamReaderDelegate {

    private static final String ATTRIBUTE_LOCAL_NAME_TYPE = "type";

    public NoNamespaceStreamReaderDelegate(final XMLStreamReader reader) {
        super(reader);
    }

    @Override
    public String getAttributeNamespace(final int index) {
        if (ATTRIBUTE_LOCAL_NAME_TYPE.equals(getAttributeLocalName(index))) {
            return XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
        }
        return super.getAttributeNamespace(index);
    }
}
