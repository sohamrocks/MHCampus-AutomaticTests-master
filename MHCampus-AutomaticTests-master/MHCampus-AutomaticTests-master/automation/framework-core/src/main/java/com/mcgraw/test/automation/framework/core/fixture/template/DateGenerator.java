package com.mcgraw.test.automation.framework.core.fixture.template;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.time.DateUtils;

public class DateGenerator {
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public String generateShiftedByDaysDate(int daysToShift) {
		DateFormat requiredDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return requiredDateFormat.format(DateUtils.addDays(Calendar
				.getInstance().getTime(), daysToShift));
	}

}
