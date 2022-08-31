package com.hackathon.bank.WeeklyReport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeeklyReportApplicationTests {

	private UserWeeklyReportGenerator userWeeklyReportGenerator;

	@Autowired
	public WeeklyReportApplicationTests(UserWeeklyReportGenerator userWeeklyReportGenerator) {
		this.userWeeklyReportGenerator = userWeeklyReportGenerator;
	}

	@Test
	void contextLoads() throws Exception {
		this.userWeeklyReportGenerator.createReport("report1/customer_transaction_list_June2022.xlsx", 6);
	}

}
