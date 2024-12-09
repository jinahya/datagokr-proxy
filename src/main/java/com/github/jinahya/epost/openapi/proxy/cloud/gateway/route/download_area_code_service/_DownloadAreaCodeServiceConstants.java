package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route._RouteConstants;

@SuppressWarnings({
        "java:S101"
})
public final class _DownloadAreaCodeServiceConstants {

    public static final String ROUTE_ID = "download_area_code_service";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String SERVICE_NAME = "downloadAreaCodeService";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String REQUEST_URI =
            _RouteConstants.REQUEST_URI_POSTAL
                    + '/' + SERVICE_NAME
                    + '/' + SERVICE_NAME;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String PATH_GET_AREA_CODE_INFO = "getAreaCodeInfo";

    /**
     * The request uri for the {@value #PATH_GET_AREA_CODE_INFO}. The value is {@value}.
     */
    public static final String REQUEST_URI_GET_AREA_CODE_INFO = REQUEST_URI + '/' + PATH_GET_AREA_CODE_INFO;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The paameter name of {@value}.
     */
    public static final String PARAM_NAME_DWLD_SE = "dwldSe";

    // ---------------------------------------------------------------------------------------------------- CONSTRUCTORS
    private _DownloadAreaCodeServiceConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
