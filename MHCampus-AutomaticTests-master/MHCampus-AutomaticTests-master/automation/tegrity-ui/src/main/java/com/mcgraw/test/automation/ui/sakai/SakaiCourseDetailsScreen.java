package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.By;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='content']")))
public class SakaiCourseDetailsScreen extends Screen {
	
	//private static final String TEGRITY_TITLE = "Tegrity";
	
	@DefinedLocators(@DefinedLocator(using = "html/body/div[1]/ul/li[2]/span/a"))
	Element editToolsBtn;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'toolMenuIcon icon-sakai-gradebook-tool ']"))
	Element gradebookBtn;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'toolMenuLink ']//span[contains(text(),'Tegrity')]"))
	Element tegrityBtn;
	
	private By tegrityBtnXpath = By.xpath("//*[@class = 'toolMenuLink ']//span[contains(text(),'Tegrity')]");
	
	public SakaiCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public SakaiExternalToolsScreen clickToolsBtn() {
		Logger.info("Clicking Edit Tools button...");
		browser.switchTo().frame(0);
		editToolsBtn.click();
		SakaiExternalToolsScreen sakaiExternalToolsScreen = 
				browser.waitForPage(SakaiExternalToolsScreen.class);
		browser.makeScreenshot();
		return sakaiExternalToolsScreen;
	}

	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		try{
			tegrityBtn.waitForPresence();
			tegrityBtn.click();
			browser.pause(2000);
			//browser.switchToWindow(TEGRITY_TITLE);	
			browser.switchToLastWindow();	
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchTo().frame(0);
			try{
				browser.switchTo().frame(0);
			}catch(Exception ex){
				Logger.info("Can't switsh to frame");
			}
			Element popupLink = browser.waitForElement(By.id("popupLink"));
			popupLink.click();
			browser.pause(2000);
			//browser.switchToWindow(TEGRITY_TITLE);
			browser.switchToLastWindow();	
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
	}

	public int getTegrityLinksCount() {
		browser.makeScreenshot();
		tegrityBtn.waitForPresence(5);
		return browser.getElementsCount(tegrityBtnXpath);
	}

	public SakaiGradesScreen clickGradebookBtn() {
		gradebookBtn.click();
		SakaiGradesScreen sakaiGradesScreen =
				browser.waitForPage(SakaiGradesScreen.class);
		browser.makeScreenshot();
		return sakaiGradesScreen;
	}
}
