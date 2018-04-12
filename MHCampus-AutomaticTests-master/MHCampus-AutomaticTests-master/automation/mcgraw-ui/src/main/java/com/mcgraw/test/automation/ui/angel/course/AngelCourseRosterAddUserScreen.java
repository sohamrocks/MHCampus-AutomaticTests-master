package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class = 'pageTitleSpan'][contains(text(),'Add a User')]")))
public class AngelCourseRosterAddUserScreen extends AngelCourseContext {

	@DefinedLocators(@DefinedLocator(using = "//input[@name = 'keyword']"))
	private Input addUserInput;

	@DefinedLocators(@DefinedLocator(using = "//input[@name = 'cmdSearch']"))
	private Element searchButton;

	public AngelCourseRosterAddUserScreen(Browser browser) {
		super(browser);
	}

	public AngelCourseEnrollmentSettingsScreen selectUserToEnroll(String username) {
		typeUsernameInSearchField(username);
		searchButton.click();
		Element selectUser = browser.waitForElement(
				By.xpath("//span[@class = 'headingSpan'][contains(text(),'" + username + "')]//..//..//..//td//small//span//a"), 10);
		selectUser.click();
		browser.switchTo().defaultContent();
		AngelCourseEnrollmentSettingsScreen angelCourseEnrollmentSettingsScreen = browser
				.waitForPage(AngelCourseEnrollmentSettingsScreen.class);
		browser.switchTo().frame(tabFrame);
		return angelCourseEnrollmentSettingsScreen;
	}

	public void typeUsernameInSearchField(String username) {
		addUserInput.typeValue(username);
	}

	public void clickSearchButton() {
		searchButton.click();
	}

}
