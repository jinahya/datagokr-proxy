package com.github.jinahya.epost.openapi.proxy.cloud.gateway.route.download_area_code_service;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.Styles;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

// https://svn.apache.org/repos/asf/poi/trunk/poi-examples/src/main/java/org/apache/poi/examples/xssf/eventusermodel/XLSX2CSV.java
@SuppressWarnings({"java:S106", "java:S4823", "java:S1192"})
public class XLSX2CSV {

    /**
     * Uses the XSSF Event SAX helpers to do most of the work of parsing the Sheet XML, and outputs the contents as a
     * (basic) CSV.
     */
    private class SheetToCSV
            implements SheetContentsHandler {

        private boolean firstCellOfRow;

        private int currentRow = -1;

        private int currentCol = -1;

        private void outputMissingRows(int number) {
            for (int i = 0; i < number; i++) {
                for (int j = 0; j < minColumns; j++) {
                    output.append(',');
                }
                output.append('\n');
            }
        }

        @Override
        public void startRow(int rowNum) {
            // If there were gaps, output the missing rows
            outputMissingRows(rowNum - currentRow - 1);
            // Prepare for this row
            firstCellOfRow = true;
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
            // Ensure the minimum number of columns
            for (int i = currentCol; i < minColumns; i++) {
                output.append(',');
            }
            output.append('\n');
        }

        @Override
        public void cell(String cellReference, String formattedValue,
                         XSSFComment comment) {
            if (firstCellOfRow) {
                firstCellOfRow = false;
            } else {
                output.append(',');
            }

            // gracefully handle missing CellRef here in a similar way as XSSFCell does
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            // Did we miss any cells?
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                output.append(',');
            }

            // no need to append anything if we do not have a value
            if (formattedValue == null) {
                return;
            }

            currentCol = thisCol;

            // Number or string?
            try {
                //noinspection ResultOfMethodCallIgnored
                Double.parseDouble(formattedValue);
                output.append(formattedValue);
            } catch (Exception e) {
                // let's remove quotes if they are already there
                if (formattedValue.startsWith("\"") && formattedValue.endsWith("\"")) {
                    formattedValue = formattedValue.substring(1, formattedValue.length() - 1);
                }

                output.append('"');
                // encode double-quote with two double-quotes to produce a valid CSV format
                output.append(formattedValue.replace("\"", "\"\""));
                output.append('"');
            }
        }
    }

    /// ////////////////////////////////////

    private final OPCPackage xlsxPackage;

    /**
     * Number of columns to read starting with leftmost
     */
    private final int minColumns;

    /**
     * Destination for data
     */
    private final PrintStream output;

    /**
     * Creates a new XLSX -&gt; CSV converter
     *
     * @param pkg        The XLSX package to process
     * @param output     The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no minimum
     */
    public XLSX2CSV(OPCPackage pkg, PrintStream output, int minColumns) {
        this.xlsxPackage = pkg;
        this.output = output;
        this.minColumns = minColumns;
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and shared-strings tables.
     *
     * @param styles           The table of styles that may be referenced by cells in the sheet
     * @param strings          The table of strings that may be referenced by cells in the sheet
     * @param sheetInputStream The stream to read the sheet-data from.
     * @throws java.io.IOException An IO exception from the parser, possibly from a byte stream or character stream
     *                             supplied by the application.
     * @throws SAXException        if parsing the XML data fails.
     */
    public void processSheet(
            Styles styles,
            SharedStrings strings,
            SheetContentsHandler sheetHandler,
            InputStream sheetInputStream) throws IOException, SAXException {
        // set emulateCSV=true on DataFormatter - it is also possible to provide a Locale
        // when POI 5.2.0 is released, you can call formatter.setUse4DigitYearsInAllDateFormats(true)
        // to ensure all dates are formatted with 4 digit years
        DataFormatter formatter = new DataFormatter(true);
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = XMLHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(
                    styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException  If reading the data from the package fails.
     * @throws SAXException if parsing the XML data fails.
     */
    public void process() throws IOException, OpenXML4JException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                String sheetName = iter.getSheetName();
                this.output.println();
                this.output.println(sheetName + " [index=" + index + "]:");

                try {
                    processSheet(styles, strings, new SheetToCSV(), stream);
                } catch (NumberFormatException e) {
                    throw new IOException("Failed to parse sheet " + sheetName, e);
                }
            }
            ++index;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Use:");
            System.err.println("  XLSX2CSV <xlsx file> [min columns]");
            return;
        }

        File xlsxFile = new File(args[0]);
        if (!xlsxFile.exists()) {
            System.err.println("Not found or not a file: " + xlsxFile.getPath());
            return;
        }

        int minColumns = -1;
        if (args.length >= 2)
            minColumns = Integer.parseInt(args[1]);

        // The package open is instantaneous, as it should be.
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        try {
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, System.out, minColumns);
            xlsx2csv.process();
        } finally {
            p.revert();
        }
    }
}