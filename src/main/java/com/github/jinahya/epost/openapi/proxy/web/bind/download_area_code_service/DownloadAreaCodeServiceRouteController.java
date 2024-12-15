package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service.AreaCodeInfoResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service._DownloadAreaCodeServiceConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

// declared just for the Swagger UI
//@Tag(name = _DownloadAreaCodeServiceApiConstants.TAG)
//@org.springframework.web.bind.annotation.RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class DownloadAreaCodeServiceRouteController {

    @GetMapping(
            path = _DownloadAreaCodeServiceConstants.REQUEST_URI_GET_AREA_CODE_INFO,
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    Mono<AreaCodeInfoResponse> getAreaCodeInfo(
//            @ParameterObject
            final AreaCodeInfoRequest request) {
        throw new UnsupportedOperationException("");
    }
}
