package com.mcgraw.test.automation.ui.canvas;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*/a/span[contains(text(),'Grades')]")))
public class CanvasGradebookScreen extends Screen {

	public CanvasGradebookScreen(Browser browser) {
		super(browser);
	}

	public CanvasAssignmentDetailsScreen clickOnAssignment(
			String assignmentTitle) {
		Logger.info("Clicking assignment " + assignmentTitle + " link...");
		/*
		Element assignmentLink = browser.waitForElement(By
				.xpath("//*[contains(text(),'" + assignmentTitle + "')]"));
		*/
		Element assignmentLink = browser.waitForElement(By
				.xpath("//*[text()[contains(.,'" + assignmentTitle + "')]]"));
		assignmentLink.click();
		return browser.waitForPage(CanvasAssignmentDetailsScreen.class, 20);
	}

	public boolean isAssignmnetPresent(String assignmentTitle) {
		return browser.isElementPresent(By.xpath("//*[contains(text(),'"
				+ assignmentTitle + "')]"));
	}
}