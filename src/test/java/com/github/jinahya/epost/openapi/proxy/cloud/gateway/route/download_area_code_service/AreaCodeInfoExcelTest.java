package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

@Disabled("takes too long")
@Slf4j
// https://poi.apache.org/components/spreadsheet/how-to.html#sxssf
class AreaCodeInfoExcelTest {

    private static Stream<File> getResourceFileStream() {
        return AreaCodeInfoUtilsTest.getResourceFileStream();
    }

    private void __(final InputStream source, final SXSSFSheet sheet) throws IOException {
        System.setProperty("java.awt.headless", "true");
        final var colcnt = new AtomicInteger();
        final var rownum = new AtomicInteger();
        AreaCodeInfoUtils.extract(
                source,
                h -> {
                    colcnt.set(h.length);
                    final var row = sheet.createRow(rownum.getAndIncrement());
                    for (int i = 0; i < h.length; i++) {
                        final var cell = row.createCell(i);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(h[i]);
                    }
                    sheet.createFreezePane(0, 1);
                },
                r -> {
                    final var row = sheet.createRow(rownum.getAndIncrement());
                    for (int i = 0; i < r.length; i++) {
                        if (r[i].isBlank()) {
                            continue;
                        }
                        final var cell = row.createCell(i);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(r[i]);
                    }
                }
        );
        // https://stackoverflow.com/a/47478314/330457
        try {
            final var _sh = SXSSFSheet.class.getDeclaredField("_sh");
            _sh.setAccessible(true);
            final var xssfsheet = (XSSFSheet) _sh.get(sheet);
            xssfsheet.addIgnoredErrors(new CellRangeAddress(1, rownum.get(), 0, colcnt.get()),
                                       IgnoredErrorType.NUMBER_STORED_AS_TEXT);
        } catch (final ReflectiveOperationException roe) {
            log.error("failed to set IgnoredErrorType.NUMBER_STORED_AS_TEXT", roe);
        }
    }

    @MethodSource({
            "getResourceFileStream"
    })
    @ParameterizedTest
    void __WorkbookPerZip(final File source) throws IOException {
        System.setProperty("java.awt.headless", "true");
        try (var workbook = new SXSSFWorkbook(1);
             var zipFile = new ZipFile(source, AreaCodeInfoUtils.CHARSET)) {
            for (var e = zipFile.entries(); e.hasMoreElements(); ) {
                final var entry = e.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".txt")) {
                    continue;
                }
                var name = entry.getName();
                name = name.substring(name.lastIndexOf('/') + 1, name.length() - 4);
                log.debug("name: {}", name);
                final var sheet = workbook.createSheet(name);
                __(zipFile.getInputStream(entry), sheet);
            }
            final File target = new File(source.getParent(), source.getName() + ".xlsx");
            log.debug("target.path: {}", target.getAbsolutePath());
            try (var output = new FileOutputStream(target)) {
                workbook.write(output);
            }
        }
    }

    @MethodSource({
            "getResourceFileStream"
    })
    @ParameterizedTest
    void __WorkbookPerEntry(final File source) throws IOException {
        System.setProperty("java.awt.headless", "true");
        try (var zipFile = new ZipFile(source, AreaCodeInfoUtils.CHARSET)) {
            for (var e = zipFile.entries(); e.hasMoreElements(); ) {
                final var entry = e.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".txt")) {
                    continue;
                }
                var name = entry.getName();
                name = name.substring(name.lastIndexOf('/') + 1, name.length() - 4);
                log.debug("name: {}", name);
                try (var workbook = new SXSSFWorkbook(1)) {
                    final var sheet = workbook.createSheet(name);
                    __(zipFile.getInputStream(entry), sheet);
                    final File target = new File(source.getParent(), source.getName() + '_' + name + ".xlsx");
                    log.debug("target.path: {}", target.getAbsolutePath());
                    try (var output = new FileOutputStream(target)) {
                        workbook.write(output);
                    }
                }
            }
        }
    }
}
