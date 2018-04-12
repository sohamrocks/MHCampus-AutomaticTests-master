package com.mcgraw.test.automation.ui.ecollege;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "Main")))
public class ECollegeCourseGeneralScreen extends Screen{
	
	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "#GRADEBOOK"))
	Element gradebookButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AuthorTab"))
	Element authorTabLink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Main"))
	Element mainFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Top"))
	Element topFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Content"))
	Element contentFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "Tree"))
	Element treeFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "[title='MHCampus']"))
	Element mhCampusLink;	
	
	protected void jumpToContentFrame()
	{
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(contentFrame);
	}
	
	

	public ECollegeCourseGeneralScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	public ECollegeGradesScreen clickGradesLink(String title) {
		Logger.info("Navigate to Gradebook screen...");
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(topFrame);
		
		Element gradebook = browser.waitForElement(By.id("GRADEBOOK"));
		gradebook.click();
		browser.pause(2000);
		browser.switchTo().defaultContent();
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(contentFrame);
		try{
			Element assignmentTitle = browser.waitForElement(By.xpath("//span[contains(text(),'" + title +"')]"), 20);
			assignmentTitle.click();
		} catch(Exception e)	{
			
		}
		ECollegeGradesScreen eCollegeGradesScreen = browser.waitForPage(ECollegeGradesScreen.class, 30);
		browser.makeScreenshot();
		return eCollegeGradesScreen;
	}
	
	public ECollegeCourseAuthorTab goToAuthorTab(){
		Logger.info("Click 'Author' link ");
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(treeFrame);
		authorTabLink.click();
		browser.switchTo().defaultContent();	
		return browser.waitForPage(ECollegeCourseAuthorTab.class);
	}
	
	public void clickMhCampusLink(){
		Logger.info("Click 'MhCampus' link ");
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(treeFrame);
		mhCampusLink.click();
		browser.pause(6000);
		browser.switchTo().defaultContent();
		
	}
	
	public MhCampusIntroductionScreen openMhCampusInstance(){
		Logger.info("Current URL BEFORE clicking MH Campus link is: " + browser.getCurrentUrl());
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				clickMhCampusLink();
			}
		});
		Logger.info("Current URL AFTER clicking MH Campus link is: " + browser.getCurrentUrl());
		return browser.waitForPage(MhCampusIntroductionScreen.class, 10);
	}

}
