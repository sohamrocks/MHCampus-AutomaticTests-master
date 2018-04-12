package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public abstract class D2lNewLinkScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_f"))
	protected Input titleInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_j"))
	protected Input urlInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_p"))
	protected CheckBox visibilityCheckbox;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_r"))
	protected CheckBox signMessageWithKeyCheckbox;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_u"))
	protected Element linkKeyRadioBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_w"))
	protected Input keyInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_y"))
	protected Input secretInput;
	
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

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@title = 'Add custom parameters']"))
	//@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(), 'Add custom parameters']"))
	protected Element addCustomParameters;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name='name_new_0']"))
	protected Input nameCustomParameterInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@name = 'value_new_0']"))
	protected Input valueCustomParameterInput;

	public D2lNewLinkScreen(Browser browser) {
		super(browser);
	}

	final public void typeTitle(String title) {
		titleInput.waitForPresence(30);
		titleInput.typeValue(title);
	}

	final public void typeUrl(String url) {
		urlInput.waitForPresence(30);
		urlInput.typeValue(url);
	}

	final public void setAllowUsersToSeeLinkCheckbox(Boolean state) {
		visibilityCheckbox.waitForPresence(30);
		visibilityCheckbox.setChecked(state);
	}

	final public void setSendToolToProvider(Boolean state) {
		sendToolToProvider.waitForPresence(30);
		sendToolToProvider.setChecked(state);
	}

	final public void setSendContextToProvider(Boolean state) {
		sendContextToProvider.waitForPresence(30);
		sendContextToProvider.setChecked(state);
	}

	final public void setSendUserIdToProvider(Boolean state) {
		sendUserIdToProvider.waitForPresence(30);
		sendUserIdToProvider.setChecked(state);
	}

	final public void setSendUserNameToProvider(Boolean state) {
		sendUserNameToProvider.waitForPresence(30);
		sendUserNameToProvider.setChecked(state);
	}

	final public void setSendUserEmailToProvider(Boolean state) {
		sendUserEmailToProvider.waitForPresence(30);
		sendUserEmailToProvider.setChecked(state);
	}

	final public void setSendLinkDescriptionToProvider(Boolean state) {
		sendLinkDescriptionToProvider.waitForPresence(30);
		sendLinkDescriptionToProvider.setChecked(state);
	}

	final public void setSendLinkTitleToProvider(Boolean state) {
		sendLinkTitleToProvider.waitForPresence(30);
		sendLinkTitleToProvider.setChecked(state);
	}

	final public void setSignMessageWithKey(Boolean state) {
		signMessageWithKeyCheckbox.waitForPresence(30);
		signMessageWithKeyCheckbox.setChecked(state);
	}

	final public void chooseLinkKeyRadioBtn() {
		linkKeyRadioBtn.waitForPresence(30);
		linkKeyRadioBtn.click();
	}

	final public void typeCustomerKey(String key) {
		keyInput.waitForPresence(30);
		keyInput.typeValue(key);
	}

	final public void typeSharedSecret(String secret) {
		secretInput.waitForPresence(30);
		secretInput.typeValue(secret);
	}

	public abstract D2lManageExternalToolsScreen clickSaveBtn();

	final public void clickAddCustomParameters() {
		addCustomParameters.waitForPresence(30);
		addCustomParameters.click();
	}

	final public void typeCustomParameters(String name, String value) {
		clickAddCustomParameters();
		nameCustomParameterInput.waitForPresence(30);
		nameCustomParameterInput.typeValue(name);
		valueCustomParameterInput.typeValue(value);
	}
}
