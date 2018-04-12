package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Select a course...')]")))
public class D2lHomeScreenV10 extends D2lHomeScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//header//div[contains(@class,'d2l-minibar-right')]//span[contains(@class,'d2l-menuflyout-arrow')][2]"))
	protected Element myAccountLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//header//a[contains(text(),'Log Out')]"))
	protected Element logOutLink;

	public D2lHomeScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsPage() {
		return browser.waitForPage(D2lCourseDetailsScreenV10.class, 20);
	}

	@Override
	public D2lLoginScreen waitForD2lLoginScreen() {
		return browser.waitForPage(D2lLoginScreenV10.class, 20);
	}

	//Added by AleksandrY
	@Override
	public void clickMyAccountLink() {
		Logger.info("Click 'My account' link");
		browser.waitForElementPresent(myAccountLink).click();
        browser.pause(1000);
	}

	//Added by AleksandrY
	@Override
	public void clickLogOutLink() {
		Logger.info("Click 'Logout' link");
		browser.waitForElementPresent(logOutLink).click();
		browser.pause(1000);
	}

}
