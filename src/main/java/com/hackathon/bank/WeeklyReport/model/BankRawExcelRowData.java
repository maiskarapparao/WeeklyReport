package com.hackathon.bank.WeeklyReport.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankRawExcelRowData extends ExcelRowData {
    private Integer slNo;
    private Date date;
    private String atmId;
    private String customerName;
    private String atmCardNumber;
    private String transaction;
    private String status;
    private BigDecimal amount;
    private BigDecimal balance;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BankRawExcelRowData that = (BankRawExcelRowData) o;
        return Objects.equals(getCustomerName(), that.getCustomerName()) && Objects.equals(getAtmCardNumber(), that.getAtmCardNumber()) && Objects.equals(getTransaction(), that.getTransaction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerName(), getAtmCardNumber(), getTransaction());
    }
}
