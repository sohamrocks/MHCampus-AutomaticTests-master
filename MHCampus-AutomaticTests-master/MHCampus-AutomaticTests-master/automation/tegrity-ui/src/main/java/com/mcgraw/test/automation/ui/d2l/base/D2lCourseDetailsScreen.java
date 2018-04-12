package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

public abstract class D2lCourseDetailsScreen extends Screen {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	private static final String POPUP_TITLE = "popup_blocker_msg";

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Content')]"))
	protected Element contentLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Grades')]"))
	protected Element gradesLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Tegrity')]"))
	protected Element tegrityLinkElement;
	
	private static By tegrityLink = By.xpath("//span[contains(text(),'Tegrity')]");

	public D2lCourseDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public int getTegrityLinksCount() {
		Logger.info("Getting count of Tegrity links...");
		browser.makeScreenshot();
		return browser.getElementsCount(tegrityLink);
	}
	
	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");
		browser.pause(2000);
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		String handle = browser.getWindowHandle();
		try{
			tegrityLinkElement.waitForPresence();
			tegrityLinkElement.click();
			browser.pause(3000);
			
			browser.switchToLastWindow();
			if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"),3)){
				browser.makeScreenshot();
				browser.close();
				browser.pause(500);
				browser.switchTo().window(handle);
				browser.pause(1000);
				tegrityLinkElement.waitForPresence();
				tegrityLinkElement.click();
				browser.pause(3000);
				browser.switchToLastWindow();
			}
			
			tegrityCourseDetailsScreen = browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		} catch (Exception e) {
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

	final public D2lContentCourseScreen clickContentLink() {
		Logger.info("Clicking Content link...");
		browser.waitForElementPresent(contentLink).click();
		return waitForD2lContentCoursePage();
	}

	final public D2lGradesDetailsScreen clickGradesLink() {
		Logger.info("Clicking Grades link...");
		browser.waitForElementPresent(gradesLink).click();
		return waitForD2lGradesDetailsPage();
	}

	protected abstract D2lContentCourseScreen waitForD2lContentCoursePage();

	protected abstract D2lGradesDetailsScreen waitForD2lGradesDetailsPage();
}
