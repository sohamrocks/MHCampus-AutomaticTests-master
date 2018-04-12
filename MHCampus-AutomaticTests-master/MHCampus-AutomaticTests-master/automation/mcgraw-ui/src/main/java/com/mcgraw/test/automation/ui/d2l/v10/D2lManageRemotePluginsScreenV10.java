package com.mcgraw.test.automation.ui.d2l.v10;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

import com.mcgraw.test.automation.ui.d2l.base.D2lManageRemotePluginsScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//span[contains(text(),'Manage Remote Plugins')]")))

public class D2lManageRemotePluginsScreenV10 extends D2lManageRemotePluginsScreen {
	
	public D2lManageRemotePluginsScreenV10(Browser browser) {
		super(browser);
	}
	

}
