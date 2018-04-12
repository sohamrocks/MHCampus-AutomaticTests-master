package com.mcgraw.test.automation.ui.ecollege;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//b[contains(text(),'Welcome')]")))
public class ECollegeLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Username"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Password"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'inputButton']"))
	Element loginButton;

	public ECollegeLoginScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.typeValue(password);
	}

	public ECollegeHomeScreen clickLogin() {
		loginButton.click();
		return browser.waitForPage(ECollegeHomeScreen.class, 20);
	}

	public ECollegeHomeScreen loginToEcollege(String name, String password) {
		typeUsername(name);
		typePassword(password);
		return clickLogin();
	}
}
