package com.mcgraw.test.automation.ui.mhcampus.course.learnsmart;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "mhlearnsmart.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "disable_menu")))
public class MhCampusLearnSmartScreenWithOutBar extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "[type='application/x-shockwave-flash']"))
	Element learnSmartDashboard;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData"))
	Element mainWindowFormDataFrame;

	public MhCampusLearnSmartScreenWithOutBar(Browser browser) {
		super(browser);
	}

	public boolean isLearnSmartDashboardPresent() {
		return isLearnSmartDashboardPresent(false);
	}

	public boolean isLearnSmartDashboardPresent(boolean isInFrame) {
		Logger.info("Check if LearnSmartDashboard present");
		boolean condition = false;
		if (isInFrame) {
			browser.switchTo().frame(
					browser.waitForElementPresent(mainWindowFormDataFrame));
			condition = learnSmartDashboard.waitForPresence(30);
			browser.switchTo().defaultContent();

		} else {
			condition = learnSmartDashboard.waitForPresence(30);
		}		
		browser.makeScreenshotUsingRobot();
		return condition;
	}

}
