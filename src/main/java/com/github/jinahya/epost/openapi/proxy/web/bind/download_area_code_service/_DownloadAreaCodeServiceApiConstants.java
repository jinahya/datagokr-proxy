package com.github.jinahya.epost.openapi.proxy.web.bind.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service._DownloadAreaCodeServiceConstants;
import com.github.jinahya.epost.openapi.proxy.web.bind.__WebBindConstants;

@SuppressWarnings({
        "java:S101", // Class names should comply with a naming convention
        "java:S125"  // Sections of code should not be commented out
})
final class _DownloadAreaCodeServiceApiConstants {

    static final String TAG = "DownloadAreaCodeService";

    // -----------------------------------------------------------------------------------------------------------------
    static final String REL_FILE_CONTENT = "fileContent";

    // -----------------------------------------------------------------------------------------------------------------
    static final String PARAM_ATTACH = "attach";

    static final String PARAM_FILENAME = "filename";

    // ------------------------------------------------------------------------------------ /api/downloadAreaCodeService
    static final String REQUEST_URI = __WebBindConstants.REQUEST_URI_API
            + '/' + _DownloadAreaCodeServiceConstants.SERVICE_NAME;

    // ----------------------------------------------------------------------- /api/downloadAreaCodeService/areaCodeInfo
    static final String PATH_AREA_CODE_INFO = "areaCodeInfo";

    static final String REQUEST_URI_AREA_CODE_INFO = REQUEST_URI + '/' + PATH_AREA_CODE_INFO;

    // ----------------------------------------------------------- /api/downloadAreaCodeService/areaCodeInfo/{dwldSe:.+}
    static final String PATH_NAME_DWLD_SE = "dwldSe";

    static final String PATH_VALUE_DWLD_SE = ".+";

    static final String PATH_TEMPLATE_DWLD_SE = '{' + PATH_NAME_DWLD_SE + ':' + PATH_VALUE_DWLD_SE + '}';

    static final String REQUEST_URI_DWLD_SE = REQUEST_URI_AREA_CODE_INFO + '/' + PATH_TEMPLATE_DWLD_SE;

    // ----------------------------------------------- /api/downloadAreaCodeService/areaCodeInfo/{dwldSe:.+}/fileContent
    static final String PATH_SEGMENT_FILE_CONTENT = REL_FILE_CONTENT;

    static final String REQUEST_URI_FILE_CONTENT = REQUEST_URI_DWLD_SE + '/' + PATH_SEGMENT_FILE_CONTENT;

    // -----------------------------------------------------------------------------------------------------------------
    private _DownloadAreaCodeServiceApiConstants() {
        throw new AssertionError("instantiation is not allowed");
    }
}
