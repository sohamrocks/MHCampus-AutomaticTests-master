package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Select a course...')]")))
public class D2lHomeScreenV10 extends D2lHomeScreen {

	public D2lHomeScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsPage() {
		D2lCourseDetailsScreenV10 d2lCourseDetailsScreenV10 = browser.waitForPage(D2lCourseDetailsScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lCourseDetailsScreenV10;
	}
}
