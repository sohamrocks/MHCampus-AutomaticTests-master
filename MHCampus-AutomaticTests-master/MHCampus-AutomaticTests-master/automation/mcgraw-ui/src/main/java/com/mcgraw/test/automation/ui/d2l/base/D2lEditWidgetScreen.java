package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
																		   
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
																		   
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
																		
																						   
																							

public abstract class D2lEditWidgetScreen extends Screen {
	
	public D2lEditWidgetScreen(Browser browser) {
		super(browser);
	}

	public abstract void setSendLinkCourseInformationToProvider(Boolean state);
									   

															   
											   
	
	public abstract void setAllowUsersToSeeLinkCheckbox(Boolean state);
								   

	public abstract void setSendToolToProvider(Boolean state);
									   

	public abstract void setSendContextToProvider(Boolean state);
										  

	public abstract void setSendUserIdToProvider(Boolean state);
										 

	public abstract void setSendUserNameToProvider(Boolean state);
										   

	public abstract void setSendUserEmailToProvider(Boolean state);
											

	public abstract void setSendLinkDescriptionToProvider(Boolean state);
											

	public abstract void setSendLinkTitleToProvider(Boolean state);
												  

	public abstract void setSignMessageWithKey(Boolean state);
																								
	
    public abstract D2lManageExternalToolsScreen clickSaveAndCloseBtn();
															 
	public abstract void chooseLinkKeyRadioBtn();

	public abstract void typeCustomerKey(String key);

	public abstract void typeSharedSecret(String secret);
	
	public abstract void typeAndSearchAddOrgUnitsCourseValue(String value);
	
	public abstract void selectFirstFoundedCourse();
	
	public abstract void setCurrentOrgUnitHEBrightspace(Boolean state);
	
	public abstract void clickAddOrgUnitsBtn();
	
	public abstract void clickAddOrgUnitsInsertBtn();
	
	public abstract void clickAddCustomParameterBtn();
	
	public abstract void typeCustomParameterName(String name);
	
	public abstract void typeCustomParameterValue(String value);
	
	public abstract void checkCourseOfferingPresent(String courseName);
  
	
	public abstract void clickSaveButton();
	
	public void scrollToElement(Element element) {
		browser.executeScript("arguments[0].scrollIntoView(true);",element);
	}

	protected abstract D2lManageExternalToolsScreen waitForManageExternalToolsScreen();
}
