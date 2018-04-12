package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "simnetonline.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(@class,'logo-simnet')]")))
public class MhCampusSimNetCreateLinkScreen extends Screen {
	
	
	// pair link ----------------------------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SubdomainTextBox"))
	Input inputTextBox;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "NextButton"))
	Element nextButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SchoolNameLabel"))
	Element stageName;
	
	
	
	public MhCampusSimNetCreateLinkScreen(Browser browser) {
		super(browser);
	}
		
	public MhCampusSimNetPairingPortalForInstructor CreateSimNetLink(String url,String ll){
		Logger.info("Pair SimNet link with url " + url);
		browser.switchTo().frame("tool_content");
		inputTextBox.clearAndTypeValue(url);
		browser.pause(2000);
		inputTextBox.sendKeys(ll);
		browser.pause(5000);
		browser.waitForElementPresent(nextButton).click();
		browser.switchTo().defaultContent();  //Edit by lior
		browser.pause(3000);
		browser.switchTo().frame("tool_content");  //Edit by lior
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusSimNetPairingPortalForInstructor.class);
		
	}
	
	//added by Yuliya
	public MhCampusSimNetPairingPortalForInstructor CreateSimNetLinkBB(String url,String ll){
		Logger.info("Pair SimNet link with url " + url);
		inputTextBox.clearAndTypeValue(url);
		browser.pause(2000);
		inputTextBox.sendKeys(ll);
		browser.pause(5000);
		browser.waitForElementPresent(nextButton).click();
		browser.pause(3000);
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusSimNetPairingPortalForInstructor.class);
		
	}
	

  // BB tests fail if use this line "browser.switchTo().frame("tool_content");"

}
