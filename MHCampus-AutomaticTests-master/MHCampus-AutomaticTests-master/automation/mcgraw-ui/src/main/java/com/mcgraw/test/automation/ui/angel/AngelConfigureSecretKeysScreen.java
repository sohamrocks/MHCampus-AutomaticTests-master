package com.mcgraw.test.automation.ui.angel;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'pageSubtitleSpan'][contains(text(),'MHCampus')]")))
public class AngelConfigureSecretKeysScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//textarea"))
	private Element textAreaElement;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnOK"))
	private Element okButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "linkContents"))
	private Element htmlEditor;

	private static String sharedSecretName = "sSharedSecret";
	private static String customerNumberName = "sCustomerNumber";
	private static String afterKeysSigns = " = '";

	public AngelConfigureSecretKeysScreen(Browser browser) {
		super(browser);
	}

	public void submitKeys(String sharedSecret, String customerNumber) {
		String file = textAreaElement.getAttribute("textContent");

		String startPartResult = file.substring(0, file.indexOf(sharedSecretName) + sharedSecretName.length() + afterKeysSigns.length());
		String middlePartResult = file.substring(file.indexOf(sharedSecretName) + sharedSecretName.length() + afterKeysSigns.length()
				+ sharedSecret.length(), file.indexOf(customerNumberName) + customerNumberName.length() + afterKeysSigns.length());
		String endPartResult = file.substring(file.indexOf(customerNumberName) + customerNumberName.length() + afterKeysSigns.length()
				+ customerNumber.length());
		String result = startPartResult + sharedSecret + middlePartResult + customerNumber + endPartResult;
		System.out.println(file == result);
		textAreaElement.clear();
		textAreaElement.sendKeys(result);
		browser.makeScreenshot();
		
		submitResult();
	}

	private void submitResult() {
		okButton.click();
	}

}
