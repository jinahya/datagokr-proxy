package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_search_all_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service.NewAddressListAreaCdSearchAllResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_search_all_service._RetrieveNewAdressAreaCdSearchAllServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind._RouteController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

//@Tag(name = _RetrieveNewAdressAreCdSearchAllServiceApiConstants.TAG)
//@org.springframework.web.bind.annotation.RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class RetrieveNewAdressAreaCdSearchAllServiceRouteController
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
//            @ParameterObject
            final NewAddressListAreaCdSearchAllRequest request) {
        throw new UnsupportedOperationException("");
    }
}
