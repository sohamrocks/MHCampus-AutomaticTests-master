package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Edit Homepage')]")))
public class D2lEditHomepageScreenV10 extends D2lEditHomepageScreen {

	public D2lEditHomepageScreenV10(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsScreenPage() {
		D2lCourseDetailsScreen d2lCourseDetailsScreen = 
				browser.waitForPage(D2lCourseDetailsScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lCourseDetailsScreen;
	}

	
	
}
