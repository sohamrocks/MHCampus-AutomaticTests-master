package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "ContentPlaceHolder1_iFrameServiceAdmin") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Simple LTI Test Page')]")))
public class MhCampusSSOLinkScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_CheckBoxEnableSakai"))
	Element sakaiIntegration;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_CheckBoxEnableECollege"))
	Element eCollegeIntegration;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_CheckBoxInstructorLevelToken"))
	Element canvasInstructorLevelTolken;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_tbClientString"))
	Element clientNumber;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_btSave"))
	Element saveButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ServiceAdminMain1_ManageSSO1_topMessage"))
	Element successMessage;                                 

	public MhCampusSSOLinkScreen(Browser browser) {
		super(browser);
	}
	
	public void configEcollegeIntegration (String customerNumber, String type) throws Exception {
		Logger.info("Configure eCollege integration...");
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
		if(!eCollegeIntegration.isSelected()){
			Logger.info("Select 'Enable eCollege integration'...");
			eCollegeIntegration.click();
		}
		clientNumber.waitForPresence(20);
		clientNumber.clear();
		clientNumber.sendKeys(customerNumber);
		Element option = browser.waitForElement(By.xpath(".//*[@id='ServiceAdminMain1_ManageSSO1_ddlLinkTypes']" +
				"/option[contains(text(),'" + type + "')]"));
		Logger.info("Choose type: " + type);
		option.click();
		browser.makeScreenshot();
		saveButton.click();
		if(!successMessage.waitForPresence(30)){
			Logger.info("Failed to configure eCollege integration, success message doesn't present...");
			throw new ElementNotVisibleException("Failed to configure eCollege integration, success message doesn't present");
		}
	}
	
	public void configSakaiIntegration(String type) throws Exception {
		Logger.info("Configure Sakai integration...");
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
		if(!sakaiIntegration.isSelected()){
			Logger.info("Select 'Enable Sakai integration'...");
			sakaiIntegration.click();
		}
		Element option = browser.waitForElement(By.xpath(".//*[@id='ServiceAdminMain1_ManageSSO1_DropDownLinkTypeSakai']" +
				"/option[contains(text(),'" + type + "')]"));
		Logger.info("Choose type: " + type);
		option.click();
		browser.makeScreenshot();
		saveButton.click();
		if(!successMessage.waitForPresence(30)){
			Logger.info("Failed to configure Sakai integration, success message doesn't present...");
			throw new ElementNotVisibleException("Failed to configure Sakai integration, success message doesn't present");
		}
	}
	
	public void useLevelTolkenInCanvas(Boolean flag) throws Exception {
		Logger.info("Configure 'Level Tolken' in Canvas...");
		
		browser.switchTo().frame("ContentPlaceHolder1_iFrameServiceAdmin");
		if(!canvasInstructorLevelTolken.isSelected() && flag){
			canvasInstructorLevelTolken.click();
			Logger.info("Checkbox 'Use Instructor Level Token in Canvas'was MARKED");
		}
		if(canvasInstructorLevelTolken.isSelected() && !flag){
			canvasInstructorLevelTolken.click();
			Logger.info("Checkbox 'Use Instructor Level Token in Canvas'was UNMARKED");
		}
		
		browser.makeScreenshot();
		saveButton.click();
		if(!successMessage.waitForPresence(30)){
			Logger.info("Failed configure to use 'Level Tolken' in Canvas, success message doesn't present...");
			throw new ElementNotVisibleException("Failed configure to use 'Level Tolken' in Canvas, success message doesn't present");
		}
	}
}
