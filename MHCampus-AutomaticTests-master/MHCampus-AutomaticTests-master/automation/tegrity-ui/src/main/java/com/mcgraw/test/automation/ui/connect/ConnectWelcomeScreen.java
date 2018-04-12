package com.mcgraw.test.automation.ui.connect;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))
public class ConnectWelcomeScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//div[@id='headerStripe']//ul/li[1]"))
	Element userFullName;

	@DefinedLocators(@DefinedLocator(using = "//div[@class='section-description large-8 columns']/p[1]/a"))
	Element course;

	public ConnectWelcomeScreen(Browser browser) {
		super(browser);
	}
	
	public String getUserFullNameName() {
		Logger.info("User full name is: " + userFullName.getText());
		return userFullName.getText();
	}
	
	public String getCourseName() {
		Logger.info("Course name is: " + course.getText());
		return course.getText();
	}
	
	public Boolean isCoursePresent(String courseName) {
		Logger.info("Check course: " + courseName + " is present");
		Boolean flag = browser.isElementPresentWithWait(By.xpath("//a[contains(text(),'" + courseName + "')]"), 10);
		return flag;
	}
		
	public ConnectCourseDetailsScreen goToCourse(String courseName) {
		Logger.info("Go to course: " + courseName);
		Element courseLink = browser.waitForElement(By.xpath("//a[contains(text(),'" + courseName + "')]"), 10);
		courseLink.click();
		ConnectCourseDetailsScreen connectCourseDetailsScreen = 
				browser.waitForPage(ConnectCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return connectCourseDetailsScreen;
	}
}
