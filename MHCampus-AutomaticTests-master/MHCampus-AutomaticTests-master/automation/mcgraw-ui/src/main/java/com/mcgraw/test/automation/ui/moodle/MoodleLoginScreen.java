package com.mcgraw.test.automation.ui.moodle;

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
//@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*/a[contains(text(),'Moodle')]")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*/a[contains(text(),'moodle')]")))  
public class MoodleLoginScreen extends Screen {
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "username"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "loginbtn"))
	Element loginButton;

	public MoodleLoginScreen(Browser browser) {
		super(browser);
	}

	public void typeUsername(String username) {
		usernameInput.clearAndTypeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.clearAndTypeValue(password);
	}

	public MoodleConfigSecretKeysScreen clickLoginForSetup() {
		Logger.info("Login into Moodle for Setup...");
		loginButton.click();
		browser.pause(6000);
		return browser.waitForPage(MoodleConfigSecretKeysScreen.class);
	}

	public MoodleHomeScreen clickLogin() {
		Logger.info("Login into Moodle...");
		loginButton.click();
		browser.pause(6000);
		return browser.waitForPage(MoodleHomeScreen.class);
	}

	public MoodleHomeScreen loginToMoodle(String username, String password) {
		typeUsername(username);
		typePassword(password);
		return clickLogin();
	}
	
	public MoodleConfigSecretKeysScreen loginToMoodleForSetup(String username,
			String password) {
		typeUsername(username);
		typePassword(password);
		return clickLoginForSetup();
	}
}
