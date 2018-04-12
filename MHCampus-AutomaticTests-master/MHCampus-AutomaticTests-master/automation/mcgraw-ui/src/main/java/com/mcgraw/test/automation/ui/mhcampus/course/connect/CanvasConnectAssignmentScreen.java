package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

//added by Maxym Klymenko & Andrii Vozniuk
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//li[@id='activitytab'] | //li[@id='previewtab'] | //li[@id='policiestab'] | //li[@id='messagetab']")))
public class CanvasConnectAssignmentScreen extends Screen{

	public CanvasConnectAssignmentScreen(Browser browser) {
		super(browser);
	}

	
	
}
