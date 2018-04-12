package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "lmsqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "returnCanvas")))
public class D2lConnectStudentRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "courseName"))
	Element courseNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "sectionName"))
	Element sectionNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "bookTitle"))
	Element bookNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "instructorName"))
	Element instructorNameElement;

	public D2lConnectStudentRegistrationScreen(Browser browser) {
		super(browser);
	}
	
	public boolean isCoursePresentInConnect(String courseName){
		Logger.info("Check course " + courseName + " present in Connect under student");		
		if(courseNameElement.getText().equals(courseName))
			return true;
		return false;
	}
	
	public boolean isSectionPresentInConnect(String sectionName){
		Logger.info("Check section " + sectionName + " present in Connect under student");		
		if(sectionNameElement.getText().equals(sectionName))
			return true;
		return false;
	}
	
	public boolean isBookPresentInConnect(String bookName){
		Logger.info("Check book " + bookName + " present in Connect under student");		
		if(bookNameElement.getText().equals(bookName))
			return true;
		return false;
	}
	
	public boolean isInstructorNamePresentInConnect(String instructorName){
		Logger.info("Check instructor " + instructorName + " present in Connect under student");		
		if(instructorNameElement.getText().contains(instructorName))
			return true;
		return false;
	}
	
	public D2lConnectStudentCompleteRegistrationScreen registerAsStudentToConnect(String studentName, String studentPassword) {
		Logger.info("Register as student to Connect...");
		Element registration = browser.waitForElement(By.id("active-registration")); 
		registration.click();
		Element	email = browser.waitForElement(By.xpath("//input[@class = 'font-regularIt large-text login-form-input   ember-view ember-text-field']")); 
		email.sendKeys(studentName+"@gmail.com");
		browser.makeScreenshot();
		Element begin = browser.waitForElement(By.xpath("//button[contains(text(), 'BEGIN')]")); 
		begin.click(); 
		browser.pause(6000);
		browser.makeScreenshot();
		
		D2lConnectStudentCompleteRegistrationScreen d2lConnectStudentCompleteRegistrationScreen = null;
		try{
			d2lConnectStudentCompleteRegistrationScreen = fillForm(studentName, studentPassword);
		}catch(Exception e){
			Logger.info("Problem has happens during filling the form, try again...");
			browser.navigate().refresh();
			browser.clickOkInAlertIfPresent();
			browser.pause(3000);
			browser.makeScreenshot();
			d2lConnectStudentCompleteRegistrationScreen = fillForm(studentName, studentPassword);
		}
		browser.makeScreenshot();
		return d2lConnectStudentCompleteRegistrationScreen;
	}
	
	private D2lConnectStudentCompleteRegistrationScreen fillForm(String studentName, String studentPassword) {
		Element confirmEmailAddress = browser.waitForElement(By.id("confirmEmailAddress"), 20); 
		confirmEmailAddress.clear();
		confirmEmailAddress.sendKeys(studentName+"@gmail.com");
		
		Element password = browser.waitForElement(By.id("passwd")); 
		password.clear();
		password.sendKeys(studentPassword);
		
		Element confirmPasswd = browser.waitForElement(By.id("confirmPasswd")); 
		confirmPasswd.clear();
		confirmPasswd.sendKeys(studentPassword);
		
		Element firstName = browser.waitForElement(By.id("firstName")); 
		firstName.clear();
		firstName.sendKeys(studentName);
		
		Element lastName = browser.waitForElement(By.id("lastName")); 
		lastName.clear();
		lastName.sendKeys(studentName);
		
		Element securityQuestion = browser.waitForElement(By.xpath("//*[@id='securityQuestion']/option[2]")); 
		securityQuestion.click();
		
		Element securityAnswer = browser.waitForElement(By.id("securityAnswer")); 
		securityAnswer.clear();
		securityAnswer.sendKeys("Test");
		
		Element terms = browser.waitForElement(By.xpath(".//*[@id='ch-ca-agreement-container']/div/label/strong"));
		terms.click();
		browser.makeScreenshot();
		
		Element nextStep = browser.waitForElement(By.id("ca-submit-btn")); 
		nextStep.click();
		browser.makeScreenshot();

		//Fix for the markered lines - Or Kowalsky
		Element getAccess = browser.waitForElement(By.xpath(".//*[@id='access-row']/div/div[2]/div[2]/div/div/a/button")); 
		if (getAccess != null) {
			System.out.println("Found courtusy access button: " + getAccess);
		}
		getAccess.click();
		browser.makeScreenshot();
		
		//Markered out by Or Kowalsky
//		Element getAssess = browser.waitForElement(By.xpath("//a[contains(text(), 'Get')]")); 
//		getAssess.click();
//		browser.makeScreenshot();
		
		Element confirm = browser.waitForElement(By.id("place_order_btn")); 
		confirm.click();
			
		return	browser.waitForPage(D2lConnectStudentCompleteRegistrationScreen.class);
	}

}
