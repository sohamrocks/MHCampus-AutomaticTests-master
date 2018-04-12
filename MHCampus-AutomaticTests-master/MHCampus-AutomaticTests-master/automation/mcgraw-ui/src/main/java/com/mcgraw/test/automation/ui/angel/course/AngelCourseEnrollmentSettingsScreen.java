package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class = 'pageTitleSpan'][contains(text(),'Enrollment Settings')]")))
public class AngelCourseEnrollmentSettingsScreen extends AngelCourseContext {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "USER_RIGHTS"))
	private Element userRightsSelect;

	@DefinedLocators(@DefinedLocator(using = "//input[@value = ' Save ']"))
	private Element saveButton;

	public AngelCourseEnrollmentSettingsScreen(Browser browser) {
		super(browser);
	}

	public void chooseUserRights(String rightsName) {
		userRightsSelect.sendKeys(rightsName);
	}

	public void clickSaveButton() {
		saveButton.click();
		browser.waitForElement(By.xpath("//span[@class = 'pageTitleSpan'][contains(text(),'Roster Editor')]"));
	}
}
