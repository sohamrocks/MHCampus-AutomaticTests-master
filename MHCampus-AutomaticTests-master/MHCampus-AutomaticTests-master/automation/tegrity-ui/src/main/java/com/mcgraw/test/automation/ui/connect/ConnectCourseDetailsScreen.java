package com.mcgraw.test.automation.ui.connect;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageRelativeUrl(relative = false, value = "connectqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))
public class ConnectCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//*[@class='courseName']"))
	Element courseNameElement;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='instructor-name']"))
	Element instructorNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "recordViewLectures"))
	Element goToLecturesLink;
	
	public ConnectCourseDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public boolean isCoursePresentInConnect(String courseName){
		Logger.info("Check course " + courseName + " present in Connect under instructor");
		courseNameElement.waitForPresence(20);
		if(courseNameElement.getText().equals(courseName))
			return true;
		return false;
	}

	public boolean isInstructorNamePresentInConnect(String instructorName){
		Logger.info("Check instructor " + instructorName + " present in Connect in the current course");
		if(instructorNameElement.getText().equals(instructorName))
			return true;
		return false;
	}
	
	public  TegrityCourseDetailsScreen goToTegrity() {
		Logger.info("Go to Tegrity");
		goToLecturesLink.waitForPresence(20);
		goToLecturesLink.click();
		browser.pause(6000);
		browser.switchToLastWindow();	
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = 
				browser.waitForPage(TegrityCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return tegrityCourseDetailsScreen;
	}
}