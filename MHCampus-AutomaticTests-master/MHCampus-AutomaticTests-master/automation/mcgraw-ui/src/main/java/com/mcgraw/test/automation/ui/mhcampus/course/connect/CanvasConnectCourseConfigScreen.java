package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))

public class CanvasConnectCourseConfigScreen extends Screen {

	public CanvasConnectCourseConfigScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	//added by Maxym Klymenko
	public void checkAndClickOkOnTipsWindow() {
		try {
			Element okButton = browser
					.waitForElement(By.xpath("/html/body/div[8]/div[2]/div/div[2]/div[2]/a"), 20);
			Logger.info("Clicking on Ok button in tips window");
			okButton.click();
		}
		catch (Exception e){
			Logger.info("No Tips window found");
		}
	}
	
	//added by Maxym Klymenko
	public void clickOnSectionOptionWheelDetailOriginal(String course, String actiontext) {
		Logger.info("Clicking " + actiontext + " option for" + course);
		
		List<WebElement> elements = browser.waitForElements(By.xpath(".//div[@course_id]")); 
		
		for(WebElement element: elements) {
			
			List<WebElement> isContains= element.findElements(By.xpath(".//span[contains(text(),'" + course + "')]"));
			if(isContains.size() != 0) {
				List<WebElement> pairing = element.findElements(By.xpath(".//span[@class='icon-icn_lock']"));
				if (pairing.size() != 0) {
					element.findElement(By.xpath(".//div/div[2]/div/span[4]/span")).click();
					browser.waitForElement(By.xpath("//span[contains(text(),'" + actiontext + "')]")).click();
					return;
				}
			
			}
		}
		throw new NoSuchElementException("");
	}

	//added by Maxym Klymenko
	public Boolean copySection(String course, String email, String newCourse) {
		
		Logger.info("Copy section in " + course + "course");
		clickOnSectionOptionWheelDetailOriginal(course, "Copy section");
		CanvasConnectCopySectionScreen canvasConnectCopySectionScreen = clickCopyInCopySection();
		canvasConnectCopySectionScreen.fillAndSubmitCopySecrion(email);
		
		browser.pause(3000);
		
		if (!getLastSectionTitle().equals(course)) {
			Logger.info("Section copiing failed");
			return false;
		}
		
		changeLastCopiedSectionCourseName(newCourse);
		browser.pause(3000);
		
		if (!getLastSectionTitle().equals(newCourse)) {
			Logger.info("Section copiing failed");
			return false;
		}
		
		return true;
	}
	
	//added by Maxym Klymenko
	public String getLastSectionTitle() {
		return browser.waitForElement(By.xpath("//div[@class='course-list-wrapper course-list-wrapper-js ']/div[1]//h4/span")).getText();
	}
	
	//added by Maxym Klymenko
	public void deleteLastCopiedCourseSection() {
		Element element = browser.waitForElement(By.xpath("//span[1][@class='icon-icn_3dots']"));
		element.click();
		element = browser.waitForElement(By.xpath("//span[contains(text(), 'Delete course')]"));
		element.click();
		browser.clickOkInAlertIfPresent();
		browser.pause(2000);
		browser.clickOkInAlertIfPresent();
	}
	
	//added by Maxym Klymenko
	public void changeLastCopiedSectionCourseName(String newCourseName) {
		Element element = browser.waitForElement(By.xpath("//span[1][@class='icon-icn_3dots']"));
		element.click();
		element = browser.waitForElement(By.xpath("//span[contains(text(), 'Edit title / time zone')]"));
		element.click();
		element = browser.waitForElement(By.xpath("//input[@id='courseNameInput']"));
		element.clear();
		element.sendKeys(newCourseName);
		element = browser.waitForElement(By.xpath("//a[@class='buttons btbl smbtbl save-button-js']"));
		element.click();
		element = browser.waitForElement(By.xpath("//a[contains(text(), 'Ok, got it.')]"));
		element.click();
	}
	
	//private methods
	private CanvasConnectCopySectionScreen clickCopyInCopySection() {
		browser.waitForElement(By.xpath("//a[@id = 'copySectionToColleague']")).click();
		
		return browser.waitForPage(CanvasConnectCopySectionScreen.class, 30);
	}

}
