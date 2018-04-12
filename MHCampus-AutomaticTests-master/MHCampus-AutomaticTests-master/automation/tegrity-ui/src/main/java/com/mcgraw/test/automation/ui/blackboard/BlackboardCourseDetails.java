package com.mcgraw.test.automation.ui.blackboard;

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
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'Courses.label'][@class = 'active']")))
public class BlackboardCourseDetails extends Screen {


	private static final String TEGRITY_TITLE = "Tegrity - ";
	private static final String POPUP_TITLE = "popup_blocker_msg";
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='MyInstitution.label']/a/span[1]"))
	Element myInstitutionLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//span[contains(text(),'Tegrity Classes')]"))
	Element tegrityClassesLink;
	
	private static By tegrityLink = By.xpath(".//span[contains(text(),'Tegrity Classes')]");

	public BlackboardCourseDetails(Browser browser) {
		super(browser);
	}

	public int getTegrityLinksCount() {
		clickRefresh();
		browser.pause(2000);
		browser.makeScreenshot();
		int linksCount = browser.getElementsCount(tegrityLink);
		return linksCount;
	}
	
	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		String handle = browser.getWindowHandle();
		try{
			tegrityClassesLink.waitForPresence();
			tegrityClassesLink.click();			
			browser.pause(3000);
			browser.switchToLastWindow();
			
			if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"),3)){
				browser.makeScreenshot();
				browser.close();
				browser.pause(500);
				browser.switchTo().window(handle);
				browser.pause(1000);
				tegrityClassesLink.waitForPresence();
				tegrityClassesLink.click();
				browser.pause(3000);
				browser.switchToLastWindow();
			}
						
			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchToWindow(POPUP_TITLE);
			Element openTegrityButton = browser.waitForElement(By.id("btnOpenSessionList"));
			openTegrityButton.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
	}

	public BlackboardHomeScreen clickMyInstitutionLink() {
		Logger.info("Clicking Institution link...");
		myInstitutionLink.click();
		browser.pause(6000);
		return browser.waitForPage(BlackboardHomeScreen.class);
	}

	private void clickRefresh() {
		Element refreshLink = browser.findElement(By
				.xpath(".//*[@id='refreshMenuLink']/a//span"));
		refreshLink.click();
	}
}
