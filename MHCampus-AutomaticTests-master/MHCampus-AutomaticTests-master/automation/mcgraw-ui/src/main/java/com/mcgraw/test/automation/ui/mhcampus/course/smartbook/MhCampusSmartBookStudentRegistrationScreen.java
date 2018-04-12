package com.mcgraw.test.automation.ui.mhcampus.course.smartbook;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//h1[contains(text(),'SmartBook:')]")))
public class MhCampusSmartBookStudentRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = ".//*[@id='pageIntro']//*[contains(text(),'Student Registration')]"))
	Element smartBookRegistration;

	public MhCampusSmartBookStudentRegistrationScreen(Browser browser) {
		super(browser);
	}

	public boolean isSmartBookRegistrationDashboardPresent() {
		Logger.info("Check Smart Book Registration Dashboard is present");
		boolean condition = smartBookRegistration.waitForPresence(30);
		browser.makeScreenshot();
		return condition;
	}
}
