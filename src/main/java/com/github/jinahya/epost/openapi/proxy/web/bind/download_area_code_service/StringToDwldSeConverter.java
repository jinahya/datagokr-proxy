package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class StringToDwldSeConverter
        implements Converter<String, AreaCodeInfoRequest.DwldSe> {

    @Override
    public AreaCodeInfoRequest.DwldSe convert(final String source) {
        return Optional.ofNullable(source)
                .map(AreaCodeInfoRequest.DwldSe::valueOfValue)
                .orElse(null);
    }
}
