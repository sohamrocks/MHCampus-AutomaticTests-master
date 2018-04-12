package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

//added by Maxym Klymenko & Andrii Vozniuk

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//button[text()='Start'] | //button[text()='Go back']")))
public class CanvasConnectAssignmentTestStartScreen extends Screen{

	public CanvasConnectAssignmentTestStartScreen(Browser browser) {
		super(browser);
	}
	
	public CanvasConnectAssignmentTestPassingScreen clickStartTestButton(){
		browser.pause(3000);
		browser.findElement(By.xpath("//button[text()='Start']")).click();
		
		return browser.waitForPage(CanvasConnectAssignmentTestPassingScreen.class, 20);
	}

}
