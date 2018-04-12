package com.mcgraw.test.automation.ui.mhcampus.course.aleks;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.IWantThisForMyStudentsBlock;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "secure.aleks.com")
@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.CSS, using = "img[src*='sso_school']")))
public class MhCampusALEKSScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptALEKS"))
	Element adoptALEKSButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData"))
	Element mainWindowFormDataFrame;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusALEKSScreen(Browser browser) {
		super(browser);
	}

	public MhCampusALEKSReadyToUseScreen adoptALEKS() {
		browser.pause(2000);
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		browser.clickOkInAlertIfPresent();
		Logger.info("Click 'Adopt ALEKS' Button");
		adoptALEKSButton.waitForPresence();
		adoptALEKSButton.click();
		
		return browser.waitForPage(MhCampusALEKSReadyToUseScreen.class, 30);
	}
}
