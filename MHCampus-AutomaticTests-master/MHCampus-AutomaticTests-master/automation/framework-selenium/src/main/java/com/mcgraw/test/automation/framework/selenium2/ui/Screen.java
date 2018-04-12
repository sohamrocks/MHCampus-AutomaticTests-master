package com.mcgraw.test.automation.framework.selenium2.ui;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


/**
 * PageObject pattern representation
 *
 * @author Andrei Varabyeu
 *
 */

// 
public abstract class Screen {
	protected Browser browser;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'Logout')]"))
	Element logoutLink;

	public Screen(Browser browser) {
		this.browser = browser;
		// TODO: Add check that page opened
	}
	
	public void refresh(){
		browser.navigate().refresh();
	}
	
	public void clickLogout()
	{
		Logger.info("Logging out...");
		logoutLink.click();
	}

	// TODO: Refactor test to implement this method
	// abstract protected boolean isPageOpened(Browser browser);


	
}