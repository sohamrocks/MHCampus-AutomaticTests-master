package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.TimeoutException;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//header[@id='page-header']//a[text()='Courses']")))
public class MoodleCourseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[@id='page-content']//input[@id='coursesearchbox']"))
	Input searchCourseField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[@id='page-content']//input[@value='Go' and @type='submit']"))
	Input searchGoButton;
	
	public MoodleCourseScreen(Browser browser) {
		super(browser);
	}

	public MoodleCourseDetailsScreen findAndSelectCourse(String courseName){
		browser.waitForElementPresent(searchCourseField);
		searchCourseField.clearAndTypeValue(courseName);
		browser.pause(6000);
		browser.waitForElementPresent(searchGoButton).click();
		browser.pause(1000);
		browser.makeScreenshot();
		try{
			Element courseLink = browser.waitForElement(By.xpath(String.format
				(".//section[@id='region-main']//span[contains(text(),'%s')]", courseName)),30);	
			courseLink.click();
			return browser.waitForPage(MoodleCourseDetailsScreen.class, 20);
		}catch(TimeoutException e){
			Logger.info(String.format("Cannot find course <%s>", courseName));
			throw e;
		}
	}
	

}
