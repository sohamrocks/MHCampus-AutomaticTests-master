package com.mcgraw.test.automation.framework.selenium2.ui.elements;

public enum CalendarMenuItemName {
	//@formatter:off
	YESTERDAY("yesterday"),
	DAYS7("last 7 days"),
	DAYS14("last 14 days"),
	DAYS30("last 30 days"),
	DAYS90("last 90 days"),
	MONTHTODATE("month to date"),
	LASTMONTH("last month"),
	YEARTODATE("year to date"),
	SPECIFICDATE("specific date"),
	ALLDATESBEFORE("all dates before"),
	ALLDATESAFTER("all dates after"),
	DATARANGE("date range");
	//@formatter:on
	String name;

	private CalendarMenuItemName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.name;
	}

}
