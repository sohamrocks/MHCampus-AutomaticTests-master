package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public abstract class D2lHomeScreen extends Screen {

	
	public D2lHomeScreen(Browser browser) {
		super(browser);
	}

	final public D2lCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to the created course " + course + "...");
		browser.makeScreenshot();
		try{
			Element close = browser.waitForElement(By.xpath("//*[@id='ngdialog1']/div[2]/a"), 10);
			close.click();
			browser.pause(2000);
			browser.makeScreenshot();
		}catch(Exception e){
			//TO DO
		}
		
		try{
			Element select = browser.waitForElement(By.xpath("//*[contains(text(),'Select a course')]"), 10);
			select.click();
		}catch(Exception e){
			//TO DO
		}
		browser.pause(5000);
		browser.makeScreenshot();
		if ( browser.isElementPresentWithWait(By.xpath(".//*[@placeholder='Search for a course']"), 10))
		{
			Element courseInput = browser.waitForElement(By.xpath(".//*[@placeholder='Search for a course']"), 10);
			browser.pause(5000);
			courseInput.sendKeys(course);
			courseInput.sendKeys(Keys.ENTER);
			browser.pause(5000);
		}
		Element courseLink = browser.waitForElement(By
				.xpath(".//*[@id='d2l_minibar']//a[contains(text(),'" + course + "')]"));
		browser.pause(5000);
		courseLink.click();
		browser.clickOkInAlertIfPresent();
		browser.makeScreenshot();
		return waitForD2lCourseDetailsPage();
	}
	
	protected abstract D2lCourseDetailsScreen waitForD2lCourseDetailsPage();

	public abstract D2lLoginScreen waitForD2lLoginScreen();

	//Added by AleksandrY
	public abstract void clickMyAccountLink();

	//Added by AleksandrY
	public abstract void clickLogOutLink();

	
}
