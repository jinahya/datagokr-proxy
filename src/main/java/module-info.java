module com.github.jinahya.epost.openapi.proxy {

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;
    requires jakarta.xml.bind;
    requires java.xml;
    requires static lombok;
    requires reactor.core;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.cloud.gateway.server;
    requires spring.web;
    requires org.slf4j;

    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.__common;
    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._download_area_code_service;
    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._retrieve_eng_address_service;
    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._retrieve_lot_number_adress_area_cd_service;
    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._retrieve_new_adress_area_cd_search_all_service;
    exports com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._retrieve_new_adress_area_cd_service;
}
