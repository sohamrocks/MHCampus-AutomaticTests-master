package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
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
public class D2lLoginScreenV10 extends D2lLoginScreen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "userName"))
	protected Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "password"))
	protected Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'Log In')]"))
	protected Element loginButton;
	
	public D2lLoginScreenV10(Browser browser) {
		super(browser);
	}
	
	public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.typeValue(password);
	}

	@Override
	public D2lHomeScreen loginToD2l(String name, String password) {
		typeUsername(name);
		typePassword(password);
		Logger.info("Click login button...");
		loginButton.click();
		return waitForD2lHomeScreen();
	}
	
	@Override
	protected D2lHomeScreen waitForD2lHomeScreen() {
		return browser.waitForPage(D2lHomeScreenV10.class, 20);
	}
}
