package com.mcgraw.test.automation.ui.angel;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class = 'pageTitleSpan'][contains(text(),'Administrator Console')]")))
public class AngelAdministratorConsoleScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'User Accounts')]"))
	private Element userAccountsLink;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin"))
	private Element contentWinFrame;

	public AngelAdministratorConsoleScreen(Browser browser) {
		super(browser);
	}

	public AngelAdministrateUsersScreen goToAdministateUsers() {
		userAccountsLink.click();
		browser.switchTo().defaultContent();
		AngelAdministrateUsersScreen angelAdministrateUsersScreen = browser.waitForPage(AngelAdministrateUsersScreen.class);
		browser.switchTo().frame(contentWinFrame);
		return angelAdministrateUsersScreen;
	}
}
