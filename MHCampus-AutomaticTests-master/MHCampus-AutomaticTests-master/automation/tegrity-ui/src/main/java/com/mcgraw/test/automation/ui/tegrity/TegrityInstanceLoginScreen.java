package com.mcgraw.test.automation.ui.tegrity;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Welcome to Tegrity')]")))
public class TegrityInstanceLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='TextFieldUserName']"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='TextFieldPassword']"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "button[id*='ButtonLogin']"))
	Input loginButton;
	
	//Add by Roman
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceDashboardForHelpDeskAdminScreen tegrityinstancedashboardforhelpdeskadminscreen;
	
	
	public TegrityInstanceLoginScreen(Browser browser) {
		super(browser);
	}

	public TegrityInstanceDashboardScreen loginToTegrityInstanceAsAdmin(String username, String password){
		browser.manage().deleteAllCookies();
		fillInstanceCredentials(username, password);
		browser.makeScreenshot();
		loginButton.click();
		return browser.waitForPage(TegrityInstanceDashboardScreen.class, 30);
	}
	//Edit by Roman
	public TegrityInstanceDashboardForHelpDeskAdminScreen loginToTegrityInstanceAsHelpDeskAdmin(String username, String password){
		browser.manage().deleteAllCookies();
		fillInstanceCredentials(username, password);
		browser.makeScreenshot();
		loginButton.click();
		try{
			//try to log in to Tegrity  
			this.tegrityinstancedashboardforhelpdeskadminscreen 
			= browser.waitForPage(TegrityInstanceDashboardForHelpDeskAdminScreen.class, 30);
			browser.makeScreenshot();
			return tegrityinstancedashboardforhelpdeskadminscreen;
		}catch(Exception e){
			//Page does not match log out and try one more time
			Logger.info("Log out and try to enter one more time");
			browser.waitForElement(By.xpath(".//*/a[contains(text(),'sign out')]"), 30).click();
//			tegrityinstancedashboardforhelpdeskadminscreen.logoutFromTegrity();
			browser.waitForPage(TegrityInstanceLoginScreen.class, 10);
			Logger.info("Delete All Cookies");
			browser.manage().deleteAllCookies();
			fillInstanceCredentials(username, password);
			loginButton.click();
			this.tegrityinstancedashboardforhelpdeskadminscreen 
			= browser.waitForPage(TegrityInstanceDashboardForHelpDeskAdminScreen.class, 30);
			browser.makeScreenshot();
			return this.tegrityinstancedashboardforhelpdeskadminscreen;
		}
	}
	//Edit by Roman
	public TegrityIntroductionScreen loginToCampusAsUser(String name, String password) {
		browser.manage().deleteAllCookies();
		Logger.info("Delete All Cookies");
		fillInstanceCredentials(name, password);
		loginButton.click();
		//AlexandrY fix for unexpected popup error
		if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"), 5)){
			browser.navigate().refresh();
			browser.pause(3000);
		}
		try{
			//try to log in to Tegrity
			this.tegrityIntroductionScreen = browser.waitForPage(TegrityIntroductionScreen.class, 10);
			browser.makeScreenshot();
			return this.tegrityIntroductionScreen;
		}catch(Exception e){
			//Page does not match log out and try one more time
			Logger.info("Log out and try to enter one more time");
			tegrityIntroductionScreen.logOut();
			browser.waitForPage(TegrityInstanceLoginScreen.class, 10);
			Logger.info("Delete All Cookies");
			browser.manage().deleteAllCookies();
			fillInstanceCredentials(name, password);
			loginButton.click();
			this.tegrityIntroductionScreen 
			= browser.waitForPage(TegrityIntroductionScreen.class, 10);
			browser.makeScreenshot();
			return this.tegrityIntroductionScreen;
		}
	}

	private void fillInstanceCredentials(String username, String password) {		
		usernameInput.clearAndTypeValue(username);
		passwordInput.clearAndTypeValue(password);
	}
}