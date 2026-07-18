package rahulshettyacademy.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Reads an .xlsx sheet into a List of HashMaps - the SAME shape your
 * existing JSON reader returns, so it drops straight into your
 * @DataProvider with no other changes.
 *
 * Row 1 of the sheet is treated as the header (the map keys).
 * Every following non-empty row becomes one HashMap:
 *   { "email" -> "...", "password" -> "...", "Productname" -> "..." }
 *
 * DataFormatter is used so numbers, dates, etc. all come back as the
 * text you see in the cell - no type surprises.
 */
public class ExcelReader {

    public List<HashMap<String, String>> getExcelData(String filePath, String sheetName)
            throws IOException {

        List<HashMap<String, String>> data = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = (sheetName != null && !sheetName.isEmpty())
                    ? workbook.getSheet(sheetName)
                    : workbook.getSheetAt(0);

            if (sheet == null) {
                throw new IOException("Sheet '" + sheetName + "' not found in " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            int totalCols = headerRow.getLastCellNum();

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;

                HashMap<String, String> map = new HashMap<>();
                boolean rowHasData = false;

                for (int c = 0; c < totalCols; c++) {
                    String key   = formatter.formatCellValue(headerRow.getCell(c)).trim();
                    String value = formatter.formatCellValue(row.getCell(c)).trim();
                    if (!value.isEmpty()) rowHasData = true;
                    map.put(key, value);
                }

                if (rowHasData) data.add(map);   // skip fully blank rows
            }
        }
        return data;
    }
}
