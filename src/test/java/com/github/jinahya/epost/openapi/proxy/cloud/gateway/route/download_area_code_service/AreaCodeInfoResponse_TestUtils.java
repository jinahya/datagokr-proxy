package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import java.util.stream.Stream;

final class AreaCodeInfoResponse_TestUtils {

    static Stream<String> getFileStream() {
        return Stream.of(
                "http://www.epost.go.kr/search/areacd/zipcode_DB.zip",
                "http://www.epost.go.kr/search/areacd/areacd_chgaddr_DB.zip",
                "http://www.epost.go.kr/search/areacd/areacd_rangeaddr_DB.zip",
                "http://www.epost.go.kr/search/areacd/areacd_pobox_DB.zip"
        );
    }

    private AreaCodeInfoResponse_TestUtils() {
        throw new AssertionError("instantiation is not allowed");
    }
}
