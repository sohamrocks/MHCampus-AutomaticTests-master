package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'pageTitleSpan'][contains(text(),'Roster Editor')]")))
public class AngelCourseManageRosterScreen extends AngelCourseContext {

	@DefinedLocators(@DefinedLocator(using = "//a[@class = 'toolbarLink'][contains(text(),'Add a User')]"))
	private Element addUserLink;

	public AngelCourseManageRosterScreen(Browser browser) {
		super(browser);
	}

	public AngelCourseRosterAddUserScreen clickAddUser() {
		addUserLink.click();
		browser.switchTo().defaultContent();
		AngelCourseRosterAddUserScreen angelCourseRosterAddUserScreen = browser.waitForPage(AngelCourseRosterAddUserScreen.class, 10);
		browser.switchTo().frame(tabFrame);
		return angelCourseRosterAddUserScreen;
	}

}
