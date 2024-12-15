package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_eng_address_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.CityEngListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.CityEngListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.DistrictEngFirstNameListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.DistrictEngFirstNameListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.LandAddressEngSearchListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.LandAddressEngSearchListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.RoadAddressEngSearchListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.RoadAddressEngSearchListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.RoadEngFirstNameListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.RoadEngFirstNameListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.StateEngListRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service.StateEngListResponse;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_eng_address_service._RetrieveEngAddressServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind._RouteController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

//@Tag(name = _RetrieveEngAddressServiceApiConstants.TAG)
//@org.springframework.web.bind.annotation.RestController
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class RetrieveEngAddressServiceRouteController
        extends _RouteController {

    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_STATE_LIST,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<StateEngListResponse> getStateList(
//                                            @ParameterObject
            final StateEngListRequest request) {
        throw new UnsupportedOperationException("");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_CITY_LIST,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<CityEngListResponse> getCityList(
//            @ParameterObject
            final CityEngListRequest request) {
        throw new UnsupportedOperationException("");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_ROAD_FIRST_NAME_LIST,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<RoadEngFirstNameListResponse> getRoadFirstNameList(
//            @ParameterObject
            final RoadEngFirstNameListRequest request) {
        throw new UnsupportedOperationException("");
    }

    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_ROAD_ADDRESS_SEARCH,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<RoadAddressEngSearchListResponse> getRoadAddressSearch(
//            ParameterObject
            final RoadAddressEngSearchListRequest request) {
        throw new UnsupportedOperationException("");
    }

    // -----------------------------------------------------------------------------------------------------------------
    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_DISTRICT_FIRST_NAME_LIST,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<DistrictEngFirstNameListResponse> getDistrictFirstNameList(
//             @ParameterObject
            final DistrictEngFirstNameListRequest request) {
        throw new UnsupportedOperationException("");
    }

    @GetMapping(path = _RetrieveEngAddressServiceConstants.REQUEST_URI_GET_LAND_ADDRESS_SEARCH,
                produces = {
                        MediaType.APPLICATION_XML_VALUE,
                        MediaType.APPLICATION_JSON_VALUE
                })
    Mono<LandAddressEngSearchListResponse> getLandAddressSearch(
//            @ParameterObject
            final LandAddressEngSearchListRequest request) {
        throw new UnsupportedOperationException("");
    }
}
