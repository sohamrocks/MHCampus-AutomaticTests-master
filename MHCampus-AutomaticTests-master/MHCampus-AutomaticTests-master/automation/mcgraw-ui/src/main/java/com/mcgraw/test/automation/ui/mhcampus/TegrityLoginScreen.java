package com.mcgraw.test.automation.ui.mhcampus;

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

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='RecordBtn'][@value='Welcome to Tegrity']")))
public class TegrityLoginScreen extends Screen {
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxInstitution"))
	Input currentInstitution;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxUsername"))
	Input currentUsername;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxPassword"))
	Input currentPassword;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ButtonLogin"))
	Element loginButton;

	public TegrityLoginScreen(Browser browser) {
		super(browser);
	}

	public void fillInstitution(String institution) {
		currentInstitution.clearAndTypeValue(institution);
	}

	public void fillUsername(String username) {
		currentUsername.clearAndTypeValue(username);
	}

	public void fillPassword(String password) {
		currentPassword.clearAndTypeValue(password);
	}

	public TegrityAccountsScreen clickloginToTegrity() {
		loginButton.click();
		return browser.waitForPage(TegrityAccountsScreen.class);
	}

	public TegrityAccountsScreen loginToTegrityClientServices(
			String institution, String name, String password){
		Logger.info("Logging to Tegrity Service. Institution: " + institution
				+ ", name: " + name + ", password:" + password);
		fillInstitution(institution);
		fillUsername(name);
		fillPassword(password);
		return clickloginToTegrity();
	}
}