package com.mcgraw.test.automation.ui.rest;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'eLog Level For Remote Client Logging')]")))
public class TestRestApi extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxUrl"))
	Input apiUrl;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxCustomrId"))
	Input customerId;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextUserId"))
	Input userId;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextPwd"))
	Input password;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnTestAuthenticate"))
	Input TestAuthenticateBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnTestAutorize"))
	Input TestAuthorizeBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxInput"))
	Input encryptionInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnTestEncrypt"))
	Input TestEncryptionBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnTestDecrypt"))
	Input TestDecryptionBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxAppId"))
	Input applicationId;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TxtCustomerIdLog"))
	Input customerIdLog;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxAppVerssion"))
	Input applicationVersion;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxCategory"))
	Input category;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxMessage"))
	Input message;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxDateSentOnClientInTicks"))
	Input DateSentOnClientInTicks;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "eLogLevelForRemoteClientLogging"))
	Select eLogLevel;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnTestLog"))
	Input testLogBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "BtnCleanFields1"))
	Input cleanFieldsBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxResult"))
	Input result;
	
	public String getResult() {
		return result.getText();
	}	

	public void clickEncryptBtn() {
		TestEncryptionBtn.click();
	}
	
	public void clickAuthenticateBtn() {
		TestAuthenticateBtn.click();
	}
	
	public void clickAutorizeBtn() {
		TestAuthenticateBtn.click();
	}			

	public void clickDecryptBtn() {
		TestDecryptionBtn.click();
	}		
	
	public TestRestApi(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public void typeApiUrl(String str) {
		apiUrl.typeValue(str);
	}

	public void typeCustomerId(String str) {
		 customerId.typeValue(str);
	}

	public void typeUserId(String str) {
		 userId.typeValue(str);
	}

	public void typePassword(String str) {
		 password.typeValue(str);
	}

	public void typeTestAuthenticateBtn(String str) {
		 TestAuthenticateBtn.typeValue(str);
	}

	public void typeTestAuthorizeBtn(String str) {
		 TestAuthorizeBtn.typeValue(str);
	}

	public void typeEncryptionInput(String str) {
		 encryptionInput.typeValue(str);
	}

	public void typeApplicationId(String str) {
		 applicationId.typeValue(str);
	}

	public void typeCustomerIdLog(String str) {
		 customerIdLog.typeValue(str);
	}

	public void typeApplicationVersion(String str) {
		 applicationVersion.typeValue(str);
	}

	public void typeCategory(String str) {
		 category.typeValue(str);
	}

	public void typeMessage(String str) {
		 message.typeValue(str);
	}

	public void typeDateSentOnClientInTicks(String str) {
		 DateSentOnClientInTicks.typeValue(str);
	}

	public void typeeLogLevel(String option) {
		 eLogLevel.selectOptionByName(option);
	}

	public String getEncryptedId(String id) {
		typeUserId(id);
		return result.getText();
	}

}
