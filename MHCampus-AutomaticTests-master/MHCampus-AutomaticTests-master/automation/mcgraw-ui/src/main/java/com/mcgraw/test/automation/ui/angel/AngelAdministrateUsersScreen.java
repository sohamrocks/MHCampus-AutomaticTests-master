package com.mcgraw.test.automation.ui.angel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class = 'pageTitleSpan'][contains(text(),'User Account Manager')]")))
public class AngelAdministrateUsersScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SearchText"))
	private Input userSearchInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SearchButton"))
	private Element searchBtn;

	@DefinedLocators(@DefinedLocator(using = "//button[@type = 'button'][contains(text(),'Delete')]"))
	private Element deleteBtn;

	@DefinedLocators(@DefinedLocator(using = "//span[contains(text(),'Account successfully deleted.')]"))
	private Element successDelete;

	public AngelAdministrateUsersScreen(Browser browser) {
		super(browser);
	}

	public void typeUserToSearch(String username) {
		userSearchInput.waitForPresence(20);
		userSearchInput.clearAndTypeValue(username);
	}

	public void clickSearchButton() {
		searchBtn.click();
	}
	
	public void deleteUserIfPresent(String username) {
		browser.pause(5000);
		String deleteBtnXpath = ".//a[contains(text(),'" + username + "')]//..//..//..//img[@title = 'Delete']";
		if (browser.isElementPresentWithWait(By.xpath(deleteBtnXpath), 20)) {
			browser.findElement(By.xpath(deleteBtnXpath)).click();
			deleteBtn.click();
			successDelete.waitForPresence();
			Logger.info("The user with username = " + username + " was deleted successfully...");
		}
		else
			Logger.info("Cannot delete user with username = " + username);
	}

}
