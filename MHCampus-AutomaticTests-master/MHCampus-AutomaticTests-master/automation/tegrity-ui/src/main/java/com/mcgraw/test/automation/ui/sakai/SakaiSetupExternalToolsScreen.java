package com.mcgraw.test.automation.ui.sakai;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//input[@value = 'Update Options']")))
public class SakaiSetupExternalToolsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//input[@id = 'imsti.launch']"))
	Input urlInput;

	@DefinedLocators(@DefinedLocator(using = "//input[@id='imsti.pagetitle']"))
	Input titleInput;
	
	@DefinedLocators(@DefinedLocator(using = "//input[@id = 'imsti.key']"))
	Input customKeyInput;
	
	@DefinedLocators(@DefinedLocator(using = "//input[@id = 'imsti.secret']"))
	Input secretInput;
	
	@DefinedLocators(@DefinedLocator(using = "//input[@id = 'imsti.releasename']"))
	Input sendNameInput;
	
	@DefinedLocators(@DefinedLocator(using = "//input[@id = 'imsti.releaseemail']"))
	Input sendEmailInput;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "imsti.custom"))
	Input customParameters;

	@DefinedLocators(@DefinedLocator(using = "//input[@value = 'Update Options']"))
	Element updateBtn;

	@DefinedLocators(@DefinedLocator(using = "//input[@value = 'Exit Setup']"))
	Element exitSetupBtn;

	public SakaiSetupExternalToolsScreen(Browser browser) {
		super(browser);
	}

	public void typeParameters(String url, String title, String customKey, 
			String secret, String customLaunchParameters) {
		
		urlInput.clearAndTypeValue(url);
		titleInput.clearAndTypeValue(title);
		customKeyInput.clearAndTypeValue(customKey);
		secretInput.clearAndTypeValue(secret);
		
		sendNameInput.click();
		sendEmailInput.click();
		
		customParameters.clearAndTypeValue(customLaunchParameters);
	}

	public void clickUpdateBtn() {
		Logger.info("Clicking 'Update' Button...");
		updateBtn.click();
		browser.makeScreenshot();
	}
}
