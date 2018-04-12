package com.mcgraw.test.automation.ui.ecollege;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Home')]")))
public class ECollegeHomeScreen extends Screen{

	public ECollegeHomeScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	public ECollegeCourseDetailsScreen goToCourse(String course) {
		Logger.info("Going to the created course " + course + "...");
		if(browser.isElementPresentWithWait(By.xpath("//img[(@alt='Click to expand the list')]"), 5)){
			Element openCoursesLink = browser.findElement(By.xpath("//img[(@alt='Click to expand the list')]"));
			openCoursesLink.click();
		}
		Element courseLink = browser.waitForElement(
				By.xpath("//a[@class = 'MainContentLink']//b[contains(text(),'" + course + "')]"), 10);
		courseLink.click();
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = 
				browser.waitForPage(ECollegeCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return eCollegeCourseDetailsScreen;
	}
	
	public ECollegeCourseDetailsForInstructorScreen goToCourseUsInsrtuctor(String course) {
		Logger.info("Going to the created course " + course + " us Instructor...");
		if(browser.isElementPresentWithWait(By.xpath("//img[(@alt='Click to expand the list')]"), 10)){
			Element openCoursesLink = browser.findElement(By.xpath("//img[(@alt='Click to expand the list')]"));
			openCoursesLink.click();
		}
		Element courseLink = browser.waitForElement(
				By.xpath("//a[@class = 'MainContentLink']//b[contains(text(),'" + course + "')]"), 10);
		courseLink.click();
		ECollegeCourseDetailsForInstructorScreen eCollegeCourseDetailsForInstructorScreen = 
				browser.waitForPage(ECollegeCourseDetailsForInstructorScreen.class, 100);
		browser.makeScreenshot();
		
		return eCollegeCourseDetailsForInstructorScreen;
	}
}