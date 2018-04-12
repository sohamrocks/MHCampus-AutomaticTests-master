package com.mcgraw.test.automation.ui.mhcampus.course.simnet;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl(relative = true, value = "simnetonline.com")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='content']/div/div/div/div[1]/h2")))
public class MhCampusSimNetGetStudentCourtesyAccess extends Screen {

	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='content']/div/div/div/div[1]/div[2]/button"))
	Element courtesyAccessBtn;
	
	//@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//h2[contains(text(),'Add a product')]"))
	//Element addProductText;
	
	//Edit by lior
	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = ".//*[@id='content']/div/div/div/div[1]/h2"))
	Element addProductText;
	
	public MhCampusSimNetGetStudentCourtesyAccess(Browser browser) {
		super(browser);
	}


	public boolean addProductTextIsPresent() {
		Logger.info("Checking if SIMnet add Product Text is present");
		
		boolean elementPresent = addProductText.isElementPresent();
		return elementPresent;
	}
	
	
	public void getCourtesyAccess(){
		Logger.info("Get student courtesy access   ");
		browser.waitForElementPresent(courtesyAccessBtn).click();
		browser.switchTo().defaultContent();  
		
	}

}
