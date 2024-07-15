module com.github.jinahya.epost.openapi.proxy {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;
    requires jakarta.xml.bind;
    requires java.xml;
    requires static lombok;

    exports com.github.jinahya.epost.openapi.proxy.bind;
    exports com.github.jinahya.epost.openapi.proxy.retrievenewadressareacdsearchallservice;
    exports com.github.jinahya.epost.openapi.proxy.retrievenewadressareacdservice;
//    exports com.github.jinahya.epost.openapi.proxy.xml.stream.util;
//    exports com.github.jinahya.epost.openapi.proxy.jackson.databind;
}
