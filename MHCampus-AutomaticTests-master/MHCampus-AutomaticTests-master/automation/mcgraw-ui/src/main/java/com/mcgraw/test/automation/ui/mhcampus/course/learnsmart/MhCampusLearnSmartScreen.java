package com.mcgraw.test.automation.ui.mhcampus.course.learnsmart;

import org.openqa.selenium.support.How;
import org.sikuli.api.ScreenRegion;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.IWantThisForMyStudentsBlock;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "mhlearnsmart.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "topHeaderText")))
public class MhCampusLearnSmartScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData"))
	Element mainWindowFormDataFrame;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptLearnSmart"))
	Element adoptLearnSmartButton;

	@DefinedLocators(@DefinedLocator(using = "//body[contains(text(),'Access denied')]"))
	Element accessDeniedMessage;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusLearnSmartScreen(Browser browser) {
		super(browser);
	}

	public MhCampusLearnSmartScreenWithOutBar adoptLearnSmart() {
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptLearnSmartButton");
		browser.waitForElementPresent(adoptLearnSmartButton).click();
		browser.clickOkInAlertIfPresent();
		// pause the browser as we can't perform waiters for flash elements
		Logger.info("Pause browser for 30 seconds to wait flash elements");
		browser.pause(30000);
		return browser.waitForPage(MhCampusLearnSmartScreenWithOutBar.class);
	}
	
	public void waitForNoThanksButtonAndClick() {
		Logger.info("Check if Flash No thanks button appeared");
		ScreenRegion noThanksButton = browser
				.waitForFlashElement("pictures/noThanksButton.png");
		Logger.info("Click No Thanks button");
		browser.clickFlashElementByImage(noThanksButton);
	}

}
