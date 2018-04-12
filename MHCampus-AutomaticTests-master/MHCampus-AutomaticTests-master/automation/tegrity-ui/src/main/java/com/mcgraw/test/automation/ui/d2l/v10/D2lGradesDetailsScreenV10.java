package com.mcgraw.test.automation.ui.d2l.v10;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'Grade Items')]")))
public class D2lGradesDetailsScreenV10 extends D2lGradesDetailsScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='z_e']"))
	Element pointsElement;

	private String categoryAndAssignmentXpath = "//table//*//strong";

	public D2lGradesDetailsScreenV10(Browser browser) {
		super(browser);
	}

	protected String getPoints() {
		return pointsElement.getText();
	}

	@Override
	protected String getCategoryAndAssignmentXpath() {
		return categoryAndAssignmentXpath;
	}
}
