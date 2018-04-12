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


@PageRelativeUrl(relative = false, value = "simnetonline.com")
//@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[@id='assnGroupInfo']/div/ul/li/span"))) //does not fit to Blackboard
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='title-area']"))) //need to fined identifictor that corresponds to both LMS
public class MhCampusSimNetHomeScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = ".//*[@id='header']/nav/div/a/div"))
	Element simNetLogo;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH,using = "//*[@class='simnet-icon-small']")) //added by Yuliya
	Element simNetLogoBB;
	
	
	
	
	public MhCampusSimNetHomeScreen(Browser browser) {
		super(browser);
	}
		
	
	
	public boolean simNetLogoIsPresent() {
		browser.switchTo().frame("tool_content");
		Logger.info("Checking if SIMnet Logo is present");
		browser.pause(5000);
		boolean elementPresent = simNetLogo.isElementPresent();
		 
		return elementPresent;
	}
	
	public boolean simNetLogoIsPresentBB() {  //added by Yuliya
		Logger.info("Checking if SIMnet Logo is present");
		browser.pause(5000);
		boolean elementPresent = simNetLogoBB.isElementPresent();
		 
		return elementPresent;
	}
	
	
}
