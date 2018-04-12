package com.mcgraw.test.automation.ui.mhcampus.course.gdp;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.IWantThisForMyStudentsBlock;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
@PageFrameIdentificator(locators = @DefinedLocators({@DefinedLocator(how = How.ID, using = "MainWindowFormData"),@DefinedLocator(how = How.ID, using = "frame")}))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CSS, using = "[alt='Information Center']")))
public class MhCampusGDPScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//span[contains(.,'I want this for my students')]"))
	Element iWantThisForMyStudentsButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptGDP"))
	Element adoptGDPButton;

	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusGDPScreen(Browser browser) {
		super(browser);
	}

	public MhCampusGDPPairingPortal adoptGDP() {
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptGDPButton");
		browser.waitForElementPresent(adoptGDPButton).click();
		return browser.waitForPage(MhCampusGDPPairingPortal.class);
	}
}
