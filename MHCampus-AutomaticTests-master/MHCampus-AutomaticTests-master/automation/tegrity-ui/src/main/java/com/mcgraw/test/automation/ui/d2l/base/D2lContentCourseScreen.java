package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;


public abstract class D2lContentCourseScreen extends Screen {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'd2l-textblock'][contains(text(),'Add a module...')]"))
	protected Element addModuleField;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "UnitName"))
	protected Element addModuleInput;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Add Existing Activities')]"))
	protected Element addActivitiesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'External Learning Tools')]"))
	protected Element externalToolsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Delete Module')]"))
	protected Element deleteModuleBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Delete')]"))
	protected Element deleteBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'd2l-navbar-title']"))
	protected Element courseTitle;
	
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

	public void selectToAddExternalLearningToolsToModule() {
		Logger.info("Selecting to add external tools to module"); 
		browser.makeScreenshot();
		browser.waitForElementPresent(addActivitiesBtn).click(); 
		browser.makeScreenshot();
		browser.waitForElementPresent(externalToolsBtn).click(); 
		browser.pause(6000);
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
		browser.makeScreenshot();
	}

	public int getTegrityLinksCount() {
		Logger.info("Getting count of Tegrity links...");
		browser.makeScreenshot();
		return browser.getElementsCount(getXpathForTegrityLink());
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
	
	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");	
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		String handle = browser.getWindowHandle();
		try{
			browser.pause(1000);
			getTegrityElement().waitForPresence();
			getTegrityElement().click();
			browser.pause(2000);
			browser.clickOkInAlertIfPresent(5);
			browser.pause(3000);
			browser.switchToLastWindow();
			
			if(browser.isElementPresentWithWait(By.xpath(".//div[@id='alertWindow']"),3)){
				browser.makeScreenshot();
				browser.close();
				browser.pause(500);
				browser.switchTo().window(handle);
				browser.navigate().back();
				browser.pause(1000);
				getTegrityElement().waitForPresence();
				getTegrityElement().click();
				browser.pause(2000);
				browser.clickOkInAlertIfPresent(5);
				browser.pause(3000);
				browser.switchToLastWindow();
			}

			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchTo().frame(0);
			Element openTegrityButton = browser.waitForElement(By.id("btnOpenSessionList"));
			openTegrityButton.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
	}
	
	protected abstract void chooseModuleNavigations();

	protected abstract void selectToolByName(String toolName);

	protected abstract void waitForLinkAppear(String toolName);
	
	protected abstract By getXpathForTegrityLink();
	
	protected abstract Element getTegrityElement();
}
