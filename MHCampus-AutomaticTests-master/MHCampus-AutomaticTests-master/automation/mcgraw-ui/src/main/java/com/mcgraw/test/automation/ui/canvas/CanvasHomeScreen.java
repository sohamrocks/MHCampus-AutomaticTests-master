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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='dashboard_header_container']/div/h1")))
//@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Recent Activity in')]")))
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
		Element chooseCourse = browser.waitForElement(By.xpath("//a[contains(text(),'" + course +   "')]"), 20);
		chooseCourse.jsClick(browser);
		canvasCourseDetailsScreen = 
				browser.waitForPage(CanvasCourseDetailsScreen.class);
		browser.makeScreenshot();		
		return canvasCourseDetailsScreen;
	}
	
	public CanvasCourseDetailsScreen goToCreatedCourseAsAdmin(String course) {
		
		CanvasCourseDetailsScreen canvasCourseDetailsScreen = null;	
		Logger.info("Going to the created course " + course + " as admin ...");
		Element admin = browser.waitForElement(By.id("global_nav_accounts_link"));
		admin.click();
		Element mcGrawHill2 = browser.waitForElement(By.xpath("//a[contains(text(),'McGraw-Hill2')]"), 20);
		mcGrawHill2.click();
		browser.pause(6000);
		Element courseName = browser.waitForElement(By.id("course_name"));
		courseName.clear();
		courseName.sendKeys(course);
		browser.pause(2000);	//Added by AlexandY to fix instability
		Element go = browser.waitForElement(By.xpath("//*[@id='new_course']//button[contains(text(),'Go')]"), 20);
		go.click();
		
		canvasCourseDetailsScreen = 
				browser.waitForPage(CanvasCourseDetailsScreen.class);
		browser.makeScreenshot();	
		return canvasCourseDetailsScreen;
	}
}