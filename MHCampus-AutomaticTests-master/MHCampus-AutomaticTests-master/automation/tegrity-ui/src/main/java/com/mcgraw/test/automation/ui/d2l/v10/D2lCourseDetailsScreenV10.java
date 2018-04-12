package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'CourseName')]")))
public class D2lCourseDetailsScreenV10 extends D2lCourseDetailsScreen {

	public D2lCourseDetailsScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lContentCourseScreen waitForD2lContentCoursePage() {
		D2lContentCourseScreenV10 d2lContentCourseScreenV10 = browser.waitForPage(D2lContentCourseScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lContentCourseScreenV10;
	}

	@Override
	protected D2lGradesDetailsScreen waitForD2lGradesDetailsPage() {
		D2lGradesDetailsScreenV10 d2lGradesDetailsScreenV10 = browser.waitForPage(D2lGradesDetailsScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lGradesDetailsScreenV10;
	}
}
