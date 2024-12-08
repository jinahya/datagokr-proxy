package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import com.github.jinahya.epost.openapi.proxy._TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@Tag(_TestConstants.TAG_LONG_RUNNING)
@Disabled("takes too long")
@Slf4j
// https://poi.apache.org/components/spreadsheet/how-to.html#sxssf
class AreaCodeInfoExcelTest {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("java.awt.headless", "true");
    }

    @AfterAll
    static void afterAll() {
        try {
            final var desktop = Desktop.getDesktop();
            final var directory = new File(
                    AreaCodeInfoUtils_Test.class.getResource("__get.sh").toURI()).getParentFile();
            desktop.open(directory);
        } catch (final Exception e) {
            log.error("failed to open directory", e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private static Stream<Arguments> getResourceFileArgumentsStream() {
        return AreaCodeInfoUtils_Test.getResourceFileStream()
                .map(f -> Arguments.of(Named.of(f.getName(), f)));
    }

    private void entryToSheet(final InputStream source, final SXSSFSheet sheet, @Nullable final CellStyle cellStyle)
            throws IOException {
        final var colcnt = new AtomicInteger();
        final var rownum = new AtomicInteger();
        AreaCodeInfoUtils.extract(
                source,
                h -> {
                    colcnt.set(h.length);
                    final var row = sheet.createRow(rownum.getAndIncrement());
                    for (int i = 0; i < h.length; i++) {
                        final var cell = row.createCell(i);
                        if (cellStyle != null) {
                            sheet.setDefaultColumnStyle(i, cellStyle);
                        } else {
                            cell.setCellType(CellType.STRING);
                        }
                        cell.setCellValue(h[i]);
                    }
                    sheet.createFreezePane(0, 1);
                },
                r -> {
                    final var row = sheet.createRow(rownum.getAndIncrement());
                    for (int i = 0; i < r.length; i++) {
                        if (r[i] == null || r[i].isBlank()) {
                            continue;
                        }
                        final var cell = row.createCell(i);
                        if (cellStyle != null) {
                            sheet.setDefaultColumnStyle(i, cellStyle);
                        } else {
                            cell.setCellType(CellType.STRING);
                        }
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

    @MethodSource({"getResourceFileArgumentsStream"})
    @ParameterizedTest
    void __WorkbookPerZip(final File source) throws IOException, InterruptedException {
        try (var workbook = new SXSSFWorkbook(1);
             var zipFile = new ZipFile(source, AreaCodeInfoUtils.CHARSET)) {
//            workbook.setCompressTempFiles(true);
            final var cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
            // https://poi.apache.org/apidocs/dev/org/apache/poi/ss/usermodel/BuiltinFormats.html
            assert cellStyle.getDataFormat() == 0x31;
            try {
                for (var e = zipFile.entries(); e.hasMoreElements(); ) {
                    final var entry = e.nextElement();
                    if (entry.isDirectory() || !entry.getName().endsWith(".txt")) {
                        continue;
                    }
                    final String name;
                    {
                        var n = entry.getName();
                        n = n.substring(n.lastIndexOf('/') + 1, n.length() - 4);
                        name = n;
                    }
                    log.debug("name: {}", name);
                    final var sheet = workbook.createSheet(name);
                    try (var stream = zipFile.getInputStream(entry)) {
                        entryToSheet(stream, sheet, cellStyle);
                    }
                }
                final File target = new File(source.getParent(), source.getName() + ".xlsx");
                log.debug("target.path: {}", target.getAbsolutePath());
                try (var output = spy(new FileOutputStream(target))) {
                    workbook.write(output);
                    verify(output, atLeast(1)).flush();
                }
                log.debug("target.size: {}", target.length());
            } finally {
                final var result = workbook.dispose(); // deprecated; // TODO: remove this try-finally
                assert result;
            }
        }
    }

    @Disabled
    @MethodSource({"getResourceFileArgumentsStream"})
    @ParameterizedTest
    void __WorkbookPerEntry_Sequential(final File source) throws IOException {
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
                    workbook.setCompressTempFiles(true);
                    final var cellStyle = workbook.createCellStyle();
                    cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
                    try {
                        final var sheet = workbook.createSheet(name);
                        entryToSheet(zipFile.getInputStream(entry), sheet, cellStyle);
                        final File target = new File(source.getParent(), source.getName() + '_' + name + ".xlsx");
                        log.debug("target.path: {}", target.getAbsolutePath());
                        try (var output = spy(new FileOutputStream(target))) {
                            workbook.write(output);
                            verify(output, atLeast(1)).flush();
                        }
                        log.debug("target.size: {}", target.length());
                    } finally {
                        final var result = workbook.dispose(); // deprecated; // TODO: remove this try-finally
                        assert result;
                    }
                }
            }
        }
    }

    @MethodSource({"getResourceFileArgumentsStream"})
    @ParameterizedTest
    void __WorkbookPerEntry_Concurrent(final File source) throws IOException, InterruptedException {
        try (var zipFile = new ZipFile(source, AreaCodeInfoUtils.CHARSET);
             var executor = Executors.newCachedThreadPool()) {
            for (var e = zipFile.entries(); e.hasMoreElements(); ) {
                final var entry = e.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".txt")) {
                    continue;
                }
                final String name;
                {
                    var n = entry.getName();
                    n = n.substring(n.lastIndexOf('/') + 1, n.length() - 4);
                    name = n;
                }
                log.debug("name: {}", name);
                executor.execute(() -> {
                    try (var workbook = new SXSSFWorkbook(1)) {
                        workbook.setCompressTempFiles(true);
                        final var cellStyle = workbook.createCellStyle();
                        cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("@"));
                        try {
                            final var sheet = workbook.createSheet(name);
                            entryToSheet(zipFile.getInputStream(entry), sheet, cellStyle);
                            final var target = new File(source.getParent(), source.getName() + '_' + name + ".xlsx");
                            log.debug("target.path: {}", target.getAbsolutePath());
                            try (var output = spy(new FileOutputStream(target))) {
                                workbook.write(output);
                                verify(output, atLeast(1)).flush();
                            }
                            log.debug("target.size: {}", target.length());
                        } finally {
                            final var result = workbook.dispose(); // deprecated; // TODO: remove this try-finally
                            assert result;
                        }
                    } catch (final IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                });
            }
            executor.shutdown();
            if (!executor.awaitTermination(60L, TimeUnit.MINUTES)) {
                log.error("executor hasn't been terminated");
            }
        }
    }
}
