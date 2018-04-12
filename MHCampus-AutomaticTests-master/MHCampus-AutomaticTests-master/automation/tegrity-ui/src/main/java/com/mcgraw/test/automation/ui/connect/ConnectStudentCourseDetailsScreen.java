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
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageRelativeUrl(relative = false, value = "newconnectqastg.mheducation.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'disable_menu']")))
public class ConnectStudentCourseDetailsScreen extends Screen {
	
	@DefinedLocators(@DefinedLocator(using = "//*[@class='icon-menu_hamburger']"))
	Element goToMenu;
	
	@DefinedLocators(@DefinedLocator(using = "//*[@id='menu-userinfo']/div"))
	Element studentNameElement;
	
	//@DefinedLocators(@DefinedLocator(how = How.ID, using = "menu-classes"))
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "classesMenu"))
	Element classes;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "tegrity"))
	Element recordedLectures;
	
	public ConnectStudentCourseDetailsScreen(Browser browser) {
		super(browser);
	}
		
	public boolean isStudentNamePresentInConnect(String studentName){
		
		Logger.info("Check assignment " + studentName + " present in Connect under student");	
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.switchTo().frame(0);
		browser.makeScreenshot();
		browser.waitForElementPresent(studentNameElement, 10);
		if(studentNameElement.getText().contains(studentName))
			return true;
		return false;
	}

	public TegrityCourseDetailsScreen goToTegrity(String courseName){
		
		Logger.info("Check assignment " + courseName + " present in Connect under student");	
		browser.switchTo().defaultContent();
		browser.switchTo().frame(0);
		browser.pause(200);    //AlexandrY added to fix local instability
		browser.switchTo().frame(0);
		classes.click();
		browser.pause(500);
		classes.click();
		browser.pause(2000);	//AlexandrY added to fix local instability
		
		Element course = browser.waitForElement(By.xpath("//li/ul/li/button[contains(.,'" + courseName + "')]"));
		course.jsClick(browser);
		browser.pause(2000);
		recordedLectures.click();
		browser.pause(10000);
		browser.switchToLastWindow();
		
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = 
				browser.waitForPage(TegrityCourseDetailsScreen.class);
		browser.makeScreenshot();
		
		return tegrityCourseDetailsScreen;
	}
}
