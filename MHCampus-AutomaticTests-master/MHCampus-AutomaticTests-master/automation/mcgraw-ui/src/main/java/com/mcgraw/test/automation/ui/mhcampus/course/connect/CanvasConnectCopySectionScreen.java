package com.mcgraw.test.automation.ui.mhcampus.course.connect;


//Created by Maxym Klymenko

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'My account')] | //a[contains(text(),'Help')] | //a[contains(text(),'Sign out')]")))
public class CanvasConnectCopySectionScreen extends Screen{
	
	@DefinedLocators(@DefinedLocator(using = "//b[contains(text(),'find colleagues')]"))
	Element findColleaguesButton;
	
	@DefinedLocators(@DefinedLocator(using = "//input[@id='addColleagueEmail']"))
	Element addColleagueEmailInput;
	
	@DefinedLocators(@DefinedLocator(using = "//b[contains(text(),'copy')]"))
	Element copyButton;

	public CanvasConnectCopySectionScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	public void fillAndSubmitCopySecrion(String email) {
		
		sendKeysToColleagueEmailInput(email);
		clickFindColleaguesButton();
		List<WebElement> isFindCollegue = browser.waitForElements(By.xpath("//strong[contains(text(), 'We found your colleague!')]"));
		browser.pause(3000);
		if (isFindCollegue.size() != 0) {
			
			clickCopyButton();
		}
 	}
	
	public void clickFindColleaguesButton() {
		Logger.info("Clicking find colleagues button");
		findColleaguesButton.click();
	}
	
	public void sendKeysToColleagueEmailInput(String email) {
		Logger.info("Sending email to email field");
		addColleagueEmailInput.sendKeys(email);
	}
	
	public void clickCopyButton() {
		Logger.info("Pressing copy button");
		copyButton.click();
	}

}
