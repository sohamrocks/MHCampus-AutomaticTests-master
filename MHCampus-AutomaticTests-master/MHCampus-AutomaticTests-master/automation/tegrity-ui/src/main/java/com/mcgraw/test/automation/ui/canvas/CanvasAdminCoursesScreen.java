package com.mcgraw.test.automation.ui.canvas;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Courses In This Account')]")))
public class CanvasAdminCoursesScreen extends Screen {

	public CanvasAdminCoursesScreen(Browser browser) {
		super(browser);
	}

	public CanvasCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to the created course " + course + "...");
		Element courseLink = browser.waitForElement(By
				.xpath("//*[contains(text(),'" + course + "')]"));
		courseLink.click();
		return browser.waitForPage(CanvasCourseDetailsScreen.class);
	}
}
