package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Manage External Learning Tool Links')]")))
public class D2lManageExternalToolsScreenV10 extends
		D2lManageExternalToolsScreen {

	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Delete')]"))
	protected Element deleteBtn;
	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Delete')]"))
	protected Element confirmationDeleteBtn;
	
	
	public D2lManageExternalToolsScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lNewLinkScreen openD2lNewLinkPage() {
		return browser.waitForPage(D2lNewLinkScreenV10.class, 20);
	}
	
	//Added By Andrii Vozniuk
	@Override
	public D2lEditWidgetScreen findAndClickOnWidgetByName(String widgetName) {
		Element widget = browser.waitForElement(By.xpath("//.[@id='z_b']/tbody/tr/th/a[contains(text(), '" + widgetName + "')]"));
		widget.jsClick(browser);
		return waitForManageExternalToolsScreen();
	}
	
	//Added By Andrii Vozniuk
	@Override
	protected D2lEditWidgetScreen waitForManageExternalToolsScreen() {
		return browser.waitForPage(D2lEditWidgetScreenV10.class, 20);
	}

	//Added by Serhei Zlatov
	@Override
	public void deleteWidgetLink(String nameOfWidget) {
		Element  link = browser.waitForElement(By.xpath("//a[contains(text(),'" + nameOfWidget+ "')]/following-sibling::a"));
		link.jsClick(browser);
		browser.pause(2000);
		browser.waitForElementPresent(deleteBtn).click();
		browser.pause(2000);
		browser.waitForElementPresent(confirmationDeleteBtn).click();
		
	}
}
