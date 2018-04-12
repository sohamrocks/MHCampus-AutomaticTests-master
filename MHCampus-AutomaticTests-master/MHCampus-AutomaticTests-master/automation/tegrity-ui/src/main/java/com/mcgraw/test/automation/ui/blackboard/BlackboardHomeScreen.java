package com.mcgraw.test.automation.ui.blackboard;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'MyInstitution.label'][@class = 'active']")))
public class BlackboardHomeScreen extends Screen {

	public BlackboardHomeScreen(Browser browser) {
		super(browser);
	}
	
	public BlackboardCourseDetails clickOnCreatedCourse(String course) {
		
		Logger.info("Going to the created course " + course + "...");
		try{
			Element close = browser.waitForElement(By.id("finishLater"), 10);
			close.click();
		}catch(Exception e){
			// TO DO
		}
		Element courseLink = browser.waitForElement(By.linkText(course));
		courseLink.click();
			
		return browser.waitForPage(BlackboardCourseDetails.class, 30);
	}
}
