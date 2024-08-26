package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service._DownloadAreaCodeServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind.__WebBindConstants;

@SuppressWarnings({
        "java:S101" // class __Download...
})
final class __DownloadAreaCodeServiceApiConstants {

    static final String TAG = "DownloadAreaCodeService";

    // -----------------------------------------------------------------------------------------------------------------
    static final String REL_FILE = "file";

    // -----------------------------------------------------------------------------------------------------------------
    static final String REQUEST_URI = __WebBindConstants.REQUEST_URI_API
            + '/' + _DownloadAreaCodeServiceConstants.SERVICE_NAME;

    // -----------------------------------------------------------------------------------------------------------------
    static final String PATH_AREA_CODE_INFO = "areaCodeInfo";

    static final String REQUEST_URI_AREA_CODE_INFO = REQUEST_URI + '/' + PATH_AREA_CODE_INFO;

    // -----------------------------------------------------------------------------------------------------------------
    static final String PATH_NAME_DWLD_SE = "dwldSe";

    static final String PATH_VALUE_DWLD_SE = ".+";

    static final String PATH_TEMPLATE_DWLD_SE = '{' + PATH_NAME_DWLD_SE + ':' + PATH_VALUE_DWLD_SE + '}';

    static final String REQUEST_URI_DWLD_SE = REQUEST_URI_AREA_CODE_INFO + '/' + PATH_TEMPLATE_DWLD_SE;

    // -----------------------------------------------------------------------------------------------------------------
    static final String PATH_SEGMENT_FILE = REL_FILE;

    static final String REQUEST_URI_FILE = REQUEST_URI_DWLD_SE + '/' + PATH_SEGMENT_FILE;

    // -----------------------------------------------------------------------------------------------------------------
    private __DownloadAreaCodeServiceApiConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
