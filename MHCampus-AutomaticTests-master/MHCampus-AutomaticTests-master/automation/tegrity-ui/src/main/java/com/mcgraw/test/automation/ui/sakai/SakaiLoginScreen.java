package com.mcgraw.test.automation.ui.sakai;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='loginForm']")))
public class SakaiLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "eid"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "pw"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "submit"))
	Element loginButton;

	@DefinedLocators(@DefinedLocator(using = "//*[@class = 'qtip-button']"))
	Element exitWindow;

	public SakaiLoginScreen(Browser browser) {
		super(browser);
	}

	public void typeUsername(String username) {
		usernameInput.clearAndTypeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.clearAndTypeValue(password);
	}

	public void clickLogin() {
		Logger.info("Clicking 'Login' button...");
		loginButton.click();
	}

	public SakaiHomeScreen loginToSakai(String username, String password) {
		typeUsername(username);
		typePassword(password);
		browser.makeScreenshot();
		clickLogin();
		SakaiHomeScreen sakaiHomeScreen = 
				browser.waitForPage(SakaiHomeScreen.class, 20);
		browser.makeScreenshot();
		return sakaiHomeScreen;
	}

	public SakaiAdminHomePage loginToSakaiAsAdmin(String username,
			String password) {
		typeUsername(username);
		typePassword(password);
		browser.makeScreenshot();
		clickLogin();
		SakaiAdminHomePage sakaiAdminHomePage =
				browser.waitForPage(SakaiAdminHomePage.class, 20);
		browser.makeScreenshot();
		return sakaiAdminHomePage;
	}
	public void closePopupWindowIfPresent() {
		if (exitWindow.waitForPresence(5)) {
			exitWindow.click();
		}
	}
}
