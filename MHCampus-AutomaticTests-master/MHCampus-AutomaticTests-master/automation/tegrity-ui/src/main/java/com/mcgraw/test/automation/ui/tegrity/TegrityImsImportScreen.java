package com.mcgraw.test.automation.ui.tegrity;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("/")                                                                                                                   
@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='main']/div/iframe"))) 
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'fileToUpload']")))
public class TegrityImsImportScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'sign out')]"))
	Element logoutLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Admin Dashboard')]"))
	Element adminDashboardLink;
	
	public TegrityImsImportScreen(Browser browser) {
		super(browser);
	}
	
	public void clickLogout()
	{
		Logger.info("Logging out...");
		logoutLink.click();
		browser.pause(2000);
	}
	
	public TegrityInstanceDashboardScreen clickAdminDashboardLink()
	{
		Logger.info("Come back to Admin Dashboard...");
		adminDashboardLink.click();
		return browser.waitForPage(TegrityInstanceDashboardScreen.class, 30);
	}
	
	public void uploadFile(String fullPathToFile) throws Exception {
		
		Logger.info("Uploading XML file: " + fullPathToFile);
		Element imsImportFrame = browser.waitForElement(By.xpath(".//*[@id='main']/div/iframe"));
		browser.switchTo().frame(imsImportFrame);		
		Element selectFileButton = browser.findElement(By.cssSelector("input[id='fileToUpload']"));
		selectFileButton.setAttribute("style", "", browser);
		browser.pause(500);    //AlexandrY added to fix instability on server
		selectFileButton.sendKeys(fullPathToFile);
		Logger.info("Waiting for a file to be uploaded...");
		browser.pause(10000);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();		
	}
	
}