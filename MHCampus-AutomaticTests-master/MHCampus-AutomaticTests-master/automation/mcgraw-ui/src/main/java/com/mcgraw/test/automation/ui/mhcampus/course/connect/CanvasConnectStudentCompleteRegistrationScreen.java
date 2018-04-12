package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "connectqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[@class='pull-left']")))
public class CanvasConnectStudentCompleteRegistrationScreen extends Screen {

	@DefinedLocators(@DefinedLocator(using = "//*[@class='font-soft-regular welcome-title']"))
	Element successMessage;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='course-title']"))
	Element courseNameElement;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='section-title default-text']"))
	Element sectionNameElement;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='default-text']"))
	Element bookNameElement;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='font-soft-regular instructor-name']"))
	Element instructorNameElement;
	
	@DefinedLocators(@DefinedLocator(using = "//button[@class='button success large-text font-soft-semibold pointer']"))
	Element goToConnectButton;

	public CanvasConnectStudentCompleteRegistrationScreen(Browser browser) {
		super(browser);
	}
	
	public boolean isSuccessMessagePresentInConnect(){
		Logger.info("Check course 'Success' message present in Connect under student");		
		if(successMessage.getText().equals("Success"))
			return true;
		return false;
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
	
	public boolean isBookPresentInConnect(String bookName){
		Logger.info("Check book " + bookName + " present in Connect under student");		
		if(bookNameElement.getText().equals(bookName.toUpperCase()))
			return true;
		return false;
	}
	
	public boolean isInstructorNamePresentInConnect(String instructorName){
		Logger.info("Check instructor " + instructorName + " present in Connect under student");		
		if(instructorNameElement.getText().contains(instructorName.toUpperCase()))
			return true;
		return false;
	}
	
	public CanvasConnectStudentCourseDetailsScreen goToConnect() {
		Logger.info("Click 'Go to Connect' button");
		goToConnectButton.click();
		browser.pause(15000);
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		browser.makeScreenshot();
		Element noButton=browser.waitForElement(By.xpath(".//button[text()='No']"),20);
		noButton.click();	
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
		return	browser.waitForPage(CanvasConnectStudentCourseDetailsScreen.class, 20);		
	}
}
