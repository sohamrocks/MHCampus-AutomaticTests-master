package com.mcgraw.test.automation.ui.mhcampus.course.learnsmart;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "paris.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Student Registration')]")))
public class MhCampusLearnSmartStudentRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'You are currently logged in')]"))
	Element youAreCurrentlyLoggedInAsMessagePresent;

	public MhCampusLearnSmartStudentRegistrationScreen(Browser browser) {
		super(browser);
	}

	public boolean isCreateSuccessTextPresent() {
		Logger.info("Check if 'You are currently logged in with' message is present");
		boolean condition = youAreCurrentlyLoggedInAsMessagePresent
				.waitForPresence(30);
		browser.makeScreenshot();
		return condition;
	}

}
