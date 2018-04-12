package com.mcgraw.test.automation.ui.mhcampus.course.activesim;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.IWantThisForMyStudentsBlock;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "ConnectWindowFormContainer")))
public class MhCampusActivSimScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptActiveSim"))
	Element adoptActiveSimButton;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusActivSimScreen(Browser browser) {
		super(browser);
	}

	public void adoptActiveSim() {
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptActiveSimButton");
		browser.waitForElementPresent(adoptActiveSimButton).click();
		browser.pause(3000);
	}
}
