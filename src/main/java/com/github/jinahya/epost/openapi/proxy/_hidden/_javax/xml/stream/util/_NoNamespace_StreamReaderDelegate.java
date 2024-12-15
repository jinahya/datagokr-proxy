package com.github.jinahya.epost.openapi.proxy._hidden._javax.xml.stream.util;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
// https://stackoverflow.com/a/7206666/330457
public class _NoNamespace_StreamReaderDelegate
        extends StreamReaderDelegate {

    private static final String ATTRIBUTE_LOCAL_NAME_TYPE = "type";

    public _NoNamespace_StreamReaderDelegate(final XMLStreamReader reader) {
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
