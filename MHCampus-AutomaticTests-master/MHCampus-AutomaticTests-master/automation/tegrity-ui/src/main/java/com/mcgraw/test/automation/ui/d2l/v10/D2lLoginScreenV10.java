package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Welcome')]")))
public class D2lLoginScreenV10 extends D2lLoginScreen {

	public D2lLoginScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lHomeScreen waitForD2lHomeScreen() {
		return browser.waitForPage(D2lHomeScreenV10.class, 20);
	}
}
