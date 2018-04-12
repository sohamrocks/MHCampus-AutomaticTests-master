package com.mcgraw.test.automation.ui.angel;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class='pageTitleSpan'][contains(text(),'Course Search')]")))
public class AngelFindCourseScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin"))
	private Element contentWinFrame;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "KeywordSearch"))
	private Input keywordSearchInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SearchButton"))
	private Element searchButton;

	@DefinedLocators(@DefinedLocator(using = "//button[starts-with(@onclick,'DoDelete')]"))
	private Element deleteAllButton;

	public AngelFindCourseScreen(Browser browser) {
		super(browser);
	}

	public void typeCourseIdToSearch(String courseId) {
		browser.switchTo().frame(contentWinFrame);
		keywordSearchInput.waitForPresence(10);
		keywordSearchInput.typeValue(courseId);
		searchButton.click();
		browser.switchTo().defaultContent();
	}
	
	public void deleteCourseIfPresent(String courseId) {
		browser.pause(5000);
		browser.switchTo().frame(contentWinFrame);
		String courseDeleteButtonXpath = "//tbody//tr//td[contains(text(),'" + courseId + "')]//..//img[@title = 'Delete']";
		if (browser.isElementPresentWithWait(By.xpath(courseDeleteButtonXpath), 6)) {
			browser.findElement(By.xpath(courseDeleteButtonXpath)).click();
			deleteAllButton.waitForPresence(6);
			deleteAllButton.click();
			Logger.info("The course with Id = " + courseId + " was deleted successfully...");
		}
		else
			Logger.info("Cannot delete course with Id = " + courseId);
		browser.switchTo().defaultContent();
	}

}
