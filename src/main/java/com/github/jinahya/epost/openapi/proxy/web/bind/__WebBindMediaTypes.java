package com.github.jinahya.epost.openapi.proxy.web.bind;

@SuppressWarnings({
        "java:S101" // Class names should comply with a naming convention
})
public final class __WebBindMediaTypes {

    // -----------------------------------------------------------------------------------------------------------------
    public static final String FILE_TYPE_ODS = "ods";

    public static final String APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET_VALUE
            = "application/vnd.oasis.opendocument.spreadsheet";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String FILE_TYPE_XLSX = "xlsx";

    public static final String APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEET_SHEET_VALUE
            = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // -----------------------------------------------------------------------------------------------------------------
    private __WebBindMediaTypes() {
        throw new AssertionError("instantiation is not allowed");
    }
}
