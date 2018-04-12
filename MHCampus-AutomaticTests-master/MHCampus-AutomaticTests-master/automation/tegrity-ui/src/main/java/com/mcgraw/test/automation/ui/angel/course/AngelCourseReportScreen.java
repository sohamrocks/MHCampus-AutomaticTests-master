package com.mcgraw.test.automation.ui.angel.course;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.angel.AngelGradesDetailsScreen;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'reportsTabGroup']")))
public class AngelCourseReportScreen extends AngelCourseContext {

	@DefinedLocators(@DefinedLocator(using = "//h2[@id = 'tabreportSettingsTab']"))
	Element reportSettingsButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "reportsTabGroup_reportSettingsTab_categoryDropdown"))
	Element reportCategory;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "reportsTabGroup_reportSettingsTab_userControl_learner_learnerDropDown"))
	Element userCategory;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "reportsTabGroup_reportSettingsTab_runButton"))
	Element runButton;

	@DefinedLocators(@DefinedLocator(using = "//*[@id = 'defaultReportPanel']//iframe"))
	Element gradesFrame;
	
	public AngelCourseReportScreen(Browser browser) {
		super(browser);
	}

	public void chooseReportSettingField() {
		Logger.info("Choosing 'Report Setting' field...");
		reportSettingsButton.waitForPresence();
		reportSettingsButton.click();

	}

	public void selectReportCategory(String name) {
		Logger.info("Selecting 'Report Category' - " + name);
		reportCategory.waitForPresence();
		reportCategory.sendKeys(name);
	}

	public void selectUserCategory(String name) {
		Logger.info("Selecting 'User Category' - " + name);
		userCategory.waitForPresence();
		userCategory.sendKeys(name);
	}

	public AngelGradesDetailsScreen clickRun() {
		Logger.info("Clicking 'Run' button...");
		runButton.waitForPresence();
		runButton.click();
		gradesFrame.waitForPresence();
		AngelGradesDetailsScreen angelGradesDetailsScreen = 
				browser.waitForPage(AngelGradesDetailsScreen.class);
		browser.switchTo().defaultContent();
		browser.switchTo().frame(tabFrame);
		browser.switchTo().frame(gradesFrame);
		browser.makeScreenshot();
		return angelGradesDetailsScreen;
	}
}
