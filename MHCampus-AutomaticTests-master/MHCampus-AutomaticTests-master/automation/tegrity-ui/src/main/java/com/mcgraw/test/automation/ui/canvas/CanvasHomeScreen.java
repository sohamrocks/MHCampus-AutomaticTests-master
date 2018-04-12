package com.mcgraw.test.automation.ui.canvas;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Recent Activity')]")))
public class CanvasHomeScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='courses_menu_item']/a"))
	Element courseMenu;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'View all courses')]"))
	Element viewAllCourses;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'TurnKey Canvas')]"))
	Element turnKeyCanvas;

	public CanvasHomeScreen(Browser browser) {
		super(browser);
	}

    public CanvasCourseDetailsScreen goToCreatedCourse(String course) {
		
		CanvasCourseDetailsScreen canvasCourseDetailsScreen = null;	
		Logger.info("Going to the created course " + course + "...");
		Element courses = browser.waitForElement(By.id("global_nav_courses_link"));
		courses.click();
		Element cooseCourse = browser.waitForElement(By.xpath("//a[contains(text(),'" + course +   "')]"), 20);
		cooseCourse.jsClick(browser);
		canvasCourseDetailsScreen = 
				browser.waitForPage(CanvasCourseDetailsScreen.class);
		browser.makeScreenshot();		
		return canvasCourseDetailsScreen;
	}
}
