package com.mcgraw.test.automation.ui.tegrity;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'CourseTitle']")))
public class TegrityCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "UserName"))
	Element userNameElement;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "StartRecordingButton"))
	Element startRecordingButton;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'Courses')]"))
	Element goToCoursesButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ReportsLink"))
	Element reportsLink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "CourseTask"))
	Element courseTaskButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "CourseSettings"))
	Element courseSettingsOption;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SignOutLink"))
	Element logoutLink;

	public TegrityCourseDetailsScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isStartRecordingButtonPresent() {
		Logger.info("Check if 'Start Recording' button is present");
		return startRecordingButton.waitForPresence(10);
	}

	public String getUserNameText() {
		userNameElement.waitForPresence(5);
		String welcomeText = userNameElement.getText();
		Logger.info("User name text is: '" + welcomeText + "'");
		return welcomeText;
	}
	
	public boolean isReportsLinkPresent() {
		Logger.info("Checking if reports link is present on the page");
		reportsLink.waitForPresence(5);
		return reportsLink.isElementPresent();
	}
	
	public boolean isCourseSettingsOptionPresent() {
		Logger.info("Checking if Course Settings option is present on the page");
		courseTaskButton.click();
		courseSettingsOption.waitForPresence(5);
		return courseSettingsOption.isElementPresent();
	}

	public boolean isCoursePresent(String course) {
		Logger.info("Checking if course " + course + " is present on the page");
		Logger.info(String.format("Page title is: %s", browser.getTitle()));
		boolean isCourseEnrolled = browser.isElementPresentWithWait(By.xpath(".//*[contains(text(),'" + course + "')]"), 10);
		return isCourseEnrolled;
	}

	public boolean isSearchOptionPresent() {
		Logger.info("Checking if 'SEARCH' option is present on the page");
		boolean isTextSearchFieldPresent = browser.isElementPresent(By.id("tegritySearchBox"));
		return isTextSearchFieldPresent;
	}
	
	public TegrityIntroductionScreen goToCourses() {
		Logger.info("Going to list of courses...");
		goToCoursesButton.waitForPresence(5);
		goToCoursesButton.click();
		browser.pause(2000);
		TegrityIntroductionScreen tegrityIntroductionScreen = 
				browser.waitForPage(TegrityIntroductionScreen.class);	
		browser.makeScreenshot();
		return tegrityIntroductionScreen;
	}
	
	public TegrityInstanceLoginScreen logOut() {
		Logger.info("Logging out...");
		logoutLink.click();
		return browser.waitForPage(TegrityInstanceLoginScreen.class);
	}
}
