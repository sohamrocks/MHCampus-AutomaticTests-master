package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public class TegrityCreateMhCampusCustomerScreenBase extends Screen {
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'Logout')]", priority = 1))
	private Element logoutLink;

	public TegrityCreateMhCampusCustomerScreenBase(Browser browser) {
		super(browser);
	}

	public void clickLogout() {
		logoutLink.click();
	}

}
