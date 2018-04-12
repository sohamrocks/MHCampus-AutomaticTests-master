package com.mcgraw.test.automation.ui.ecollege;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "Main"), @DefinedLocator(how = How.ID, using = "Content") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'announcementContent']")))
public class ECollegeCourseDetailsForInstructorScreen extends ECollegeCourseGeneralScreen{
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='contentMenu']/a[3]"))
	Element setupGradebookButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "nextButton1"))
	Element addNewItemsLink;

	public ECollegeCourseDetailsForInstructorScreen(Browser browser) {
		super(browser);
	}
	
	public void deleteAssignmentTitle(String assignmentTitle) throws Exception {
		Logger.info("Navigate to Gradebook screen under Instructor...");           
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(topFrame);
		gradebookButton.click();
		browser.makeScreenshot();
		
		switchToContentFrame();
		Logger.info("Click 'Setup Gradebook' button ...");
		setupGradebookButton.click();
		browser.makeScreenshot();
		Logger.info("Click 'Add New Items' link ...");
		addNewItemsLink.click();
		browser.makeScreenshot();
		
		switchToContentFrame();
		boolean wasFound = false;		
		WebElement option;
		Element select = browser.waitForElement(By.id("CustomItem1_existingItemList"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		Iterator<WebElement> itr = options.iterator();
		while(itr.hasNext()){	
		   option = itr.next();
		   if(assignmentTitle.equals(option.getText())){
			   wasFound = true;
		       option.click();
		       browser.makeScreenshot();
		       deleteTitle(assignmentTitle);
		       break;
		   }
		}
		if(!wasFound)
			Logger.info("The item: " + assignmentTitle + " was not found");
	}
	
	private void deleteTitle(String assignmentTitle)throws Exception {
		try{
			Element delete = browser.waitForElement(By.id("CustomItem1_btnDeleteCustomItem"));
			delete.click();
			browser.switchTo().alert().accept();
			browser.makeScreenshot();
			
			switchToContentFrame();
			Element saveCanges = browser.waitForElement(By.id("save2"));
			saveCanges.click();
			browser.switchTo().alert().accept();
		}catch(Exception ex){
			Logger.info("Can not delete the item: " + assignmentTitle);
			throw new Exception(ex);
		}
		browser.makeScreenshot();
		Logger.info("The item: " + assignmentTitle + " was deleted successfully...");
	}
	
	private void switchToContentFrame() {
		browser.switchTo().defaultContent();	
		browser.switchTo().frame(mainFrame);
		browser.switchTo().frame(contentFrame);
	}
}
	