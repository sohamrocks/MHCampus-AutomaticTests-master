package com.mcgraw.test.automation.ui.d2l.v9;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Grade Items')]")))
public class D2lGradesDetailsScreenV9 extends D2lGradesDetailsScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='z_c']"))
	Element pointsElement;

	private String categoryAndAssignmentXpath = "//table//*[@class = 'dco_c']//strong"; 

	public D2lGradesDetailsScreenV9(Browser browser) {
		super(browser);
	}

	@Override
	protected String getPoints() {
		return pointsElement.getText();
	}

	@Override
	protected String getCategoryAndAssignmentXpath() {
		return categoryAndAssignmentXpath;
	}

	@Override
	protected boolean isAssignmentPresent(String assignmentName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void clickEditAssignment(String assignmentName) {
		// TODO Auto-generated method stub
		
	}

}
