package com.mcgraw.test.automation.ui.lti;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Launch URL')]")))
public class LtiScreen extends Screen {
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='id_registation']/p/span[1]/label/input"))
	Input launchUrl;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_key"))
	Input customerKey;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_secret"))
	Input sharedSecret;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='id_launchData']/div[6]/p/span[1]/label/input"))
	Input userId;                                              

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_roles"))
	Input roles;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='id_launchData']/div[6]/p/span[4]/label/input"))
	Input fullName;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='id_launchData']/div[5]/p[1]/span[1]/label/input"))
	Input courseId;                                            

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='id_launchData']/div[5]/p[1]/span[3]/label/input"))
	Input courseTitle;                                         
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_custom"))
	Element customParameters;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "optional_top"))
	Element optionalParameters;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "clear_top"))
	Element clearDataButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "save_top"))
	Element saveDataButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "launchw_bottom"))
	Element launchTP;
	
	public Element getLaunchTP() {
		return launchTP;
	}

	public LtiScreen(Browser browser) {
		super(browser);
	}

	public void typeLaunchUrl(String launchUrlvalue) {
		launchUrl.clearAndTypeValue(launchUrlvalue);
	}

	public void typeCustomerKey(String key) {
		customerKey.clearAndTypeValue(key);
	}

	public void typeSharedSecret(String sharedSecretValue) {
		sharedSecret.clearAndTypeValue(sharedSecretValue);
	}

	public void typeUserId(String userIdValue) {
		userId.clearAndTypeValue(userIdValue);
	}

	public void typeUserFullName(String fullNameValue) {
		fullName.clearAndTypeValue(fullNameValue);
	}

	public void typeCourseId(String courseIdValue) {
		courseId.clearAndTypeValue(courseIdValue);
	}

	public void typeCourseTitle(String courseTitleValue) {
		courseTitle.clearAndTypeValue(courseTitleValue);
	}

	public void typeRoleOfUser(String role) {
		roles.clearAndTypeValue(role);
	}
	
	public void clickClearData() {
		Logger.info("Clicking 'Clear data' button...");
		clearDataButton.click();
	}

	public void clickSaveData() {
		Logger.info("Clicking 'Save data' button...");
		saveDataButton.click();
		browser.pause(4000);
	}
	
	public void clickLaunchTP() {
		Logger.info("Clicking 'Launch TP' button...");
		launchTP.click();
		browser.pause(6000);
		browser.switchToLastWindow();
	}
	
	public void displayOptionalParameters() {
		if(!optionalParameters.isSelected()){
			Logger.info("Clicking 'Display optional parameters?...'");
			optionalParameters.click();
		}
	}

	public void typeCustomParameters(String param) {
		Logger.info("Typing 'Custom Parameters'...");
		customParameters.clear();
		customParameters.sendKeys(param);
	}
}