package com.hackathon.bank.WeeklyReport.excel.readers;

import com.hackathon.bank.WeeklyReport.configuration.AppConfig;
import com.hackathon.bank.WeeklyReport.model.BankRawExcelRowData;
import com.hackathon.bank.WeeklyReport.model.ExcelRowData;
import com.hackathon.bank.WeeklyReport.utils.CommonUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankRawDataExcelReader extends AbstractExcelReader {

    @Autowired
    public BankRawDataExcelReader(CommonUtils commonUtils) {
        super(commonUtils);
    }

    @Override
    public void convertCellToRowData(Cell cell, int cellNum, ExcelRowData rowData) {
        if (cell == null || cell.getRow() == null || cell.getRowIndex() <= 0 || rowData == null || !(rowData instanceof BankRawExcelRowData))
            return;

        BankRawExcelRowData bankRawExcelRowData = (BankRawExcelRowData) rowData;

        if (cellNum == 0)
            bankRawExcelRowData.setSlNo(getInteger(cell));

        if (cellNum == 1)
            bankRawExcelRowData.setDate(getDate(cell));

        if (cellNum == 2)
            bankRawExcelRowData.setAtmId(getString(cell));

        if (cellNum == 3)
            bankRawExcelRowData.setCustomerName(getString(cell));

        if (cellNum == 4)
            bankRawExcelRowData.setAtmCardNumber(getString(cell));

        if (cellNum == 5)
            bankRawExcelRowData.setTransaction(getString(cell));

        if (cellNum == 6)
            bankRawExcelRowData.setStatus(getString(cell));

        if (cellNum == 7)
            bankRawExcelRowData.setAmount(getBigDecimal(cell));

        if (cellNum == 8)
            bankRawExcelRowData.setBalance(getBigDecimal(cell));

    }

}
