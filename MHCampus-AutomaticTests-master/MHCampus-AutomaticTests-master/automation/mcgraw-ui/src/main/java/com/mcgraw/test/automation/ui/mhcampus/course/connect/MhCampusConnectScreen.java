package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
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

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "connectWindowFormData")))  
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*/a[contains(text(),'My account')] | //*/a[contains(text(),'Help')] | //*/a[contains(text(),'Sign out')]")))
public class MhCampusConnectScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//*[contains(text(),\"We're sorry\")]"))
	Element errorHeader;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "add-button"))
	Element selectButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AdoptConnect"))
	Element adoptConnectButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "connectWindowFormData"))
	Element connectWindowFormDataFrame;

	IWantThisForMyStudentsBlock iWantThisForMyStudentsBlock;

	public IWantThisForMyStudentsBlock getiWantThisForMyStudentsBlock() {
		return iWantThisForMyStudentsBlock;
	}

	public MhCampusConnectScreen(Browser browser) {
		super(browser);
	}

	public boolean isErrorMessagePresent() {
		Logger.info("Check if error message present");
		browser.switchTo().frame(connectWindowFormDataFrame);
		boolean condition = errorHeader.waitForPresence(5);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
		return condition;
	}

	public MhCampusConnectScreen selectProduct() {
		Logger.info("Select product");
		browser.doInFrame(connectWindowFormDataFrame, new Action() {
			@Override
			public void perform() {
				browser.waitForElementPresent(selectButton).click();

			}
		});
		return browser.waitForPage(MhCampusConnectScreen.class);
	}

	public MhCampusConnectCourseSectionPairScreen adoptConnect() {
		browser.switchTo().frame(connectWindowFormDataFrame);
		//Element ok = browser.waitForElement(By.xpath("//a[contains(text(),'OK')]"));
		//ok.click();
		//browser.pause(3000);
		try{
//			Element close = browser.waitForElement(By.xpath("//div[@id='MHCampusToolTip']/a"));
			Element close = browser.waitForElement(By.xpath(".//div[@class='modalHeader']/a[@title='close window']")); // AlexandrY fix according to changes on ui
			close.click();
		}catch(Exception e){
			Logger.info("Can't click 'close' button: " + e.getStackTrace());
		}
		browser.switchTo().defaultContent();
		iWantThisForMyStudentsBlock.waitForPresence();
		iWantThisForMyStudentsBlock.clickIWantThisForMyStudentsButton();
		Logger.info("Click adoptConnectButton");
		browser.waitForElementPresent(adoptConnectButton).click();

		return browser.waitForPage(MhCampusConnectCourseSectionPairScreen.class);

	}
}
