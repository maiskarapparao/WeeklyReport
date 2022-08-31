package com.hackathon.bank.WeeklyReport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankUserTxnWeeklyReportData {
    private String customerName;
    private String atmCardNumber;
    private String transaction;
    private int numOfOccurrencesInWeek1;
    private int numOfOccurrencesInWeek2;
    private int numOfOccurrencesInWeek3;
    private int numOfOccurrencesInWeek4;
    private int numOfOccurrencesInWeek5;

    public BankUserTxnWeeklyReportData(String customerName, String atmCardNumber, String transaction) {
        this.customerName = customerName;
        this.atmCardNumber = atmCardNumber;
        this.transaction = transaction;
        this.numOfOccurrencesInWeek1 = 0;
        this.numOfOccurrencesInWeek2 = 0;
        this.numOfOccurrencesInWeek3 = 0;
        this.numOfOccurrencesInWeek4 = 0;
        this.numOfOccurrencesInWeek5 = 0;
    }
}
