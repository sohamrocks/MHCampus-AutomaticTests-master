package com.mcgraw.test.automation.ui.connect;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'homeLogo large-3 columns']/img")))
public class ConnectLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "userName"))
	Element email;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	Element password;

	@DefinedLocators(@DefinedLocator(using = "//*[@id='signinForm']/fieldset/div[4]/div[2]/input"))
	Element loginButton;
	
	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'OK')]"))
	Element okButton;

	public ConnectLoginScreen(Browser browser) {
		super(browser);
	}

	public ConnectWelcomeScreen loginIntoConnect(String userEmail, String userPassword) {
		Logger.info("login into Connect");
		email.clear();
		email.sendKeys(userEmail);
		password.clear();
		password.sendKeys(userPassword);
		loginButton.click();
		browser.pause(6000);
		browser.makeScreenshot();
		try{
			okButton.waitForPresence(10);
			okButton.click();
		}catch(Exception e){
			Logger.info("Ok button doesn't exist");
		}
		ConnectWelcomeScreen connectWelcomeScreen = 
				browser.waitForPage(ConnectWelcomeScreen.class);
		browser.makeScreenshot();
		
		return connectWelcomeScreen;
	}
}