package com.mcgraw.test.automation.ui.angel;

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

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='btnLogon']")))
public class AngelLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "username"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//form[@id = 'frmLogon']//input[@class = 'button'][@value = ' Log On ']"))
	Element logonBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[@class = 'default'][contains(text(),'Agree')]"))
	Element agreeBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[@type = 'button'][contains(text(),'Decline')]"))
	Element declineBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'Logoff')]"))
	Element logoff;

	public AngelLoginScreen(Browser browser) {
		super(browser);
	}

	public AngelHomeScreen loginToAngel(String username, String password) {
		
		try{
			browser.switchTo().frame("contentWin");
			typeUsername(username);
			typePassword(password);
			browser.makeScreenshot();
			clickLogon();
		}catch (Exception e){
			browser.switchTo().defaultContent();
			try{
				clickLogoff();
			}catch(Exception ex){
				Logger.info("Can't logoff frpom Angel...");
			}
			Logger.info("Trying Login into Angel again...");
			browser.switchTo().frame("contentWin");
			typeUsername(username);
			typePassword(password);
			browser.makeScreenshot();
			clickLogon();
		}
		browser.switchTo().defaultContent();
		AngelHomeScreen angelHomeScreen = browser.waitForPage(AngelHomeScreen.class);
		if (agreeBtn.waitForPresence(10)) {
			Logger.info("Clicking agree to save coockies...");
			agreeBtn.click();
			browser.pause(4000);
		}
		return angelHomeScreen;
	}

	private void typeUsername(String username) {
		usernameInput.waitForPresence(5);
		usernameInput.clearAndTypeValue(username);
	}

	private void typePassword(String password) {
		passwordInput.waitForPresence(5);
		passwordInput.clearAndTypeValue(password);
	}

	private void clickLogon() {
		Logger.info("Clicking Login button...");
		logonBtn.waitForPresence(5);
		logonBtn.click();
	}
	
	private void clickLogoff() {
		Logger.info("Clicking Logoff button...");
		logoff.waitForPresence(5);
		logoff.click();
		browser.makeScreenshot();
		browser.clickOkInAlertIfPresent();
	}
}
