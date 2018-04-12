package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.RadioButton;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//div/*[contains(text(),'Pair your course')]")))
public class MhCampusConnectCourseSectionPairScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "create_new_connect_course"))
	RadioButton createNewConnectCourseOption;

	@DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "existing_connect_course"))
	RadioButton existingConnectCourseOption;

	@DefinedLocators(@DefinedLocator(using = "//span[b[.='SAVE']]"))
	Element saveButton;

	public MhCampusConnectCourseSectionPairScreen(Browser browser) {
		super(browser);
	}

	public MhCampusConnectSaveCourseSectionPair selectCourse(String courseName) {
		browser.waitForElementPresent(existingConnectCourseOption).click();
		browser.waitForElement(
				By.xpath("//span[@class='left course_name' and contains(.,'"
						+ courseName + "')]/ancestor::li[1]//input")).click();
		browser.waitForElementPresent(saveButton).click();
		return browser.waitForPage(MhCampusConnectSaveCourseSectionPair.class);
	}

	//added by Maxym Klymenko & Andrii Vozniuk
	public Boolean selectExistingCourseByName(String courseName, String originCourseName) {
		
		selectExistingRadioButton();
		selectCourseFromExistingList(courseName);
		selectRadioButtonByName(originCourseName);
		browser.pause(3000);
		
		return browser.waitForElement(By.xpath("//span[contains(text(), 're done!')]")).isDisplayed();
	}
	
	
	//added by Maxym Klymenko & Andrii Vozniuk
	public void selectCourseFromExistingList(String courseName) {
		browser.waitForElement(By.xpath("//a[@class='section_selector'][contains(text(),'" + courseName +  "')]")).click();
	}
	
	//added by Maxym Klymenko & Andrii Vozniuk
	public void selectExistingRadioButton() {
		Logger.info("Selection A section in an existing Connect course ...");
		browser.waitForElementPresent(existingConnectCourseOption).click();		
	}
	
	//added by Maxym Klymenko & Andrii Vozniuk
	public void selectRadioButtonByName(String name){
		browser.findElement(By.xpath("//span[text()='"+name+"']")).findElement(By.xpath("..")).findElement(By.tagName("input")).click();
		saveButton.click();
	}
	
}
