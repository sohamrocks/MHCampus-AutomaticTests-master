package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "connect.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[@class='pair_course_container']")))
public class MhCampusConnectSaveCourseSectionPair extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//div[@class='success_curve_center']/b[contains(.,'done!')]"))
	Element successMessage;

	public MhCampusConnectSaveCourseSectionPair(Browser browser) {
		super(browser);
	}

	public boolean isSuccessMessagePresent() {
		Logger.info("Check the success message present");
		browser.makeScreenshot();
		return successMessage.waitForPresence(30);
	}

}
