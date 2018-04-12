package com.mcgraw.test.automation.ui.tegrity;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Courses')]")))
public class TegrityIntroductionScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "UserName"))
	Element userNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "StartRecordingButton"))
	Element startRecordingButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SignOutLink"))
	Element logoutLink;

	public TegrityIntroductionScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public TegrityInstanceLoginScreen logOut() {
		Logger.info("Logging out...");
		logoutLink.click();
		return browser.waitForPage(TegrityInstanceLoginScreen.class);
	}
	
	public boolean isSearchOptionPresent() {
		Logger.info("Checking if 'SEAFCH' option is present on the page");
		boolean isTextSearchFieldPresent = browser.isElementPresent(By.id("tegritySearchBox"));
		return isTextSearchFieldPresent;
	}
	
	public boolean isCoursePresent(String course) {
		Logger.info("Checking if course " + course + " is present on the page");
		try {
			boolean isCourseEnrolled = browser.isElementPresent(By.xpath(".//*[contains(text(),'" + course + "')]"));
			
			return isCourseEnrolled;

		} catch (Exception e) {
			Logger.info("Try again...Checking if course " + course + " is present on the page");		
			browser.pause(5000);
			boolean isCourseEnrolled = browser.isElementPresent(By.xpath(".//*[contains(text(),'" + course + "')]"));
	
			return isCourseEnrolled;
		}
	}
	
	public String getUserNameText() {
		userNameElement.waitForPresence(10);
		String welcomeText = userNameElement.getText();
		Logger.info("User name text is: '" + welcomeText + "'");
		return welcomeText;
	}
	
	public boolean isStartRecordingButtonPresent() {
		Logger.info("Check if 'Start Recording' button is present");
		return startRecordingButton.waitForPresence(5);
	}
	
}