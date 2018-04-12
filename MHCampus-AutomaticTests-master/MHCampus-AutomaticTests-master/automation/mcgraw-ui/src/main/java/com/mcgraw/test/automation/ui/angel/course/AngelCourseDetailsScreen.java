package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.angel.AngelHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='breadcrumbMenu']/li[@id='bcSectionHome' and contains(.,'Course')]")))
public class AngelCourseDetailsScreen extends AngelCourseContext {
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@href = '/Portal/Nuggets/MHCampus/sso.asp']"))
	Element mhCampusLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id = 'btnHome']//span"))
	Element homeLink;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "portalRefreshAll"))
	Element refreshButton;

	@DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Report')]"))
	Element reportButton;

	public AngelCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public boolean isMhCampusLinkPresent() {
		clickRefresh();
		boolean isPresent = mhCampusLink.waitForPresence();
		browser.makeScreenshot();
		return isPresent;
	}

	public MhCampusIntroductionScreen clickMhCampusLink() {
		Logger.info("Clicking MH Campus Link...");
		mhCampusLink.waitForPresence(10);
		mhCampusLink.click();
		browser.pause(6000);
		return browser.waitForPage(MhCampusIntroductionScreen.class, 10);
	}

	public AngelHomeScreen goToHomePage() {
		homeLink.click();
		return browser.waitForPage(AngelHomeScreen.class, 10);
	}

	private void clickRefresh() {
		Logger.info("Clicking refresh...");
		refreshButton.waitForPresence(10);
		refreshButton.click();
	}
}
