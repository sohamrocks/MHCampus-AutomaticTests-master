package com.mcgraw.test.automation.ui.blackboard;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Action Successful')]")))
public class BlackBoardConfigSecretKeysResultScreen extends Screen {

	public BlackBoardConfigSecretKeysResultScreen(Browser browser) {
		super(browser);
	}
}
