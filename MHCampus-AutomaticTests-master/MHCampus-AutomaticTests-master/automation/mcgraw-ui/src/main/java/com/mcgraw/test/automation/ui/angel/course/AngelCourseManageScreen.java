package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'nugDataManagement']")))
public class AngelCourseManageScreen extends AngelCourseContext {

	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Roster')]"))
	private Element rosterButton;

	public AngelCourseManageScreen(Browser browser) {
		super(browser);
	}

	public AngelCourseManageRosterScreen clickRosterButton() {
		rosterButton.click();
		browser.switchTo().defaultContent();
		AngelCourseManageRosterScreen angelCourseManageRosterScreen = browser.waitForPage(AngelCourseManageRosterScreen.class, 10);
		browser.switchTo().frame(tabFrame);
		return angelCourseManageRosterScreen;
	}
}
