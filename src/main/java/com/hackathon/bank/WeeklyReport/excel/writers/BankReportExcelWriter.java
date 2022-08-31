package com.hackathon.bank.WeeklyReport.excel.writers;

import com.hackathon.bank.WeeklyReport.configuration.AppConfig;
import com.hackathon.bank.WeeklyReport.model.BankUserTxnWeeklyReportData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class BankReportExcelWriter extends AbstractExcelWriter {
    private AppConfig appConfig;

    @Autowired
    public BankReportExcelWriter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
    @Override
    List<String> getHeaders(String sheetName) {
        return Arrays.asList("Customer Name", "ATM Card Number", "Transaction", "No. of Occurances in Week 1",
                "No. of Occurances in Week 2", "No. of Occurances in Week 3", "No. of Occurances in Week 4",
                "No. of Occurances in Week 5");
    }

    @Override
    String getSheetName(int sheetIndex) {
        return appConfig.getBankReportSheetName();
    }

    @Override
    Short getDefaultHeaderRowHeight() {
        return (short) appConfig.getBankReportRowHeight();
    }

    @Override
    void insertValues(Sheet sh, Object data, int rowNum) {
        if (data == null)
            return;

        BankUserTxnWeeklyReportData reportsData = (BankUserTxnWeeklyReportData) data;

        Row row = sh.createRow(rowNum);

        int cellNum = 0;

        // "Customer Name",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getCustomerName());

        // "ATM Card Number",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getAtmCardNumber());

        // "Transaction",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getTransaction());

        // "No. of Occurances in Week 1",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getNumOfOccurrencesInWeek1());

        // "No. of Occurances in Week 2",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getNumOfOccurrencesInWeek2());

        // "No. of Occurances in Week 3",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getNumOfOccurrencesInWeek3());

        // "No. of Occurances in Week 4",
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getNumOfOccurrencesInWeek4());

        // "No. of Occurances in Week 5"
        cellNum = insertValueOrDefault(row, cellNum, reportsData.getNumOfOccurrencesInWeek5());
    }
}
