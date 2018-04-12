package com.mcgraw.test.automation.ui.canvas;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='right-side']/div[2]/h2")))
//@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Recent Activity in')]")))
public class CanvasCourseDetailsScreen extends Screen {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'grades']"))
	Element gradesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='courses_menu_item']/a"))
	Element courseMenu;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Tegrity Classes')]"))
	Element tegrityLinkElement;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'settings'][contains(text(),'Settings')]"))
	Element settingsBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "tool_content"))
	Element contentFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "global_nav_profile_link"))
	Element account;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button [contains(text(),'Logout')]"))
	Element logoutButton;
	
	private static By tegrityLink = By.xpath("//a[contains(text(),'Tegrity Classes')]");

	public CanvasCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public CanvasLoginScreen logout(){
		Logger.info("Logout from Canvas...");
		account.click();
		browser.pause(2000);
		browser.makeScreenshot();
		logoutButton.click();
		browser.pause(2000);
		browser.makeScreenshot();
		return browser.waitForPage(CanvasLoginScreen.class);
	}
	
	public CanvasGradebookScreen clickGradesButton() {
		Logger.info("Clicking Grades link...");
		gradesBtn.click();
		browser.pause(2000);
		browser.makeScreenshot();
		return browser.waitForPage(CanvasGradebookScreen.class);
	}

	public int getTegrityLinkCount() {
		Logger.info("Getting count of Tegrity links...");
		browser.makeScreenshot();
		return browser.getElementsCount(tegrityLink);
	}

    public CanvasCourseDetailsScreen goToCreatedCourse(String course) {
		
		CanvasCourseDetailsScreen canvasCourseDetailsScreen = null;	
		Logger.info("Going to the created course " + course + "...");
		Element courses = browser.waitForElement(By.id("global_nav_courses_link"));
		courses.click();
		Element cooseCourse = browser.waitForElement(By.xpath("//a[contains(text(),'" + course +   "')]"), 20);
		cooseCourse.jsClick(browser);
		canvasCourseDetailsScreen = 
				browser.waitForPage(CanvasCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return canvasCourseDetailsScreen;
	}
	
	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		try{
			tegrityLinkElement.waitForPresence();
			tegrityLinkElement.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchTo().frame(browser.waitForElementPresent(contentFrame));
			Element openTegrityButton = browser.waitForElement(By.id("btnOpenSessionList"));
			openTegrityButton.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
	}
}