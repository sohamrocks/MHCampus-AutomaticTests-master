package com.mcgraw.test.automation.ui.d2l.v10;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Edit Link')]")))
public class D2lEditWidgetScreenV10 extends D2lEditWidgetScreen {

	public D2lEditWidgetScreenV10(Browser browser) {
		super(browser);
	}

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_w"))
	protected Input keyInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_y"))
	protected Input secretInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_a"))
	protected Element saveAndCloseBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//img[@alt='Add custom parameters']"))
	protected Element addCustomParemeterBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//table[@id='z_bc']//tr[contains(@class,'d_lastRow')]//input[contains(@name,'name')]"))
	protected Input customParemeterNameInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//table[@id='z_bc']//tr[contains(@class,'d_lastRow')]//input[contains(@name,'value')]"))
	protected Input customParemeterValueInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[contains(@id,'currentOuDiv')]/input[@type='checkbox']"))
	protected CheckBox сurrentOrgUnitHEBrightspace;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Add Org Units']"))
	protected Element addOrgUnitsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//table[@class='d_dgsearch']//input[@type='text']"))
	protected Input addOrgUnitsSearchCourseInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_j"))
	protected Element addOrgUnitsPerformSearchCourseBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//table[@id='z_g']//tr[@class=' d_lastRow']"))
	protected Element firstFoundedCourse;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Insert'][@class='vui-button d2l-button vui-button-primary']"))
	protected Element addOrgUnitsInsertBtn;

//	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//td[@class='dcs_c dcs_cf']"))
//	protected Element courseOffering;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[@class='vui-button d2l-button vui-button-primary']"))
	protected Element addCustomParameterSaveChangesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//a[@id='z_b']"))
	protected Element saveBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_p"))
	protected CheckBox visibilityCheckbox;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_r"))
	protected CheckBox signMessageWithKeyCheckbox;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//fieldset//input[@name='signOption' and @type='radio'][2]"))
	protected Element linkKeyRadioBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendTCInfo']"))
	protected CheckBox sendToolToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendContextInfo']"))
	protected CheckBox sendContextToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendUserId']"))
	protected CheckBox sendUserIdToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendUserName']"))
	protected CheckBox sendUserNameToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendUserEmail']"))
	protected CheckBox sendUserEmailToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendLinkTitle']"))
	protected CheckBox sendLinkTitleToProvider;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendLinkDescription']"))
	protected CheckBox sendLinkDescriptionToProvider;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'sendCourseInfo']"))
	protected CheckBox sendLinkCourseInformationToProvider;

	//@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@title = 'Add custom parameters']"))
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='d2l_form']/div/a[1]/img"))
	protected Element addCustomParameters;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name='name_new_0']"))
	protected Input nameCustomParameterInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'value_new_0']"))
	protected Input valueCustomParameterInput;
		
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//div[@id='d_content']//a[text()='Save']"))
	protected Element saveButton;	
	
	public void setSendLinkCourseInformationToProvider(Boolean state) {
		browser.waitForElementPresent(sendLinkCourseInformationToProvider);		
		sendLinkCourseInformationToProvider.setChecked(state);
	}
	
	@Override
	public void setAllowUsersToSeeLinkCheckbox(Boolean state) {
		browser.waitForElementPresent(visibilityCheckbox);		
		visibilityCheckbox.setChecked(state);
	}

	@Override
	public void setSendToolToProvider(Boolean state) {
		browser.waitForElementPresent(sendToolToProvider);		
		sendToolToProvider.setChecked(state);
	}

	@Override
	public void setSendContextToProvider(Boolean state) {
		browser.waitForElementPresent(sendContextToProvider);		
		sendContextToProvider.setChecked(state);
	}

	@Override
	public void setSendUserIdToProvider(Boolean state) {
		browser.waitForElementPresent(sendUserIdToProvider);		
		sendUserIdToProvider.setChecked(state);
	}

	@Override
	public void setSendUserNameToProvider(Boolean state) {
		browser.waitForElementPresent(sendUserNameToProvider);		
		sendUserNameToProvider.setChecked(state);
	}

	@Override
	public void setSendUserEmailToProvider(Boolean state) {
		browser.waitForElementPresent(sendUserEmailToProvider);		
		sendUserEmailToProvider.setChecked(state);
	}

	@Override
	public void setSendLinkDescriptionToProvider(Boolean state) {
		browser.waitForElementPresent(sendLinkDescriptionToProvider);		
		sendLinkDescriptionToProvider.setChecked(state);
	}

	@Override
	public void setSendLinkTitleToProvider(Boolean state) {
		browser.waitForElementPresent(sendLinkTitleToProvider);		
		sendLinkTitleToProvider.setChecked(state);
	}

	@Override
	public void setSignMessageWithKey(Boolean state) {
		browser.waitForElementPresent(signMessageWithKeyCheckbox);		
		signMessageWithKeyCheckbox.setChecked(state);
	}
	
	@Override
	public void typeAndSearchAddOrgUnitsCourseValue(String value) {
		browser.waitForElementPresent(addOrgUnitsSearchCourseInput);
		addOrgUnitsSearchCourseInput.clearAndTypeValue(value);
		addOrgUnitsSearchCourseInput.sendKeys(Keys.ENTER);
	}

	@Override
	public void selectFirstFoundedCourse() {
		browser.waitForElementPresent(firstFoundedCourse);
		firstFoundedCourse.findElement(By.xpath(".//input")).click();
	}

	@Override
	public void setCurrentOrgUnitHEBrightspace(Boolean state) {
		browser.waitForElementPresent(сurrentOrgUnitHEBrightspace);
		сurrentOrgUnitHEBrightspace.setChecked(state);
	}

	@Override
	public void clickAddOrgUnitsBtn() {
		browser.waitForElementPresent(addOrgUnitsBtn);
		addOrgUnitsBtn.click();
		browser.pause(2000);
		browser.switchTo().frame(browser.waitForElement(By.xpath(".//iframe[@title='Add Org Units']")));
	}

	@Override
	public void clickAddOrgUnitsInsertBtn() {
		browser.switchTo().defaultContent();
		browser.waitForElementPresent(addOrgUnitsInsertBtn);
		addOrgUnitsInsertBtn.click();
	}

	@Override
	public void chooseLinkKeyRadioBtn() {
		browser.waitForElementPresent(linkKeyRadioBtn);
		linkKeyRadioBtn.click();
	}

	@Override
	public void typeCustomerKey(String key) {
		browser.waitForElementPresent(keyInput);
		keyInput.clearAndTypeValue(key);
	}

	@Override
	public void typeSharedSecret(String secret) {
		browser.waitForElementPresent(secretInput);
		secretInput.clearAndTypeValue(secret);
	}

	@Override
	public D2lManageExternalToolsScreen clickSaveAndCloseBtn() {
		browser.waitForElementPresent(saveAndCloseBtn);
		saveAndCloseBtn.click();
		return waitForManageExternalToolsScreen();
	}

	@Override
	public void clickAddCustomParameterBtn() {
		browser.waitForElementPresent(addCustomParemeterBtn);
		addCustomParemeterBtn.click();
	}

	@Override
	public void typeCustomParameterName(String name) {
		browser.waitForElementPresent(customParemeterNameInput);
		customParemeterNameInput.clearAndTypeValue(name);
	}
	
	@Override
	public void typeCustomParameterValue(String value) {
		browser.waitForElementPresent(customParemeterValueInput).sendKeys(value);
	}
	
	@Override
	public void checkCourseOfferingPresent(String courseName){
		browser.waitForElement(By.xpath(".//table//strong[contains(text(),'" + courseName + "')]"));
	}
	
	@Override
	protected D2lManageExternalToolsScreen waitForManageExternalToolsScreen() {
		return browser.waitForPage(D2lManageExternalToolsScreenV10.class, 20);
	}
	
	@Override
	public void clickSaveButton(){
		browser.waitForElementPresent(saveButton);
		saveButton.click();
	}
}
