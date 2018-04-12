package com.mcgraw.test.automation.ui.d2l.v9;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Security Settings')]")))
public class D2lNewLinkScreenV9 extends D2lNewLinkScreen {
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_e"))
	protected Input titleInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_i"))
	protected Input urlInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_t"))
	protected Element linkKeyRadioBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_v"))
	protected Input keyInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_x"))
	protected Input secretInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[contains(text(),'Save')]"))
	Element saveBtn;

	public D2lNewLinkScreenV9(Browser browser) {
		super(browser);
	}

	@Override
	public void typeTitle(String title) {
		if(titleInput.waitForPresence(30))
			titleInput.typeValue(title);
	}

	@Override
	public void typeUrl(String url) {
		if(urlInput.waitForPresence(30))
			urlInput.typeValue(url);
	}
	
	@Override
	public void chooseLinkKeyRadioBtn() {
		if(linkKeyRadioBtn.waitForPresence(30))
			linkKeyRadioBtn.click();
	}

	@Override
	public void typeCustomerKey(String key) {
		if(keyInput.waitForPresence(30))
			keyInput.typeValue(key);
	}

	@Override
	public void typeSharedSecret(String secret) {
		if(secretInput.waitForPresence(30))
			secretInput.typeValue(secret);
	}
	
	public D2lManageExternalToolsScreen clickSaveBtn() {
		saveBtn.click();
		return browser.waitForPage(D2lManageExternalToolsScreenV9.class, 20);
	}
}