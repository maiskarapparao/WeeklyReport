package com.hackathon.bank.WeeklyReport;

import com.hackathon.bank.WeeklyReport.enums.TransactionStatus;
import com.hackathon.bank.WeeklyReport.excel.readers.BankRawDataExcelReader;
import com.hackathon.bank.WeeklyReport.excel.writers.BankReportExcelWriter;
import com.hackathon.bank.WeeklyReport.model.BankRawExcelRowData;
import com.hackathon.bank.WeeklyReport.model.BankUserTxnWeeklyReportData;
import com.hackathon.bank.WeeklyReport.model.ExcelRowData;
import com.hackathon.bank.WeeklyReport.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserWeeklyReportGenerator {

    private BankRawDataExcelReader bankRawDataExcelReader;
    private BankReportExcelWriter bankReportExcelWriter;
    private CommonUtils commonUtils;

    @Autowired
    public UserWeeklyReportGenerator(BankRawDataExcelReader bankRawDataExcelReader,
                                     BankReportExcelWriter bankReportExcelWriter,
                                     CommonUtils commonUtils) {
        this.bankRawDataExcelReader = bankRawDataExcelReader;
        this.bankReportExcelWriter = bankReportExcelWriter;
        this.commonUtils = commonUtils;
    }

    private Map<BankRawExcelRowData, BankUserTxnWeeklyReportData> convertToReportData(List<ExcelRowData> excelRowDataList, int month) {
        Map<BankRawExcelRowData, BankUserTxnWeeklyReportData> retMap = new ConcurrentHashMap<>();
        for (ExcelRowData data: excelRowDataList) {
            BankRawExcelRowData rawExcelRowData = (BankRawExcelRowData) data;
            if (rawExcelRowData == null || TransactionStatus.SUCCESS.name().equalsIgnoreCase(rawExcelRowData.getStatus()) ||
                    rawExcelRowData.getDate() == null)
                continue;
            LocalDate localDate = rawExcelRowData.getDate().toLocalDate();
            if ((localDate.getMonthValue() < (month -1)) || (localDate.getMonthValue() > (month + 1)))
                continue;
            if ((((localDate.getMonthValue() == (month - 1)) && localDate.getDayOfMonth() < 29)) ||
                    ((localDate.getMonthValue() == (month + 1) && localDate.getDayOfMonth() > 2)))
                continue;

            BankUserTxnWeeklyReportData reportData = new BankUserTxnWeeklyReportData(rawExcelRowData.getCustomerName(),
                    rawExcelRowData.getAtmCardNumber(), rawExcelRowData.getTransaction());
            if (retMap.containsKey(rawExcelRowData)) {
                reportData = retMap.get(rawExcelRowData);
            }

            if (localDate.getMonthValue() == month) {
                if (localDate.getDayOfMonth() <= 7)
                    reportData.setNumOfOccurrencesInWeek1(reportData.getNumOfOccurrencesInWeek1() + 1);
                else if (localDate.getDayOfMonth() <= 14)
                    reportData.setNumOfOccurrencesInWeek2(reportData.getNumOfOccurrencesInWeek2() + 1);
                else if (localDate.getDayOfMonth() <= 21)
                    reportData.setNumOfOccurrencesInWeek3(reportData.getNumOfOccurrencesInWeek3() + 1);
                else if (localDate.getDayOfMonth() <= 28)
                    reportData.setNumOfOccurrencesInWeek4(reportData.getNumOfOccurrencesInWeek4() + 1);
                else if (localDate.getDayOfMonth() <= 31)
                    reportData.setNumOfOccurrencesInWeek5(reportData.getNumOfOccurrencesInWeek5() + 1);
            }

            if (localDate.getMonthValue() == month -1)
                reportData.setNumOfOccurrencesInWeek1(reportData.getNumOfOccurrencesInWeek1() + 1);
            if (localDate.getMonthValue() == month + 1)
                reportData.setNumOfOccurrencesInWeek5(reportData.getNumOfOccurrencesInWeek5() + 1);

            retMap.put(rawExcelRowData, reportData);
        }
        return retMap;
    }

    /**
     * Generates the collective user transaction weekly report from the provided inputExcelRelativePath file
     * @param inputExcelRelativePath raw Excel file of users data.
     * @return outputExcelPath collective report file path.
     */
    public String createReport(String inputExcelRelativePath, int month) throws Exception {
        if (!StringUtils.hasLength(inputExcelRelativePath) || inputExcelRelativePath.length() <= ".xlsx".length() ||
                !inputExcelRelativePath.endsWith(".xlsx"))
            return null;
        List<ExcelRowData> bankRawExcelRowDataList = this.bankRawDataExcelReader.processFile(inputExcelRelativePath);
        Map<BankRawExcelRowData, BankUserTxnWeeklyReportData> aggregatedMap = convertToReportData(bankRawExcelRowDataList, month);
        String outPath = commonUtils.getAbsolutePath(inputExcelRelativePath.substring(0, inputExcelRelativePath.lastIndexOf(".xlsx")) +  "_rpt.xlsx");
        bankReportExcelWriter.writeToFile(outPath, aggregatedMap.values());
        return outPath;
    }
}
