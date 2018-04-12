package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;

public abstract class D2lManageExternalToolsScreen extends Screen {

	public D2lManageExternalToolsScreen(Browser browser) {
		super(browser);
	}

	public D2lNewLinkScreen clickLink(String linkTitle) {
		Element link = browser.waitForElement(By.xpath("//a[contains(text(),'" + linkTitle + "')]"));
		link.click();
		return openD2lNewLinkPage();
	}

	protected abstract D2lNewLinkScreen openD2lNewLinkPage();
}
