package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_search_all_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service._RetrieveNewAdressAreaCdSearchAllServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind._RouteController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = __RetrieveNewAdressAreCdSearchAllServiceApiConstants.TAG)
@RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class _RetrieveNewAdressAreaCdSearchAllServiceRouteController
        extends _RouteController {

    // -----------------------------------------------------------------------------------------------------------------
    @GetMapping(
            path =
                    _RetrieveNewAdressAreaCdSearchAllServiceConstants.REQUEST_URI_GET_NEW_ADDRESS_LIST_AREA_CD_SEARCH_ALL,
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    Mono<NewAddressListAreaCdSearchAllResponse> getNewAddressListAreaCdSearchAll(
            @ParameterObject final NewAddressListAreaCdSearchAllRequest request) {
        throw new UnsupportedOperationException("");
    }
}
