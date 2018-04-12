package com.mcgraw.test.automation.ui.mhcampus.coursesmart;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = false, value = "vitalsource.com")                                                                                                                    
//@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.TAG_NAME, using = "iframe") })) 
@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.XPATH, using = "//*[@title='Entering Book Content']") })) 
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "epub-container")))
public class CourseSmartProfileScreen extends Screen {
	
//	@DefinedLocators(@DefinedLocator(how = How.ID, using = "book-title"))
//	Element titleOfBook;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "toc-button"))
	Element tblConBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//button[@class='section no-drill']/div/span"))
	Element titleOfBook;

	public CourseSmartProfileScreen(Browser browser) {
		super(browser);
	}

	public String getTitleOfBook() {
		
		goToTableOfContent();
		String title = titleOfBook.getText();
		Logger.info("The actual book title is: " + title);
		browser.makeScreenshot();
		return title;
	}
	
	private void goToTableOfContent(){
		tblConBtn.click();
	}
}
