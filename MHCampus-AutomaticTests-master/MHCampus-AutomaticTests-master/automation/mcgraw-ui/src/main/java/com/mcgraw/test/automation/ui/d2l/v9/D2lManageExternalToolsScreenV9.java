package com.mcgraw.test.automation.ui.d2l.v9;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Manage External Learning Tool Links')]")))
public class D2lManageExternalToolsScreenV9 extends
		D2lManageExternalToolsScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Delete')]"))
	Element deleteBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[contains(text(),'Delete')]"))
	Element confirmDeleteBtn;

	public D2lManageExternalToolsScreenV9(Browser browser) {
		super(browser);
	}

	// Not used, check
	public D2lManageExternalToolsScreenV9 deleteLink(String linkTitle) {
		Logger.info("Deleting external tool named " + linkTitle);
		Element link = browser.waitForElement(By
				.xpath("//a[@title='Actions for " + linkTitle + "']"));
		link.click();
		browser.waitForElementPresent(deleteBtn).click();
		browser.waitForElementPresent(confirmDeleteBtn).click();
		return browser.waitForPage(D2lManageExternalToolsScreenV9.class);
	}

	@Override
	protected D2lNewLinkScreen openD2lNewLinkPage() {
		return browser.waitForPage(D2lNewLinkScreenV9.class, 20);
	}

	@Override
	public D2lEditWidgetScreen findAndClickOnWidgetByName(String widgetName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected D2lEditWidgetScreen waitForManageExternalToolsScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	//added by Serhei Zlatov
	@Override
	public void deleteWidgetLink(String nameOfWidget) {
		// TODO Auto-generated method stub
		
	}
	
	
}
