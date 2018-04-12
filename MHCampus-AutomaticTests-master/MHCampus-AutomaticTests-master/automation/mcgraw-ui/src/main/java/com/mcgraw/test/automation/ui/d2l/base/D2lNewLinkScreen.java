package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public abstract class D2lNewLinkScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_p"))
	protected CheckBox visibilityCheckbox;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_r"))
	protected CheckBox signMessageWithKeyCheckbox;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_u"))
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

	//@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@title = 'Add custom parameters']"))
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='d2l_form']/div/a[1]/img"))
	protected Element addCustomParameters;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name='name_new_0']"))
	protected Input nameCustomParameterInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'value_new_0']"))
	protected Input valueCustomParameterInput;
	

	public D2lNewLinkScreen(Browser browser) {
		super(browser);
	}

	final public void setAllowUsersToSeeLinkCheckbox(Boolean state) {
		if(visibilityCheckbox.waitForPresence(30))
			visibilityCheckbox.setChecked(state);
	}

	final public void setSendToolToProvider(Boolean state) {
		if(sendToolToProvider.waitForPresence(30))
			sendToolToProvider.setChecked(state);
	}

	final public void setSendContextToProvider(Boolean state) {
		if(sendContextToProvider.waitForPresence(30))
			sendContextToProvider.setChecked(state);
	}

	final public void setSendUserIdToProvider(Boolean state) {
		if(sendUserIdToProvider.waitForPresence(30))
			sendUserIdToProvider.setChecked(state);
	}

	final public void setSendUserNameToProvider(Boolean state) {
		if(sendUserNameToProvider.waitForPresence(30))
			sendUserNameToProvider.setChecked(state);
	}

	final public void setSendUserEmailToProvider(Boolean state) {
		if(sendUserEmailToProvider.waitForPresence(30))
			sendUserEmailToProvider.setChecked(state);
	}

	final public void setSendLinkDescriptionToProvider(Boolean state) {
		if(sendLinkDescriptionToProvider.waitForPresence(30))
			sendLinkDescriptionToProvider.setChecked(state);
	}

	final public void setSendLinkTitleToProvider(Boolean state) {
		if(sendLinkTitleToProvider.waitForPresence(30))
			sendLinkTitleToProvider.setChecked(state);
	}

	final public void setSignMessageWithKey(Boolean state) {
		if(signMessageWithKeyCheckbox.waitForPresence(30))
			signMessageWithKeyCheckbox.setChecked(state);
	}

	final public void clickAddCustomParameters() {
		
			if(addCustomParameters.waitForPresence(30))
				try{
					Logger.info("Pause for 3000 milis");
					browser.pause(3000);
					addCustomParameters.click();
					browser.pause(3000);
				}
				catch (Exception e){
					Logger.info("Faile to click on the add button");
				}
	}

	final public void typeCustomParameters(String name, String value) {
		clickAddCustomParameters();
		if(nameCustomParameterInput.waitForPresence(30))
			nameCustomParameterInput.typeValue(name);
		if(valueCustomParameterInput.waitForPresence(30))
			valueCustomParameterInput.typeValue(value);
	}
	
	public void scrollDownPage() {
		//browser.executeScript("window.scrollBy(0,250)", "");
		browser.executeScript("arguments[0].scrollIntoView(true);",linkKeyRadioBtn);
	}

	public abstract D2lManageExternalToolsScreen clickSaveBtn();
	
	public abstract void typeTitle(String title);

	public abstract void typeUrl(String url);
	
	public abstract void chooseLinkKeyRadioBtn();

	public abstract void typeCustomerKey(String key);

	public abstract void typeSharedSecret(String secret);

}
