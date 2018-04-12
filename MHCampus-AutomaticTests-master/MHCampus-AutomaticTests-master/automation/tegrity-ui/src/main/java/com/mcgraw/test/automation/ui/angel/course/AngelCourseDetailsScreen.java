package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='breadcrumbMenu']/li[@id='bcSectionHome' and contains(.,'Course')]")))
public class AngelCourseDetailsScreen extends AngelCourseContext {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	private static final String POPUP_TITLE = "popup_blocker_msg";
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@href = '/Portal/Nuggets/TegrityCampus/sso.asp']"))
	Element tegrityCampusLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id = 'btnHome']//span"))
	Element homeLink;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "portalRefreshAll"))
	Element refreshButton;

	@DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Report')]"))
	Element reportButton;
	
	public AngelCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public boolean isTegrityCampusLinkPresent() {
		Logger.info("Checking if Tegrity Campus link is present...");
		clickRefresh();
		boolean isPresent = tegrityCampusLink.waitForPresence();
		return isPresent;
	}
	
	public TegrityCourseDetailsScreen clickTegrityCampusLink() {
		Logger.info("Clicking Tegrity Campus link...");
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		String title = browser.getTitle();
		try{
			tegrityCampusLink.waitForPresence();
			tegrityCampusLink.click();
			browser.pause(2000);
			browser.switchToWindow("Tegrity");

			if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"), 3)){
				browser.close();
				browser.pause(1000);
				browser.switchToWindow(title);
				browser.switchTo().frame(tabFrame);
				tegrityCampusLink.waitForPresence();
				tegrityCampusLink.click();
				browser.pause(2000);
				browser.switchToWindow(TEGRITY_TITLE);
			}

			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);

		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchToWindow(POPUP_TITLE);
			Element openTegrityButton = browser.waitForElement(By.id("btnOpenSessionList"));
			openTegrityButton.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
	}

	public AngelHomeScreen goToHomePage() {
		homeLink.click();
		return browser.waitForPage(AngelHomeScreen.class, 20);
	}

	private void clickRefresh() {
		Logger.info("Clicking refresh...");
		refreshButton.waitForPresence(10);
		refreshButton.click();
	}
}
