package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Home')]")))
public class MoodleHomeScreen extends Screen {

	public MoodleHomeScreen(Browser browser) {
		super(browser);
	}

	public MoodleCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to the created course " + course + "...");
		Element courseLink = browser.waitForElement(
				By.xpath("//*[contains(text(),'" + course + "')]"), 30);
		courseLink.click();
		MoodleCourseDetailsScreen moodleCourseDetailsScreen = browser.waitForPage(MoodleCourseDetailsScreen.class);
		browser.makeScreenshot();
		return moodleCourseDetailsScreen;
	}
}
