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
    static final String PATH_SEGMENT_NAME_SEARCH_SE = "searchSe";

    //    static final String PATH_SEGMENT_VALUE_SEARCH_SE = "(?:dong|post|road)";
    static final String PATH_SEGMENT_VALUE_SEARCH_SE = ".+";

//    static {
//        assert PATH_SEGMENT_NAME_SEARCH_SE.equals(
//                Arrays.stream(NewAddressListAreaCdRequest.SearchSe.values())
//                        .map(NewAddressListAreaCdRequest.SearchSe::text)
//                        .collect(Collectors.joining("|", "(?:", ")"))
//        );
//    }

    static final String PATH_SEGMENT_TEMPLATE_SEARCH_SE =
            '{' + PATH_SEGMENT_NAME_SEARCH_SE + ':' + PATH_SEGMENT_VALUE_SEARCH_SE + '}';

    static final String PATH_SEGMENT_NAME_SRCHWRD = "srchwrd";

    static final String PATH_SEGMENT_VALUE_SRCHWRD = ".+";

    static final String PATH_SEGMENT_TEMPLATE_SRCHWRD =
            '{' + PATH_SEGMENT_NAME_SRCHWRD + ':' + PATH_SEGMENT_VALUE_SRCHWRD + '}';

    static final String REQUEST_URI__SEARCH_SE__SRCHWRD =
            REQUEST_URI + '/' + PATH_SEGMENT_TEMPLATE_SEARCH_SE + '/' + PATH_SEGMENT_TEMPLATE_SRCHWRD;

    static final String REQUEST_URI__SRCHWRD = REQUEST_URI + '/' + PATH_SEGMENT_TEMPLATE_SRCHWRD;

    // -----------------------------------------------------------------------------------------------------------------
    static final String REQUEST_PARAM_SEARCH_SE = _RetrieveNewAdressAreaCdServiceConstants.PARAM_SEARCH_SE;

    static final String REQUEST_PARAM_SRCHWRD = _RetrieveNewAdressAreaCdServiceConstants.PARAM_SRCHWRD;

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    private _RetrieveNewAdressAreaCdServiceApiConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
