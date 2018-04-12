package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.cli.MhCampusCliParams;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Welcome to MH Campus')]")))
public class MhCampusInstanceLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='TextBoxInstitution']"))
	Input institutionInput;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='TextBoxName']"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='TextBoxPassword']"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "input[id*='ButtonLogin']"))
	Input loginButton;

	public MhCampusInstanceLoginScreen(Browser browser) {
		super(browser);
	}

	public MhCampusInstanceTermsOfUseScreen loginToCampusFirstTime(String institution, String name, String password){
		fillInstanceCredentials(institution, name, password);
		return clickLoginFirstTime();
	}

	public MhCampusInstanceDashboardScreen loginToCampus(String institution, String name, String password) {
		fillInstanceCredentials(institution, name, password);
		return clickLogin();
	}

	public MhCampusIntroductionScreen loginToCampusAsUser(String institution, String name, String password) {
		fillInstanceCredentials(institution, name, password);
		return clickLoginAsUser();
	}

	private void fillInstanceCredentials(String institution, String name, String password) {
		if (MhCampusCliParams.getInstance().getInstanceUrl() != null) {
			institutionInput.clearAndTypeValue(institution);
			browser.waitForAlert(5);
			browser.clickOkInAlertIfPresent();
		}
		usernameInput.clearAndTypeValue(name);
		passwordInput.clearAndTypeValue(password);
		browser.makeScreenshot();
	}

	private MhCampusIntroductionScreen clickLoginAsUser() {
		Logger.info("Clicking Login button...");
		loginButton.click();
		MhCampusIntroductionScreen mhCampusIntroductionScreen = 
				browser.waitForPage(MhCampusIntroductionScreen.class, 20);
		browser.makeScreenshot();
		return mhCampusIntroductionScreen;
	}
	
	private MhCampusInstanceDashboardScreen clickLogin() {
		Logger.info("Clicking Login button...");
		loginButton.click();
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = 
				browser.waitForPage(MhCampusInstanceDashboardScreen.class, 10);
		browser.makeScreenshot();
		return mhCampusInstanceDashboardScreen;
	}

	private MhCampusInstanceTermsOfUseScreen clickLoginFirstTime() {
		Logger.info("Clicking Login button...");
		loginButton.click();
		MhCampusInstanceTermsOfUseScreen mhCampusInstanceTermsOfUseScreen = 
				browser.waitForPage(MhCampusInstanceTermsOfUseScreen.class);
		browser.makeScreenshot();
		return mhCampusInstanceTermsOfUseScreen;
	}
}
