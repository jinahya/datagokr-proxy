package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service.NewAddressListAreaCdRequest;
import com.github.jinahya.epost.openapi.proxy.web.bind.StringToEnumConverter;
import org.springframework.stereotype.Component;

@Component
class StringToSearchSeConverter
        extends StringToEnumConverter<NewAddressListAreaCdRequest.SearchSe> {

    StringToSearchSeConverter() {
        super(NewAddressListAreaCdRequest.SearchSe::valueOfText);
    }
}
