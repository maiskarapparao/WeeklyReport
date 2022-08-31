package com.hackathon.bank.WeeklyReport.excel.readers;

import com.hackathon.bank.WeeklyReport.configuration.AppConfig;
import com.hackathon.bank.WeeklyReport.model.ExcelRowData;
import com.hackathon.bank.WeeklyReport.utils.CommonUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExcelReader {
    private CommonUtils commonUtils;

    public AbstractExcelReader(CommonUtils commonUtils) {
        this.commonUtils = commonUtils;
    }

    public abstract void convertCellToRowData(Cell cell, int cellNum, ExcelRowData rowData);

    /**
     * get Excel Workbook object for given relative file path.
     * @param inputFileRelativePath
     * @return workbook object
     * @throws IOException
     */
    public Workbook getWorkBook(String inputFileRelativePath) throws IOException {
        File file = new File(commonUtils.getAbsolutePath(inputFileRelativePath));
        if (file == null || !file.isFile() || file.isDirectory())
            return null;
        return new XSSFWorkbook(inputFileRelativePath);
    }

    /**
     * loop through the rows of given excel file's sheet 0.
     * @param inputFileRelativePath input filePath
     * @return List<ExcelRowData> converted excel java object.
     * @throws IOException
     */
    public List<ExcelRowData> processFile(String inputFileRelativePath) throws IOException {
        return processFile(getWorkBook(inputFileRelativePath));
    }


    /**
     * loop through the rows of given excel file's sheet 0.
     * @param workbook input filePath's excel Workbook object.
     * @return List<ExcelRowData> converted excel java object.
     * @throws IOException
     */
    public List<ExcelRowData> processFile(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

        try {
            List<ExcelRowData> excelDataList = new ArrayList<>();
            for (Row row : sheet) {
                ExcelRowData rowData = new ExcelRowData();
                int cellNum = 0;
                for (Cell cell : row) {
                    if (row.getRowNum() > 0) {
                        convertCellToRowData(cell, cellNum, rowData);
                        cellNum++;
                    }
                }
                excelDataList.add(rowData);
            }
            return excelDataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get Integer value for the cell.
     * @param cell
     * @return
     */
    public Integer getInteger(Cell cell) {
        try {
            if (cell.getCellType() == CellType.STRING)
                return Integer.parseInt(cell.getStringCellValue());
        } catch (Exception ignored) {}

        try {
            if (cell.getCellType() == CellType.NUMERIC)
                return (int) cell.getNumericCellValue();
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * get String value for the cell.
     * @param cell
     * @return
     */
    public String getString(Cell cell) {
        try {
            if (cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
        } catch (Exception ignored) {}

        try {
            if (cell.getCellType() == CellType.NUMERIC)
                return String.valueOf(cell.getNumericCellValue());
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * get BigDecimal value for the cell.
     * @param cell
     * @return
     */
    public BigDecimal getBigDecimal(Cell cell) {
        try {
            if (cell.getCellType() == CellType.STRING)
                return new BigDecimal(cell.getStringCellValue());
        } catch (Exception ignored) {}

        try {
            if (cell.getCellType() == CellType.NUMERIC)
                return BigDecimal.valueOf(cell.getNumericCellValue());
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * get String value for the cell.
     * @param cell
     * @return
     */
    public Date getDate(Cell cell) {
        try {
            if (cell.getCellType() == CellType.STRING)
                return Date.valueOf(cell.getStringCellValue());
        } catch (Exception ignored) {}

        return null;
    }
}
