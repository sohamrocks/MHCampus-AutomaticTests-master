package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "ContentPlaceHolder1_iFrameServiceAdmin") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_AcceptTOS1_LinkButtonAccept")))
public class MhCampusInstanceTermsOfUseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_AcceptTOS1_LinkButtonAccept"))
	Element acceptLink;

	@DefinedLocators(@DefinedLocator(using = "//legend[contains(text(),' Terms Of Use ')]"))
	Element termOfUseTextElement;

	public MhCampusInstanceTermsOfUseScreen(Browser browser) {
		super(browser);
	}

	public MhCampusInstanceDashboardScreen acceptTheRules() {
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
		Logger.info("Accepting the rules...");
		Element agreeCheckbox = browser.waitForElement(
				By.id("ServiceAdminMain1_AcceptTOS1_CheckBoxAgreed"), 5);
		agreeCheckbox.click();
		acceptLink.click();
		browser.switchTo().defaultContent();
		return browser.waitForPage(MhCampusInstanceDashboardScreen.class);
	}

}
