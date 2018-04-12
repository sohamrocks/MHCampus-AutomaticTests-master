package com.mcgraw.test.automation.ui.mhcampus.course.connect;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

//added by Maxym Klymenko & Andrii Vozniuk
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//h1[text()='Success']")))
public class CanvasConnectAssignmentSuccessScreen extends Screen{

	public CanvasConnectAssignmentSuccessScreen(Browser browser) {
		super(browser);
	}

}
