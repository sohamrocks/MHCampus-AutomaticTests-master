package com.mcgraw.test.automation.ui.mhcampus.course.connect;

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
public class MhCampusConnectStudentRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//div[@id='pageHdrInner']/*[contains(text(),'Create Success')]"))
	Element createSuccessText;

	public MhCampusConnectStudentRegistrationScreen(Browser browser) {
		super(browser);
	}

	public boolean isCreateSuccessTextPresent() {
		Logger.info("Check if 'Create Success' is present");
		boolean condition = createSuccessText.waitForPresence(30);
		browser.makeScreenshot();
		return condition;
	}

}
