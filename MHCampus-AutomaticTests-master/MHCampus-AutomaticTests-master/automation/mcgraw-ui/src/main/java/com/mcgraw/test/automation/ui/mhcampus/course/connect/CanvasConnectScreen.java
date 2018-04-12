package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))
public class CanvasConnectScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//div[@id='headerStripe']//ul/li[1]"))
	Element userFullName;

	@DefinedLocators(@DefinedLocator(using = "//div[@class='large-3 columns pairing-content text-left']/span[1]"))
	Element section;

	@DefinedLocators(@DefinedLocator(using = "//div[@class='large-3 columns pairing-content text-left']/span[2]"))
	Element course;

	@DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'done!')]"))
	Element successMessage;
	
	@DefinedLocators(@DefinedLocator(using = "//span[@class='lock-image']"))
	Element lockImage;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'go to section home page')]"))
	Element goToHome;

	public CanvasConnectScreen(Browser browser) {
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
	
	public String getSectionName() {
		Logger.info("Section name is: " + section.getText());
		return section.getText();
	}


	public boolean isSuccessMessagePresent() {
		Logger.info("Check if success message present");
		return successMessage.waitForPresence(5);
	}

	public boolean isLockImagePresent() {
		Logger.info("Check if lock image present");
		return lockImage.waitForPresence(5);
	}
	
	public CanvasConnectCourseDetailsScreen goToHomePage() {
		Logger.info("Click 'Go to home page' link");
		goToHome.click();
		CanvasConnectCourseDetailsScreen canvasConnectCourseDetailsScreen = 
				browser.waitForPage(CanvasConnectCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return canvasConnectCourseDetailsScreen;
	}
}
