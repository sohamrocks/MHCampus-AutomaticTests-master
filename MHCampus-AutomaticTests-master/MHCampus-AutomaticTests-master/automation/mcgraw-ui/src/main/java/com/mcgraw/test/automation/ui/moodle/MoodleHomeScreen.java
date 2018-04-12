package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Home')]")))
public class MoodleHomeScreen extends Screen {
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[@role='navigation']//a[text()='Courses']"))
	Element coursesLinkInNavBar;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[@id='frontpage-course-list']//a[contains(text(),'All courses')]"))
	Element allCoursesLink;

	public MoodleHomeScreen(Browser browser) {
		super(browser);
	}

	public MoodleCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to the created course " + course + "...");
		Element courseLink = browser.waitForElement(
				By.xpath("//*[contains(text(),'" + course + "')]"), 30);
		courseLink.click();
		return browser.waitForPage(MoodleCourseDetailsScreen.class);
	}
	
	//added by AleksandrY
	public MoodleCourseScreen clickOnCoursesInNavBar(){
		Logger.info("Click on 'Courses' in left navigation bar");
		browser.waitForElementPresent(coursesLinkInNavBar).click();
		return browser.waitForPage(MoodleCourseScreen.class, 20);
	}

	//added by AleksandrY
	public MoodleCourseScreen clickOnAllCoursesLink(){
		Logger.info("Click on 'all Courses' link at the bottom of courses list");
		browser.waitForElementPresent(allCoursesLink);
		allCoursesLink.jsClick(browser);
		return browser.waitForPage(MoodleCourseScreen.class, 20);
	}
	
}
