package com.mcgraw.test.automation.ui.blackboard;

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

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='pageTitleText']//*[contains(text(),'Configure McGraw-Hill')]")))
public class BlackBoardConfigSecretKeysScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.NAME, using = "TEG_CUSTOMER_ID"))
	Input customerNumberInput;

	@DefinedLocators(@DefinedLocator(how = How.NAME, using = "SHARED_SECRET"))
	Input sharedSecretInput;

	@DefinedLocators(@DefinedLocator(how = How.NAME, using = "bottom_Submit"))
	Element submitButton;

	public BlackBoardConfigSecretKeysScreen(Browser browser) {
		super(browser);
	}

	public void typeCustomerNumber(String customerNumber) {
		customerNumberInput.clearAndTypeValue(customerNumber);
	}

	public void typeSharedSecret(String sharedSecret) {
		sharedSecretInput.clearAndTypeValue(sharedSecret);
	}

	public BlackBoardConfigSecretKeysResultScreen clickSubmit() {
		Logger.info("Click Sibmit button...");
		submitButton.click();
		return browser
				.waitForPage(BlackBoardConfigSecretKeysResultScreen.class);
	}

	public BlackBoardConfigSecretKeysResultScreen submitKeys(
			String customerNumber, String sharedSecret) {
		typeCustomerNumber(customerNumber);
		typeSharedSecret(sharedSecret);
		return clickSubmit();
	}
}
