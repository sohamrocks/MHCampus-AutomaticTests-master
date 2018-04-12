package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How; 

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentRegistrationScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'CourseName')]")))
public class D2lCourseDetailsScreenV10 extends D2lCourseDetailsScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//ul//a[@title='Module']"))
	protected Element moduleTabLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//h3[text()='Module']"))
	protected Element moduleDetailsTitle;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//iframe[@title='Content Browser']"))
	protected Element contentBrowserFrame;
	
	//add by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Continue')]"))
	protected Element continueBtn;
	
	public D2lCourseDetailsScreenV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lContentCourseScreen waitForD2lContentCoursePage() {
		D2lContentCourseScreenV10 d2lContentCourseScreenV10 = 
				browser.waitForPage(D2lContentCourseScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lContentCourseScreenV10;
	}

	@Override
	protected D2lGradesDetailsScreen waitForD2lGradesDetailsPage() {
		D2lGradesDetailsScreenV10 d2lGradesDetailsScreenV10 = 
				browser.waitForPage(D2lGradesDetailsScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lGradesDetailsScreenV10;
	}

	//add by Serhei Zlatov
	@Override
	protected D2lEditHomepageScreen waitForD2lEditHomepageScreenPage() {
		D2lEditHomepageScreenV10 d2lEditHomepageScreenV10 = browser.waitForPage(D2lEditHomepageScreenV10.class, 20);
		browser.makeScreenshot();
		return d2lEditHomepageScreenV10;
	}
	
	@Override
	public boolean isExistsPlugInErrorMessage() {
		browser.switchTo().frame(1);
		String msg = plugInErrorMessage.getText();
		return msg.equals(errormessage);
	}

	@Override
	public void clickOnModuleTabLink() {
		Logger.info("Clicking Module tab link...");
		browser.waitForElementPresent(contentBrowserFrame);
		Logger.info(String.format("switch to frame %s", contentBrowserFrame.toString()));
		browser.switchTo().frame(contentBrowserFrame);
		browser.pause(500);
		browser.waitForElementPresent(moduleTabLink);
		Logger.info(String.format("click on %s", moduleTabLink.toString()));
		moduleTabLink.click();
		browser.pause(500);
		Logger.info("switch to default content");
		browser.waitForElementPresent(moduleDetailsTitle);
		browser.switchTo().defaultContent();
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsPage() {
		return browser.waitForPage(D2lCourseDetailsScreenV10.class, 20);
	}

	
	//added by Sergei Zlatov
	@Override
	public void clickOnContinueBtn() {
		Logger.info("Clicking continue Button...");
		browser.switchTo().frame(1);
		browser.waitForElementPresent(continueBtn).click();
		isInstructor = true;
		browser.pause(5000);
		
	}

	//added by Sergei Zlatov
	@Override
	public boolean isExistErrorMessageText() {
		if(isInstructor ==false){
			browser.switchTo().frame(1);
		}
		browser.waitForElementPresent(errorMsg);
		String msg = errorMsg.getText();
		return msg.equals(errorMsgText);
		
	}
	
	//Add by AleksandrY 
	@Override
	public D2lConnectStudentRegistrationScreen clickBeginAsStudent() {
		Logger.info("Clicking Begin button as student...");
		browser.waitForElement(By.xpath(".//div[@class='d2l-htmlblock']/iframe"),30);
		browser.switchTo().frame(1);
		browser.makeScreenshot();
		browser.waitForElementPresent(begin);
		begin.click();
		browser.pause(6000);
		browser.switchToLastWindow();
		D2lConnectStudentRegistrationScreen d2lConnectStudentRegistrationScreen = browser
				.waitForPage(D2lConnectStudentRegistrationScreen.class, 20);
		browser.makeScreenshot();
		return d2lConnectStudentRegistrationScreen;
	}


}
