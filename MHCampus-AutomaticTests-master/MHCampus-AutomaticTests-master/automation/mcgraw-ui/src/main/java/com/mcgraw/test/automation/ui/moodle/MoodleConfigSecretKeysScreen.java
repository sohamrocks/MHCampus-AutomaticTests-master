package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Blocks: McGraw-Hill AAIRS')]")))
public class MoodleConfigSecretKeysScreen extends Screen {
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_s__block_mhaairs_customer_number"))
	Input customerNumberInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_s__block_mhaairs_shared_secret"))
	Input sharedSecretInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "id_s__block_mhaairs_display_services_MHCampus"))
	Element availableServicesCheckbox;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@value = 'Save changes']"))
	Element saveBtn;

	public MoodleConfigSecretKeysScreen(Browser browser) {
		super(browser);
	}

	public void typeCustomerNumber(String customerNumber) {
		browser.waitForElementPresent(customerNumberInput);
		customerNumberInput.clearAndTypeValue(customerNumber);
	}

	public void typeSharedSecret(String sharedSecret) {
		browser.waitForElementPresent(sharedSecretInput);
		sharedSecretInput.clearAndTypeValue(sharedSecret);
	}

	public void tickAvailableServices() {
		Logger.info("Check Available Services...");
		if (!availableServicesCheckbox.isSelected()) {
			availableServicesCheckbox.click();
			saveChanges();
		}
	}

	public void saveChanges() {
		Logger.info("Click 'Save changes' button...");
		saveBtn.submit();
		browser.pause(6000);
	}

	public void submitKeys(String customerNumber, String sharedSecret) {
		typeCustomerNumber(customerNumber);
		typeSharedSecret(sharedSecret);
		browser.makeScreenshot();
		saveChanges();
	}
}
