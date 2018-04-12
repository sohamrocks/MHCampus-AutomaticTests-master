package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

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
//@PageFrameIdentificator(locators = @DefinedLocators({@DefinedLocator(how = How.ID, using = "MainWindowFormData"),@DefinedLocator(how = How.ID, using = "frame")}))  Old version
//@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "top-navigation")))  Old version
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//h4[@id='topHeaderText' and contains(text(),'You are currently in SimNet')]")))

public class MhCampusSimNetScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptSimNet"))
	Element adoptSimNetButton;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusSimNetScreen(Browser browser) {
		super(browser);
	}

	public MhCampusSimNetPairingPortalForInstructor adoptSimNet() {
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptSimNetButton");
		browser.waitForElementPresent(adoptSimNetButton).click();
		return browser.waitForPage(MhCampusSimNetPairingPortalForInstructor.class);
	}
}
