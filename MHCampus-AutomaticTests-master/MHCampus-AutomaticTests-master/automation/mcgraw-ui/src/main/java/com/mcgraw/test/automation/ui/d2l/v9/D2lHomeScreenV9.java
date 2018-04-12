package com.mcgraw.test.automation.ui.d2l.v9;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Select a course...')]")))
public class D2lHomeScreenV9 extends D2lHomeScreen {

	public D2lHomeScreenV9(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsPage() {
		return browser.waitForPage(D2lCourseDetailsScreenV9.class);
	}

	@Override
	public D2lLoginScreen waitForD2lLoginScreen() {
		return browser.waitForPage(D2lLoginScreenV9.class);
	}

	//Added by AleksandrY
	@Override
	public void clickMyAccountLink() {
		// TODO Auto-generated method stub
	}

	//Added by AleksandrY
	@Override
	public void clickLogOutLink() {
		// TODO Auto-generated method stub
	}
}
