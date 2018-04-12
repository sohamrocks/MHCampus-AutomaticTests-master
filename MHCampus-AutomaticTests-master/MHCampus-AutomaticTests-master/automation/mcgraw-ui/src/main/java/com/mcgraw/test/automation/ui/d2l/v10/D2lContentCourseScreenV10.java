package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentCourseDetailsScreen;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div[contains(text(),'Table of Contents')]")))
public class D2lContentCourseScreenV10 extends D2lContentCourseScreen {                       

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "(//*[starts-with(@title, 'Actions for')])[1]"))
	Element editModule;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Add Existing Activities')]"))
	protected Element addActivitiesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'External Learning Tools')]"))
	protected Element externalToolsBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'McGraw-Hill Campus')]"))
	protected Element mhCampusLinkElement;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Continue')]"))
	protected Element continueBtn;
	//added by AleksandrY
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[contains(text(),'Launch')]"))
	protected Element lunchBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Open in New Window')]"))
	protected Element openInNewWindow;
	
	//added by Andrey Pirozhenko
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'd2l-editable d2l-editable-placeholder js_contentDashboard']"))
	protected Element contentAddModule;
	
	private static By mhCampusLink = By.xpath("//a[contains(text(),'McGraw-Hill Campus')]");

	public D2lContentCourseScreenV10(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void chooseModuleNavigations() {
		editModule.click();
	}

	@Override
	protected void selectToolByName(String toolName) {
		Element linkButton = browser.waitForElement(By
				.xpath("//div[contains(text(),'" + toolName + "')]"));
		linkButton.click();
	}

	@Override
	protected void waitForLinkAppear(String toolName) {
		
		browser.waitForElement(By.xpath("//a[contains(text(),'" + toolName + "')]"));
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
		Logger.info("Clicking McGraw-Hill Campus link in V10");
		browser.waitForElementPresent(mhCampusLinkElement).click();
		WebDriverWait wait = new WebDriverWait(browser,5);
		if(wait.until(ExpectedConditions.alertIsPresent())==null){
			Logger.info("Alert was not Present");
		}
		else{
			browser.switchTo().alert().accept();
			Logger.info("Alert was present and accepted");
		}
		browser.switchToLastWindow();
		try{
			browser.waitForElementPresent(continueBtn).click();
		}catch(Exception e){
			e.toString();
		}
		
		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser
				.waitForPage(MhCampusIntroductionScreen.class);
		browser.switchTo().defaultContent();
		return mhCampusIntroductionScreen;
	}
	
	//added by Andrey Pirozhenko
	@Override
	protected void addModuleName(String moduleName) {
		Logger.info("Clicking Add a module...");
		browser.waitForElementPresent(contentAddModule).sendKeys(moduleName);
		browser.clickOkInAlertIfPresent();
	}

	//added by AleksandrY
	@Override
	public D2lConnectStudentCourseDetailsScreen lunchConnectLinkInModuleAsStudent(String moduleName, String assignmentName) {
		Logger.info(String.format("Lunch Connect link <%s> in Module <%s>", assignmentName, moduleName));
		chooseModuleBlock(moduleName);
		browser.pause(3000);
		Element appLink = browser.waitForElement(
				By.xpath("//a[contains(text(), '" + assignmentName + "')]"), 20);
		appLink.click();
		browser.pause(2000);

		Logger.info("Clicking Lunch button...");
		browser.switchTo().frame(browser.waitForElement(By.xpath(".//iframe[@title='" + assignmentName + "']")));
		browser.waitForElementPresent(lunchBtn).click();

		browser.pause(6000);
		try {
			browser.switchToLastWindow();
		} catch (Exception e) {
			browser.pause(20000);
			browser.switchToLastWindow();
		}
		browser.makeScreenshot();
		D2lConnectStudentCourseDetailsScreen d2lConnectStudentCourseDetailsScreen = browser
				.waitForPage(D2lConnectStudentCourseDetailsScreen.class, 20);
		browser.makeScreenshot();
		return d2lConnectStudentCourseDetailsScreen;
	}
}
