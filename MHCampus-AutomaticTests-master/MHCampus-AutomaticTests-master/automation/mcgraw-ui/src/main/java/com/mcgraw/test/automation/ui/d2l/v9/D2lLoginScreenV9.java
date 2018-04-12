package com.mcgraw.test.automation.ui.d2l.v9;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Welcome')]")))
public class D2lLoginScreenV9 extends D2lLoginScreen {
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Username"))
	protected Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Password"))
	protected Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'Login']"))
	protected Element loginButton;

	public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.typeValue(password);
	}

	public D2lLoginScreenV9(Browser browser) {
		super(browser);
	}
	
	@Override
	public D2lHomeScreen loginToD2l(String name, String password) {
		typeUsername(name);
		typePassword(password);
		loginButton.click();
		return waitForD2lHomeScreen();
	}

	@Override
	protected D2lHomeScreen waitForD2lHomeScreen() {
		return browser.waitForPage(D2lHomeScreenV9.class);
	}

}
