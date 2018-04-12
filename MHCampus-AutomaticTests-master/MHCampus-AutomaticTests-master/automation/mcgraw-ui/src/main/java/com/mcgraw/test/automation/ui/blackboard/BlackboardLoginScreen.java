package com.mcgraw.test.automation.ui.blackboard;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Preview as Guest')]")))
public class BlackboardLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "user_id"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@name = 'login'][@value = 'Login']"))
	Element loginButton;

	public BlackboardLoginScreen(Browser browser) {
		super(browser);
	}

	public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.typeValue(password);
	}

	public BlackBoardConfigSecretKeysScreen clickLoginForSetup() {
		loginButton.click();
		return browser.waitForPage(BlackBoardConfigSecretKeysScreen.class);
	}

	public BlackboardHomeScreen clickLogin() {
		loginButton.click();
		return browser.waitForPage(BlackboardHomeScreen.class);
	}

	public BlackboardHomeScreen loginToBlackBoard(String name, String password) {
		typeUsername(name);
		typePassword(password);
		return clickLogin();
	}

	public BlackBoardConfigSecretKeysScreen loginToBlackBoardForSetup(
			String name, String password) {
		typeUsername(name);
		typePassword(password);
		return clickLoginForSetup();
	}
}
