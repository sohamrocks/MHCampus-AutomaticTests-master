package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public abstract class D2lLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "userName"))
	protected Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	protected Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'Log In')]"))
	protected Element loginButton;

	public D2lLoginScreen(Browser browser) {
		super(browser);
	}

	final public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	final public void typePassword(String password) {
		passwordInput.typeValue(password);
	}

	final public D2lHomeScreen loginToD2l(String name, String password) {
		typeUsername(name);
		typePassword(password);
		browser.makeScreenshot();
		loginButton.click();
		return waitForD2lHomeScreen();
	}

	protected abstract D2lHomeScreen waitForD2lHomeScreen();
}
