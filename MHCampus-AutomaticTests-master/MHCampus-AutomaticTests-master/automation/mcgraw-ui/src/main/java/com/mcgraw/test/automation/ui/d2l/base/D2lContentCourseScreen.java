package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentCourseDetailsScreen;

public abstract class D2lContentCourseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'd2l-textblock'][contains(text(),'Add a module...')]"))
	protected Element addModuleField;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "UnitName"))
	protected Element addModuleInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Delete Module')]"))
	protected Element deleteModuleBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Delete')]"))
	protected Element deleteBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'd2l-navbar-title']"))
	protected Element courseTitle;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//iframe[starts-with(@title, 'McGraw-Hill Campus')]"))
	protected Element mhCampusFrame;

	public D2lContentCourseScreen(Browser browser) {
		super(browser);
	}

	public void addModule(String module) {
		Logger.info("Creating D2L module " + module);
		browser.makeScreenshot();
		browser.waitForElementPresent(addModuleField).click();
		browser.waitForElementPresent(addModuleInput).sendKeys(module);
		browser.waitForElementPresent(courseTitle).click();
		browser.waitForElement(By.xpath("//h1[contains(text(),'" + module
				+ "')]"));
		browser.pause(2000);
	}

	public void deleteModule(String moduleName) {
		Logger.info("Deleting D2L module " + moduleName);
		chooseModuleBlock(moduleName);
		chooseModuleNavigations();
		browser.waitForElementPresent(deleteModuleBtn).click();
		browser.switchTo().frame(0);
		browser.waitForElementPresent(deleteBtn).click();
		browser.switchTo().defaultContent();
	}

	public void insertExternalLearningTool(String toolName) {
		Logger.info("Adding to the module external tool named " + toolName);
		browser.makeScreenshot();
		browser.switchTo().frame(0);
		selectToolByName(toolName);
		browser.switchTo().defaultContent();
		waitForLinkAppear(toolName);
	}

	public int getMhCampusLinksCount() {
		browser.makeScreenshot();
		return browser.getElementsCount(getXpathForMhCampusLink());
	}

	public void chooseModuleBlock(String moduleName) {
		Logger.info("Choosing module " + moduleName);
		Element moduleLink = browser.waitForElement(By
				.xpath("//div[contains(text(),'module')]//..//div[contains(text(),'" + moduleName + "')]"),100);
		moduleLink.click();
		browser.waitForElement(By.xpath("//h1[contains(text(),'" + moduleName
				+ "')]"),30);
		browser.pause(2000);
		browser.makeScreenshot();
	}
	//added by AleksandrY
	public boolean isAssignmentPresent(String assignmentName) {
		return browser.isElementPresentWithWait(
				By.xpath("//a[contains(text(), '" + assignmentName + "')]"), 20);
	}

	public abstract void selectToAddExternalLearningToolsToModule();

	public abstract MhCampusIntroductionScreen clickMhCampusLink();
	
	protected abstract void chooseModuleNavigations();

	protected abstract void selectToolByName(String toolName);

	protected abstract void waitForLinkAppear(String toolName);
	
	// added by Andrey Pirozhenko
	protected abstract void addModuleName(String moduleName);
	
	protected abstract By getXpathForMhCampusLink();
	
    // added by AleksandrY
	public abstract D2lConnectStudentCourseDetailsScreen lunchConnectLinkInModuleAsStudent(String moduleName, String assignmentName);

	// added by Sergey Zlatov
	public boolean verifyItemTitle(String titleName) {
	    return browser.isElementPresentWithWait(
	    By.xpath("//h1[contains(text(), '" + titleName + "')]"), 20);
	}

}
