package com.hackathon.bank.WeeklyReport.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties("bankreports")
public class AppConfig {
    @Value("${server.base-path}")
    private String serverBasePath;

    @Value("${bank-report.sheet-name}")
    private String bankReportSheetName;

    @Value("${bank-report.row-height}")
    private int bankReportRowHeight;
}
