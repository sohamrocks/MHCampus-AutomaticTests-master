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
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@id='question-info-holder']")))
public class CanvasConnectAssignmentTestPassingScreen extends Screen{

	public CanvasConnectAssignmentTestPassingScreen(Browser browser) {
		super(browser);
	}

	public void clickOptionInTest(int position){
		
		browser.findElement(By.xpath("//ul[@class='answers--mc']")).findElements(By.tagName("li")).get(position).click();
	}
	
	public void clickSubmitButton(){
		browser.pause(5000);
		browser.switchTo().defaultContent();

		//do not touch, magic!!
		List<WebElement> frames = browser.findElements(By.tagName("iframe"));
		browser.switchTo().frame(frames.get(0));
		frames = browser.findElements(By.tagName("iframe")); 
		browser.switchTo().frame(frames.get(1));
		///html/body/div/header/div/div/button[2]
		System.out.println(browser.findElement(By.xpath("//html/body/div/header/div/div/button[2]")));
		browser.findElement(By.xpath("//html/body/div/header/div/div/button[2]")).click();
		browser.pause(3000);
	}
	
	public void acceptSubmitting(){
		browser.findElement(By.xpath("/html/body/div/ic-modal[2]/ic-modal-main/div[2]/button[2]")).click();
		browser.pause(4000);
	}
	
	public void clickViewResultsButton(){
		browser.findElement(By.xpath("/html/body/div/ic-modal[3]/ic-modal-main/div/button")).click();
		browser.pause(3000);
	}
}
