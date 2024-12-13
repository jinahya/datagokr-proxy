package com.github.jinahya.epost.openapi.proxy.web.bind.retrieve_new_adress_area_cd_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.retrieve_new_adress_area_cd_service._RetrieveNewAdressAreaCdServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind.__WebBindConstants;

public final class _RetrieveNewAdressAreaCdServiceApiConstants {

    // -----------------------------------------------------------------------------------------------------------------
    static final String TAG = "RetrieveNewAdressAreaCdService";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String REQUEST_URI =
            __WebBindConstants.REQUEST_URI_API + '/' + _RetrieveNewAdressAreaCdServiceConstants.SERVICE_NAME;

    // -----------------------------------------------------------------------------------------------------------------
    static final String REQUEST_URI_SEARCH = REQUEST_URI + "/search";

    // -----------------------------------------------------------------------------------------------------------------
    static final String REQUEST_PARAM_SEARCH_SE = _RetrieveNewAdressAreaCdServiceConstants.PARAM_SEARCH_SE;

    static final String REQUEST_PARAM_SRCHWRD = _RetrieveNewAdressAreaCdServiceConstants.PARAM_SRCHWRD;

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    private _RetrieveNewAdressAreaCdServiceApiConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
