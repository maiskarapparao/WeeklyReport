package com.hackathon.bank.WeeklyReport.utils;

import com.hackathon.bank.WeeklyReport.configuration.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CommonUtils {
    private AppConfig appConfig;

    @Autowired
    public CommonUtils(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getAbsolutePath(String inputFileRelativePath) {
        if (!StringUtils.hasText(inputFileRelativePath) || appConfig == null || !StringUtils.hasText(appConfig.getServerBasePath()))
            return null;
        String basePath = appConfig.getServerBasePath();
        if (basePath.endsWith("/"))
            basePath = basePath.substring(0, basePath.length()-1);
        if (!inputFileRelativePath.startsWith("/"))
            inputFileRelativePath = "/" + inputFileRelativePath;

        return basePath + inputFileRelativePath;
    }
}
