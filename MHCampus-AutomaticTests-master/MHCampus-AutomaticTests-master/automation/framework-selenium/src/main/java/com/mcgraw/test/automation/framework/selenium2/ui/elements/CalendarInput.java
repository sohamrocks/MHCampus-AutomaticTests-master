package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Base element for Date fields
 *
 * @author Andrei Varabyeu
 *
 */
public class CalendarInput extends Element {

	/* Date format on dataconsole pages */
	private DateFormat dataConsoleDataFormat;

	public CalendarInput(WebElement webElement) {
		super(webElement);
		dataConsoleDataFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	public void typeDate(Date date) {
		Logger.operation("Type "+dataConsoleDataFormat.format(date)+" to CalendarInput '"
				+ getIdentifyingText() + "'");
		webElement.sendKeys(dataConsoleDataFormat.format(date));
	}

	public void clearInput() {
		Logger.operation("Clear CalendarInput '" + getIdentifyingText()+ "'");
		webElement.clear();
	}

	public void getDate() {
		Logger.operation("Obtaining value from element with TagName '"
				+ getIdentifyingText() + "'");
		webElement.clear();
	}

}
