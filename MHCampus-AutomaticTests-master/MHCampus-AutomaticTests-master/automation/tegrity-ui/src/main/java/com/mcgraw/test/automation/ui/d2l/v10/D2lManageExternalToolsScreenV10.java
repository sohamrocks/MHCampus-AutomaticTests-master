package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Manage External Learning Tool Links')]")))
public class D2lManageExternalToolsScreenV10 extends
		D2lManageExternalToolsScreen {

	public D2lManageExternalToolsScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lNewLinkScreen openD2lNewLinkPage() {
		return browser.waitForPage(D2lNewLinkScreenV10.class, 20);
	}
}
