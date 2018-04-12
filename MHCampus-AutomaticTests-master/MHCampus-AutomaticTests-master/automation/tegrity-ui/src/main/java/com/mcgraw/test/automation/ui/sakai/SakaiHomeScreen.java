package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'topnav']")))
public class SakaiHomeScreen extends Screen {

	private String courseBtnPattern = ".//*[@id='topnav']//*[@data = '<currentCourse>']//..//a[@aria-haspopup = 'true']";

	public SakaiHomeScreen(Browser browser) {
		super(browser);
	}

	public SakaiCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to Created Course...");
		if (browser.isElementPresent(By.xpath(courseBtnPattern.replace(
				"<currentCourse>", course)))) {
			Element courseBtn = browser.findElement(By.xpath(courseBtnPattern
					.replace("<currentCourse>", course)));
			courseBtn.click();
		} else {
			Logger.info(course + " doesn't present");
		}
		SakaiCourseDetailsScreen sakaiCourseDetailsScreen = browser.waitForPage(SakaiCourseDetailsScreen.class);
		browser.makeScreenshot();
		return sakaiCourseDetailsScreen;
	}
}
