package com.mcgraw.test.automation.ui.ecollege;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "Main"), @DefinedLocator(how = How.ID, using = "Content") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'announcementContent']")))
public class ECollegeCourseDetailsScreen extends ECollegeCourseGeneralScreen{
	
	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "[title='Tegrity']"))
	Element tegrityLink;	
	
	private By tegrityBtnXpath = By.cssSelector("[title='Tegrity']");

	public ECollegeCourseDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public int getTegrityLinksCount() {	
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(treeFrame);
		tegrityLink.waitForPresence(5);
		int count = browser.getElementsCount(tegrityBtnXpath);
		browser.switchTo().defaultContent();
		return count;
	}	
}
