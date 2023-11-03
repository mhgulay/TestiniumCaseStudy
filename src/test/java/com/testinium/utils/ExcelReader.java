package com.testinium.utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {
    private static FileInputStream file;
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;

    static {
        try
        {
            file = new FileInputStream("beymen.xlsx");

            workbook = new XSSFWorkbook(file);

            sheet = workbook.getSheet("beymen");

            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String readCell(int rowNum, int columnNum) {
        return sheet.getRow(rowNum).getCell(columnNum).toString();
    }
}
