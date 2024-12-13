package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service.NewAddressListAreaCdRequest;
import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service.NewAddressListAreaCdResponse;
import com.github.jinahya.epost.openapi.proxy.web.bind._ApiService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Validated
@Service
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class RetrieveNewAdressAreaCdServiceApiService
        extends _ApiService {

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS

    // -----------------------------------------------------------------------------------------------------------------
    Mono<NewAddressListAreaCdResponse> exchange(@Valid final NewAddressListAreaCdRequest request) {
        return super.exchangeResponse(request);
    }
}
