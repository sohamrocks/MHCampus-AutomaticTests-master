package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.BookForConnect;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentRegistrationScreen;

public abstract class D2lCourseDetailsScreen extends Screen {

	protected String errormessage = "[INVALID SOURCE] SSOD2lSource";
	
	protected String errorMsgText = "Error: LTI_REQ_004 - Configuration setup is not validated";
	
	protected boolean isInstructor;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Content')]"))
	protected Element contentLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[contains(text(),'Grades')]"))
	protected Element gradesLink;

	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class='d2l-page-main-padding']//a[@title='Edit this homepage']"))
	protected Element editHomepageBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='lblErrorMessage']"))
	protected Element plugInErrorMessage;	
	
	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(using = ".//*[@id='bodycontent']/div/section/div/span"))
	protected Element errorMsg;
	
	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='oauth2_accept_form']/div/input"))
	protected Element authorizeBtn;
	//added by Serhei Zlatov
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Begin')]"))
	protected Element begin;

	public D2lCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	//add by Serhei Zlatov
	final public D2lEditHomepageScreen clickEditHomepageBtn() {
		Logger.info("Clicking edit Homepage Button...");
		browser.waitForElementPresent(editHomepageBtn).click();
		return waitForD2lEditHomepageScreenPage();
	}

	//add by Serhei Zlatov
	protected abstract D2lEditHomepageScreen  waitForD2lEditHomepageScreenPage();

	final public D2lContentCourseScreen clickContentLink() {
		Logger.info("Clicking Content link...");
		browser.waitForElementPresent(contentLink).click();
		browser.clickOkInAlertIfPresent();
		return waitForD2lContentCoursePage();
	}

	final public D2lGradesDetailsScreen clickGradesLink() {
		Logger.info("Clicking Grades link as student...");
		browser.waitForElementPresent(gradesLink).click();
		browser.clickOkInAlertIfPresent();
		return waitForD2lGradesDetailsPage();
	}
	
	final public D2lGradesDetailsScreenForInstructorV10 clickGradesLinkAsInstructor() {
		Logger.info("Clicking Grades link as instructor...");
		browser.waitForElementPresent(gradesLink).click();
		browser.clickOkInAlertIfPresent();
		D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructorV10 = 
				browser.waitForPage(D2lGradesDetailsScreenForInstructorV10.class, 20);
		browser.makeScreenshot();
		return d2lGradesDetailsScreenForInstructorV10;
	}
	
	//added by Serhei Zlatov
	public D2lConnectScreen createCourceInConnect(String user, String appName, String courseName, String sectionName,BookForConnect book) {
		Logger.info("Create course in Connect");
		D2lConnectCreateAccountScreen d2lConnectCreateAccountScreen = beginWithConnect(appName);
		d2lConnectCreateAccountScreen.createConnectAccount(user);
		D2lConnectScreen d2lConnectScreen = d2lConnectCreateAccountScreen.createCourceInConnect(courseName,
				sectionName, book);
		return d2lConnectScreen;
	}

	// added by AleksandrY
	private D2lConnectCreateAccountScreen beginWithConnect(String appName) {
		Logger.info("Begin with Connect");
		clickOnContinueBtn();
		browser.pause(3000);
		browser.makeScreenshot();
		browser.waitForElementPresent(begin);
		begin.click();
		browser.pause(7000);
		browser.switchToLastWindow();
		D2lConnectCreateAccountScreen d2lConnectCreateAccountScreen = browser.waitForPage(D2lConnectCreateAccountScreen.class);
		browser.makeScreenshot();
		return d2lConnectCreateAccountScreen;
	}

	//added by AleksandrY
	public abstract D2lConnectStudentRegistrationScreen clickBeginAsStudent();

		
	//added by Serhei Zlatov
//		public void clickApplicationLink(String appName) {
//			Logger.info("Clicking Application Link...");
//			Element appLink = browser.waitForElement(By.xpath("//a[contains(text(),'" + appName + "')]"));
//			appLink.click();
//			browser.switchTo().frame("tool_content");
//			browser.pause(10000);
//			browser.makeScreenshot();
//		}
	
	public abstract void clickOnModuleTabLink();

	protected abstract D2lContentCourseScreen waitForD2lContentCoursePage();

	protected abstract D2lGradesDetailsScreen waitForD2lGradesDetailsPage();
	
	protected abstract D2lCourseDetailsScreen waitForD2lCourseDetailsPage();
	
	public abstract boolean isExistsPlugInErrorMessage();
	
	//added by Serhei Zlatov
	public abstract void clickOnContinueBtn();
	public abstract boolean isExistErrorMessageText();
	
}
