package com.mcgraw.test.automation.ui.canvas;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Submitting')]")))
public class CanvasAssignmentDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'description user_content teacher-version enhanced']"))
	Element descriptionField;

	public CanvasAssignmentDetailsScreen(Browser browser) {
		super(browser);
	}

	public String getDescription() {
		browser.pause(6000);
		return descriptionField.getText();
	}
}
