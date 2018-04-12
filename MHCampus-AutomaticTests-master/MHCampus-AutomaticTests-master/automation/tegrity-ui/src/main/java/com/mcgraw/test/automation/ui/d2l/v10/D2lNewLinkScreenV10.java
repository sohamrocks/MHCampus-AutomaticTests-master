package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Security Settings')]")))
public class D2lNewLinkScreenV10 extends D2lNewLinkScreen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_a"))
	Element saveBtn;

	public D2lNewLinkScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	public D2lManageExternalToolsScreen clickSaveBtn() {
		saveBtn.click();
		return browser.waitForPage(D2lManageExternalToolsScreenV10.class, 20);
	}
}
