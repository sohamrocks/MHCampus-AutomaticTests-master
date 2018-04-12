package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.By;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='content']")))
public class SakaiCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "html/body/div[1]/ul/li[2]/span/a"))
	Element editToolsBtn;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'toolMenuIcon icon-sakai-gradebook-tool ']"))
	Element gradebookBtn;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'toolMenuLink ']//span[contains(text(),'MhCampus')]"))
	Element mhCampusBtn;

	private By mhCampusBtnXpath = By
			.xpath("//*[@class = 'toolMenuLink ']//span[contains(text(),'MhCampus')]");

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

	public MhCampusIntroductionScreen clickMhCampusLink() {
		if (mhCampusBtn.waitForPresence(10)) {
			Logger.info("Clicking MhCampus link...");
			mhCampusBtn.click();
		} else {
			Logger.info("MhCampus button is not present");
		}
		
		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser
				.waitForPage(MhCampusIntroductionScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusIntroductionScreen;
	}

	public int getMhCampusLinksCount() {
		browser.makeScreenshot();
		mhCampusBtn.waitForPresence(5);
		return browser.getElementsCount(mhCampusBtnXpath);
	}

	public SakaiGradesScreen clickGradebookBtn() {
		gradebookBtn.click();
		SakaiGradesScreen sakaiGradesScreen =
				browser.waitForPage(SakaiGradesScreen.class);
		browser.makeScreenshot();
		return sakaiGradesScreen;
	}
}
