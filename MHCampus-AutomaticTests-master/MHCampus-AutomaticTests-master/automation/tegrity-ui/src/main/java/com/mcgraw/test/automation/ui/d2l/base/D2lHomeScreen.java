package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;

public abstract class D2lHomeScreen extends Screen {

	public D2lHomeScreen(Browser browser) {
		super(browser);
	}

	final public D2lCourseDetailsScreen goToCreatedCourse(String course) {
		Logger.info("Going to the created course " + course + "...");	
		
		if(browser.isElementVisibletWithWait(By.xpath("//*[@id='ngdialog1']/div[2]/a"), 20)){
			browser.pause(2000);
			Element close = browser.waitForElement(By.xpath("//*[@id='ngdialog1']/div[2]/a"), 3);
			close.click();
			browser.pause(2000);
			browser.makeScreenshot();
		} else {
			browser.makeScreenshot();
		}

	    if(browser.isElementVisibletWithWait(By.xpath("//*[contains(text(),'Select a course')]"), 10)){
			Element select = browser.waitForElement(By.xpath("//*[contains(text(),'Select a course')]"), 3);
			select.click();		
			browser.pause(1000);
			browser.makeScreenshot();	
	    } else {
			browser.makeScreenshot();	
	    }

		Element courseLink = browser.waitForElement(By.xpath("//*[contains(text(),'" + course + "')]"), true, 30);
		courseLink.click();
		browser.pause(1000);
		browser.makeScreenshot();
		
		return waitForD2lCourseDetailsPage();
	}

	protected abstract D2lCourseDetailsScreen waitForD2lCourseDetailsPage();
}
