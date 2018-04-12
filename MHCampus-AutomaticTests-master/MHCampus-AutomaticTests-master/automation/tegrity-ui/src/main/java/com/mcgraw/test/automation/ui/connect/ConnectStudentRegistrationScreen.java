package com.mcgraw.test.automation.ui.connect;

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

@PageRelativeUrl(relative = false, value = "")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[@class='pull-left']")))
public class ConnectStudentRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'font-soft-bold margin-top']/h4")) 
	Element courseNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'font-soft-bold margin-top']/p")) 
	Element sectionNameElement;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'instructor-detail default-text']/span[2]")) 
	Element instructorNameElement;

	public ConnectStudentRegistrationScreen(Browser browser) {
		super(browser);
	}
	
	public boolean isCoursePresentInConnect(String courseName){
		Logger.info("Check course " + courseName + " present in Connect under student");		
		if(courseNameElement.getText().equals(courseName.toUpperCase()))
			return true;
		return false;
	}
	
	public boolean isSectionPresentInConnect(String sectionName){
		Logger.info("Check section " + sectionName + " present in Connect under student");		
		if(sectionNameElement.getText().equals(sectionName.toUpperCase()))
			return true;
		return false;
	}
	
	public boolean isInstructorNamePresentInConnect(String instructorName){
		Logger.info("Check instructor " + instructorName + " present in Connect under student");
		if(instructorNameElement.getText().equals(instructorName.toUpperCase()))
			return true;
		return false;
	}
	
	public ConnectStudentCompleteRegistrationScreen registerAsNewStudentToConnect(String studentName, String studentPassword, String accessCode) {
		Logger.info("Register as student to Connect...");
		Element	email = browser.waitForElement(By.xpath("//input[@class = 'font-regularIt large-text login-form-input   ember-view ember-text-field']")); 
		email.sendKeys(studentName+"@gmail.com");
		browser.makeScreenshot();
		Element begin = browser.waitForElement(By.xpath("//button[contains(text(), 'BEGIN')]")); 
		begin.click(); 
		browser.pause(6000);
		browser.makeScreenshot();
		
		ConnectStudentCompleteRegistrationScreen connectStudentCompleteRegistrationScreen = null;
		connectStudentCompleteRegistrationScreen = fillForm(studentName, studentPassword, accessCode);
		
		browser.makeScreenshot();
		return connectStudentCompleteRegistrationScreen;
	}
	
	public ConnectStudentCompleteRegistrationScreen loginAsStudentToConnect(String studentName, String studentPassword) {
		Logger.info("Login into Connect as student: " + studentName + " with password: " + studentPassword);
		Element	email = browser.waitForElement(By.xpath("//input[@class = 'font-regularIt large-text login-form-input   ember-view ember-text-field']")); 
		email.sendKeys(studentName+"@gmail.com");                        
		browser.makeScreenshot();
		Element begin = browser.waitForElement(By.xpath("//button[contains(text(), 'BEGIN')]")); 
		begin.click(); 
		browser.pause(2000);
		browser.makeScreenshot();
		
		email = browser.waitForElement(By.xpath("//input[@class = 'font-regularIt large-text login-form-input    ember-view ember-text-field']")); 
		email.sendKeys(studentPassword);
		browser.makeScreenshot();
		begin = browser.waitForElement(By.xpath("//button[contains(text(), 'BEGIN')]")); 
		begin.click(); 
		browser.pause(2000);
		browser.makeScreenshot();
		
		ConnectStudentCompleteRegistrationScreen connectStudentCompleteRegistrationScreen = 
				browser.waitForPage(ConnectStudentCompleteRegistrationScreen.class);
		browser.makeScreenshot();
		return connectStudentCompleteRegistrationScreen;
	}
	
	private ConnectStudentCompleteRegistrationScreen fillForm(String studentName, String studentPassword, String studentAccessCode) {
		Logger.info("Complete registration for student: " + studentName + " with password: " + studentPassword);
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
		
		//Element terms = browser.waitForElement(By.id("terms"));
		Element terms = browser.waitForElement(By.xpath(".//*[@id='ch-ca-agreement-container']/div/label/strong"));
		terms.click();
		browser.makeScreenshot();
		
		Element nextStep = browser.waitForElement(By.id("ca-submit-btn")); 
		nextStep.click();
		browser.makeScreenshot();
		
//		Element assessCode = browser.waitForElement(By.id("accessCode1"));
		Element assessCode = browser.waitForElement(By.xpath(".//input[@id='accessCode1']"));
		assessCode.clear();
		assessCode.sendKeys(studentAccessCode);
		browser.makeScreenshot();
		
		Element submit = browser.waitForElement(By.xpath("//div[@class='cpf-button-holder']/a/button")); 
		submit.click();
		browser.makeScreenshot();
		
		Element confirm = browser.waitForElement(By.id("place_order_btn")); 
		confirm.click();
			
		return	browser.waitForPage(ConnectStudentCompleteRegistrationScreen.class);
	}

}
