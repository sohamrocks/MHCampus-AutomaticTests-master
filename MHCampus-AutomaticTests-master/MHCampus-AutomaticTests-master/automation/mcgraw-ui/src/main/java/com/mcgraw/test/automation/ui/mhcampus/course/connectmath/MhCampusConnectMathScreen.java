package com.mcgraw.test.automation.ui.mhcampus.course.connectmath;

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
import com.mcgraw.test.automation.ui.mhcampus.course.connectmath.MhCampusConnectMathReadyToUseScreen;
@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CSS, using = "a[href*='connectmath.com'] img")))
public class MhCampusConnectMathScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptConnectMath"))
	Element adoptConnectMathButton;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusConnectMathScreen(Browser browser) {
		super(browser);
	}

	public MhCampusConnectMathReadyToUseScreen adoptConnectMath() {
		iWantThisForMyStudentsBlock.waitForPresence();
		browser.pause(1000);	//AlexandrY try to fix local instability
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptConnectMathButton");
		browser.pause(1000);    //AlexandrY try to fix local instability
		browser.waitForElementPresent(adoptConnectMathButton).click();
		return browser.waitForPage(MhCampusConnectMathReadyToUseScreen.class);
	}
}
