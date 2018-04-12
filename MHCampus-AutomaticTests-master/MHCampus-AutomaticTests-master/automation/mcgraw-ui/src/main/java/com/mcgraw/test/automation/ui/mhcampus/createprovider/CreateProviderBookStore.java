package com.mcgraw.test.automation.ui.mhcampus.createprovider;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Bookstore')]")))
public class CreateProviderBookStore extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//span[@id = 'pd-prod-name']"))
	Element titleOfBook;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "pd-buy-btn"))
	Element addToCardBtn;

	public CreateProviderBookStore(Browser browser) {
		super(browser);
	}

	public String getTitleOfBook() {
		titleOfBook.waitForNonEmptyText(30);
		browser.makeScreenshot();
		return titleOfBook.getText();
	}

	public boolean isAddToCardBtnAppear() {
		return addToCardBtn.waitForPresence(30);
	}

}
