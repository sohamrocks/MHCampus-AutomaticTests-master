package com.mcgraw.test.automation.ui.ecollege;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageFrameIdentificator(locators = @DefinedLocators({ @DefinedLocator(how = How.ID, using = "Main"), @DefinedLocator(how = How.ID, using = "Content") }))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id = 'announcementContent']")))
public class ECollegeCourseDetailsScreen extends ECollegeCourseGeneralScreen{
	

	public ECollegeCourseDetailsScreen(Browser browser) {
		super(browser);
	}
	
}
