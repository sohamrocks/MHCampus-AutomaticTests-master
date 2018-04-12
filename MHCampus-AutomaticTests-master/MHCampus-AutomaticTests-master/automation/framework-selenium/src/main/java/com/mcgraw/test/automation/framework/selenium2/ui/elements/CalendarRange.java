package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarRange {

	private String startDay;

	private String endDay;

	private String startMonth;

	private String endMonth;

	private int startYear;

	private int endYear;

	public CalendarRange(String startDay, String endDay, String startMonth,
			String endMonth, int startYear, int endYear) {
		this.startDay = startDay;
		this.endDay = endDay;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startYear = startYear;
		this.endYear = endYear;
	}

	public CalendarRange(Date startDate, Date endDate) {
		SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("d");
		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		this.startDay = dayOfMonthFormat.format(startDate);
		this.endDay = dayOfMonthFormat.format(endDate);
		this.startMonth = monthFormat.format(startDate);
		this.endMonth = monthFormat.format(endDate);
		this.startYear = Integer.valueOf(yearFormat.format(startDate));
		this.endYear = Integer.valueOf(yearFormat.format(endDate));
	}

	public String getStartDay() {
		return startDay;

	}

	public String getEndDay() {
		return endDay;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public int getStartYear() {
		return startYear;
	}

	public int getEndYear() {
		return endYear;
	}

}
