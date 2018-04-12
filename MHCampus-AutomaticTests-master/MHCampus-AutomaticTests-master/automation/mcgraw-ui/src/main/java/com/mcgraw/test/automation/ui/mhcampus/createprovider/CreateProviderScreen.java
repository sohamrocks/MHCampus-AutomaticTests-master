package com.mcgraw.test.automation.ui.mhcampus.createprovider;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "vitalsource.com")
// Changed from How.ID to How.XPATH
// @PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how =
// How.XPATH, using = "//div[@id='book-info']")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[@id='toc-button']")) )

// @PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID,
// using = "book-container")))
public class CreateProviderScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class='title']/span"))
	Element titleOfBook;

//	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[@class='tour-close noButton']"))
//	Element closeBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "home-button"))
	Element homeBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[@class='noButton next-button nav-button']"))
	Element nextBtn;

	public CreateProviderScreen(Browser browser) {
		super(browser);
	}

	public String getTitleOfBook() {
//		closeAlert();
		goToHomeButton();
		closeWelcomeToBookShelf();
		browser.waitForElementPresent(titleOfBook);
		String title = titleOfBook.getText();
		Logger.info("The actual book title is: " + title);
		return title;
	}

	public boolean isContentOfBookAvailiable() {

		backToMainPage();
		List<WebElement> frames = browser.findElements(By.tagName("iframe"));
		// change from get(0) to get(1)
		browser.switchTo().frame(frames.get(1));
		browser.switchTo().frame(0);

		return browser.isElementPresentWithWait(By.id("pbk-extras"), 7);
	}

	/*-------------------------------------------------------------------Private Methods------------------------------------------------------------------------*/
//	private void closeAlert() {
//		browser.pause(1000);
//		closeBtn.click();
//	}

	private void goToHomeButton() {
		
		browser.pause(1000);
		homeBtn.click();
		
		browser.pause(5000);
	}

	private void backToMainPage() {
		browser.navigate().back();
		browser.pause(1000);
	}
	
	private void closeWelcomeToBookShelf() {

		String check = "";
		String temp = "Done";

		while (!check.equals(temp)) {
			nextBtn.click();
			browser.pause(500);
			check = nextBtn.getText();

		}
		nextBtn.click();
	}


}
