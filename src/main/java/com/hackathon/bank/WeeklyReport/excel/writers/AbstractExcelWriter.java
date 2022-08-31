package com.hackathon.bank.WeeklyReport.excel.writers;

import org.apache.commons.codec.DecoderException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractExcelWriter {
    protected int insertValueOrDefault(Row row, int cellNum, Integer val) {
        Cell cell = row.createCell(cellNum++);
        if (val == null)
            val = 0;
        cell.setCellValue(val);
        return cellNum;
    }

    protected int insertValueOrDefault(Row row, int cellNum, String val) {
        Cell cell = row.createCell(cellNum++);
        if (val == null)
            val = "";
        cell.setCellValue(val);
        return cellNum;
    }

    abstract List<String> getHeaders(String sheetName);
    abstract String getSheetName(int sheetIndex);
    abstract Short getDefaultHeaderRowHeight();
    abstract void insertValues(Sheet sh, Object data, int rowNum);

    public Integer insertHeader(SXSSFWorkbook wb, Sheet sh, Integer rowNum) throws DecoderException {
        Row row = sh.createRow(rowNum);
//        row.setHeight((short)-1);
        AtomicInteger cellNum = new AtomicInteger();
        List<String> headers = getHeaders(sh.getSheetName());

        if (headers != null && headers.size() > 0) {
            for(int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                Cell cell = row.createCell(cellNum.get());
                cell.setCellValue(header);
                CellStyle cs = wb.createCellStyle();
                cs.setWrapText(true);
                cell.setCellStyle(cs);
                cellNum.getAndIncrement();
            }
            rowNum += 1;
        }
        if (getDefaultHeaderRowHeight() != null)
            row.setHeight(getDefaultHeaderRowHeight());
        return rowNum;
    }

    public void insertData(Sheet sh, Object data, Integer rowNumInt) {
        List<Object> objectList = (List<Object>) data;
        AtomicInteger rowNum = new AtomicInteger(rowNumInt);
        objectList.forEach(objectData -> {
            insertValues(sh, objectData, rowNum.getAndIncrement());
        });
    }

    public void writeToFile(String fileName, Object ...dataVarList) throws Exception {
        if (!StringUtils.hasLength(fileName))
            return;
        if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))
            fileName += ".xlsx";
        SXSSFWorkbook wb = new SXSSFWorkbook(10);

        for (int i = 0; i < dataVarList.length; i++) {
            Object data = dataVarList[i];
            Sheet sh = wb.createSheet();

            wb.setSheetName(wb.getSheetIndex(sh), getSheetName(i));
            Integer rowNum = 0;
            rowNum = insertHeader(wb, sh, rowNum);
            insertData(sh, data, rowNum);
        }

        wb.setActiveSheet(0);
        wb.setSelectedTab(0);
        FileOutputStream fileOut = new FileOutputStream(fileName);
        wb.write(fileOut);
        fileOut.close();
    }
}
