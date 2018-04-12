package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * @author Andrei Varabyeu
 */
public class Input extends Element {
	
	
	
	public Input(WebElement webElement) {
		super(webElement);
	}

	// Straight type to input (keys sent one by one)
	public void typeValue(String value) {
		Logger.info("Type value '" + value + "' to Input '"
				+ getIdentifyingText() + "'");
		webElement.sendKeys(value);
	}

	public void clearAndTypeValue(String value) {
		clearInput();
		typeValue(value);
	}
	
	public void typeCommaSeparatedValue(List<String> values) {
		String value = StringUtils.join(values, ",");
		Logger.info("Type value '" + value + "' to Input '"
				+ getIdentifyingText() + "'");
		webElement.sendKeys(value);
	}

	public void clearInput() {		
		webElement.clear();
	}
}