package com.mcgraw.test.automation.ui.ecollege;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//form[@id = 'gradebookView']")))
public class ECollegeAssignmentDetailsScreen extends Screen{

	@DefinedLocators(@DefinedLocator(using = "//div[@id='profCommentDiv']"))
	Element commentElement;
	
	public ECollegeAssignmentDetailsScreen(Browser browser) {
		super(browser);
	}
	
	public String getComment(){
		return commentElement.getText();
	}

}
