package com.mcgraw.test.automation.ui.d2l.v9;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentCourseDetailsScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(text(),'Upcoming Events')]")))
public class D2lContentCourseScreenV9 extends D2lContentCourseScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'More Actions')]"))
	Element moreActionsBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_g"))
	Element selectLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Insert')]"))
	Element insertBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Continue')]"))
	protected Element continueBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Add Activities')]"))
	protected Element addActivitiesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'External Learning Tools')]"))
	protected Element externalToolsBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'McGraw-Hill Campus')]"))
	protected Element mhCampusLinkElement;
	
	private static By mhCampusLink = By
			.xpath("//a[contains(text(),'McGraw-Hill Campus')]");

	public D2lContentCourseScreenV9(Browser browser) {
		super(browser);
	}

	@Override
	protected void chooseModuleNavigations() {
		browser.waitForElementPresent(moreActionsBtn).click();
	}

	@Override
	protected void selectToolByName(String toolName) {
		selectLink.waitForPresence(10);
		new Select(selectLink).selectOptionByName(toolName);
		insertBtn.click();
	}

	@Override
	protected void waitForLinkAppear(String toolName) {
		browser.waitForElement(By.xpath("//a[contains(text(),'" + toolName
				+ "')]"));
	}

	@Override
	protected By getXpathForMhCampusLink() {
		return mhCampusLink;
	}

	@Override
	public void selectToAddExternalLearningToolsToModule() {
		Logger.info("Selecting to add external tools to module");
		browser.makeScreenshot();
		browser.waitForElementPresent(addActivitiesBtn).click();
		browser.makeScreenshot();
		browser.waitForElementPresent(externalToolsBtn).click();
		browser.pause(6000);
	}
	
	@Override
	public MhCampusIntroductionScreen clickMhCampusLink() {
		Logger.info("Clicking McGraw-Hill Campus link in V9");
		browser.waitForElementPresent(mhCampusLinkElement).click();
		browser.waitForAlert(5);
		browser.clickOkInAlertIfPresent();
		browser.switchTo().frame(browser.waitForElementPresent(mhCampusFrame));
		try{
			browser.waitForElementPresent(continueBtn).click();
		}catch(Exception e){
			e.toString();
		}
		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser
				.waitForPage(MhCampusIntroductionScreen.class);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
		return mhCampusIntroductionScreen;
	}
	
	//added by Andrey Pirozhenko
	@Override
	protected void addModuleName(String moduleName) {
		// TODO Auto-generated method stub
	}

	@Override
	public D2lConnectStudentCourseDetailsScreen lunchConnectLinkInModuleAsStudent(String moduleName,
			String assignmentName) {
		// TODO Auto-generated method stub
		return null;
	}
}
