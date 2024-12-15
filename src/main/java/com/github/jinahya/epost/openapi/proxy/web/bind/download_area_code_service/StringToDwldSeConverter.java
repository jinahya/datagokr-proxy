package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.web.bind.StringToEnumConverter;
import org.springframework.stereotype.Component;

@Component
class StringToDwldSeConverter
        extends StringToEnumConverter<AreaCodeInfoRequest.DwldSe> {

    StringToDwldSeConverter() {
        super(AreaCodeInfoRequest.DwldSe::valueOfValue);
    }
}
