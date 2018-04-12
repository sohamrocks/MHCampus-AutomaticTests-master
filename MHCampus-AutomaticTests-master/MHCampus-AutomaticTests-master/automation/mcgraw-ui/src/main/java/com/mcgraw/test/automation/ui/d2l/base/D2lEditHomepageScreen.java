package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


public abstract class D2lEditHomepageScreen extends Screen {
	public D2lEditHomepageScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Add Widgets')]"))
	protected Element addWidgetsBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class='d2l-searchsimple-input-cell']/input"))
	protected Input searchField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class='d2l-searchsimple-search-cell']/a"))
	protected Element searchBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[@class='d2l-checkbox-container']/input[2]"))
	protected CheckBox checkBoxWidget;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Add')]"))
	protected Element addBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Save and Close')]"))
	protected Element saveAndCloseBtn;
	
	
	
	public D2lCourseDetailsScreen  addWidget(String nameOfWidget){
		Logger.info("Click to add Widget button... ");
		browser.waitForElementPresent(addWidgetsBtn).click();
		browser.makeScreenshot();
		browser.switchTo().frame(0);
		browser.waitForElementPresent(searchField).sendKeys(nameOfWidget);
		browser.waitForElementPresent(searchBtn).jsClick(browser);
		browser.pause(6000);
		browser.waitForElement(By.xpath("//label[contains(text(),'" + nameOfWidget+ "')]"));
		browser.waitForElementPresent(checkBoxWidget).click();
		browser.waitForElementPresent(addBtn).click();
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		browser.waitForElementPresent(saveAndCloseBtn).click();
		
		return waitForD2lCourseDetailsScreenPage();
		
	}
	
	protected abstract D2lCourseDetailsScreen waitForD2lCourseDetailsScreenPage();
}
