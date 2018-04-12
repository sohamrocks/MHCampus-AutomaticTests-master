package com.mcgraw.test.automation.ui.mhcampus.course.smartbook;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.IWantThisForMyStudentsBlock;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "flowFlash")))
public class MhCampusSmartBookScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "MainWindowFormData"))
	Element mainWindowFormDataFrame;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptLearnSmartSmartBook"))
	Element adoptSmartBookButton;
	
	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusSmartBookScreen(Browser browser) {
		super(browser);
	}

	public MhCampusSmartBookFlowRunner adoptSmartBook() {
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		browser.waitForElementPresent(adoptSmartBookButton).click();
		MhCampusSmartBookFlowRunner mhCampusSmartBookFlowRunner = browser
				.waitForPage(MhCampusSmartBookFlowRunner.class);
		return mhCampusSmartBookFlowRunner;
	}
}
