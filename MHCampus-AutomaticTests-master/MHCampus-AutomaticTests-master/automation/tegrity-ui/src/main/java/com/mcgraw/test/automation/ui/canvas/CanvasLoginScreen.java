package com.mcgraw.test.automation.ui.canvas;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Forgot Password?')]")))
public class CanvasLoginScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "pseudonym_session_unique_id"))
	Input usernameInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "pseudonym_session_password"))
	Input passwordInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[contains(text(),'Log In')]"))
	Element loginButton;

	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='modal-box']/div/label/input"))
	Input iAgreeInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='modal-box']/div/div/button"))
	Element submitButton;

	public CanvasLoginScreen(Browser browser) {
		super(browser);
	}

	public void typeUsername(String username) {
		usernameInput.typeValue(username);
	}

	public void typePassword(String password) {
		passwordInput.typeValue(password);
	}
	
	public void clickLogin() {
		browser.pause(2000);
		loginButton.click();
	}
	
	public CanvasHomeScreen acceptTermsOfUse() {
		iAgreeInput.waitForPresence(10);
		iAgreeInput.click();
		browser.makeScreenshot();
		submitButton.waitForPresence(10);
		submitButton.click();
		return browser.waitForPage(CanvasHomeScreen.class);
	}

	public CanvasHomeScreen loginToCanvasAndAcceptTerms(String name, String password) {
		browser.pause(2000);
		typeUsername(name);
		typePassword(password);
		browser.makeScreenshot();
		clickLogin();
		return acceptTermsOfUse();
	}
	
	public CanvasHomeScreen loginToCanvas(String name, String password) {
		browser.pause(2000);
		typeUsername(name);
		typePassword(password);
		browser.makeScreenshot();
		clickLogin();
		return browser.waitForPage(CanvasHomeScreen.class);
	}
}
