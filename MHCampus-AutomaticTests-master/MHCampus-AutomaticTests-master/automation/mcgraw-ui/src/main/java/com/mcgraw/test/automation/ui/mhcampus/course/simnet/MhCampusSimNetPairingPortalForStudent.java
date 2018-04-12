package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "simnetonline.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(@class,'logo-simnet')]")))
public class MhCampusSimNetPairingPortalForStudent extends Screen {

	private final String CLASS_NOT_CONFIGURED_MESSAGE = "The class you attempted to login to has not been configured by your instructor.";

	@DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'" + CLASS_NOT_CONFIGURED_MESSAGE + "')]"))
	Element classNotConfiguredMessage;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_LoginRadioButtonList_1"))
	RadioButton radioButtonNo;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewEmailTextBox"))
	Input emailInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewUsernameTextBox"))
	Input userNameInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewStudentIDTextBox"))
	Input studentIdInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewPasswordTextBox"))
	Input passwordInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewConfirmPasswordTextBox"))
	Input passwordConfirmInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_AgreementCheckBox"))
	CheckBox checkBoxToS;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_CreateUserButton"))
	Element createBtn;
	
	//@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//a[contains(text(),'Continue to SIMnet')]"))
	//Element countinueToSimNetBtn;
	
	//Edit by lior
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='ctl00_ContentPlaceHolder1_LoginButton']"))
	Element countinueToSimNetBtn;
	
	//@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//h2[contains(text(),'You have successfully registered and paired your SIMnet account.')]"))
	//Element successfullyRegisteredText;
	
	//Edit by lior
	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = ".//*[@id='registrationPanel']/div[2]/h2"))	
	Element successfullyRegisteredText;
	
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = ".//*[@id='content']/div/div/div[1]/div[2]/button"))
	Element EditUserInfoBtn;
	
	
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = ".//*[@id='assignments/13832/tiles']/span"))
	Element assigmentsLink;
	
	
	
	
	
	
	public MhCampusSimNetPairingPortalForStudent(Browser browser) {
		super(browser);
	}

	public boolean isClassNotConfiguredMessagePresent() {
		Logger.info("Check message present: " + CLASS_NOT_CONFIGURED_MESSAGE);
		boolean condition = classNotConfiguredMessage.waitForPresence(30);
		browser.makeScreenshot();
		return condition;
	}
	
	
	public void pairStudent(String email,String password){
		Logger.info("Pair Student  ");
		browser.switchTo().frame("tool_content");
		browser.waitForElementPresent(radioButtonNo).jsClick(browser);
		browser.waitForElementPresent(emailInput).sendKeys(email);
		browser.waitForElementPresent(userNameInput).sendKeys(email);
		browser.waitForElementPresent(studentIdInput).sendKeys(email);
		browser.waitForElementPresent(passwordInput).sendKeys(password);
		browser.waitForElementPresent(passwordConfirmInput).sendKeys(password);
		browser.waitForElementPresent(checkBoxToS).click();
		browser.makeScreenshot();
		browser.pause(4000);
		browser.waitForElementPresent(createBtn).click();
		browser.switchTo().defaultContent(); //Edit by lior
	}
	
	
	
	public boolean successfullyRegisteredTextIsPresent() {
		Logger.info("Checking if SIMnet successfully registered text is present");
		browser.switchTo().frame("tool_content");  //Edit by lior
		browser.pause(5000);
		boolean elementPresent = successfullyRegisteredText.isElementPresent();
		browser.switchTo().defaultContent();
		return elementPresent;
	}
	

	public boolean EditUserInfoBtnIsPresent() {
		Logger.info("Checking if edit User info button is present");
		browser.switchTo().frame("tool_content");
		browser.pause(8000);
		boolean elementPresent = EditUserInfoBtn.isElementPresent();
		browser.switchTo().defaultContent(); 
		return elementPresent;
		
	}
	
	
	
	public void clickOnAssigmentsLink(){
		
		Logger.info("clicking on assigments link");
		browser.switchTo().frame("tool_content");
		browser.pause(5000);
		browser.waitForElementPresent(assigmentsLink).click();
		browser.switchTo().defaultContent();  
	}
	
	public MhCampusSimNetGetStudentCourtesyAccess pressContinueToSIMnet(){
		browser.switchTo().frame("tool_content");  //Edit by lior
		browser.waitForElementPresent(countinueToSimNetBtn).click();
		browser.switchTo().defaultContent();  //Edit by lior
		browser.pause(10000);
		browser.switchTo().frame("tool_content");  //Edit by lior
		return browser.waitForPage(MhCampusSimNetGetStudentCourtesyAccess.class);
	}
	
	

}
