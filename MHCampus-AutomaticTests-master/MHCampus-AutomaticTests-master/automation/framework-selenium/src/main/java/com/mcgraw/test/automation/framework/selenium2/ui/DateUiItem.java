package com.mcgraw.test.automation.framework.selenium2.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

public class DateUiItem implements UiItem {

	private Date date;

	private DateFormat format;

	public DateUiItem(Date date, DateFormat format) {
		this.date = date;
		this.format = format;
	}

	@Override
	public String getLabel() {
		return format.format(date);
	}

	@Override
	public UiItem asUiItem(String label) {
		try {
			return new DateUiItem(format.parse(label), format);
		} catch (ParseException e) {
			throw new CommonTestRuntimeException("Unable to parse date", e);
		}
	}

}
