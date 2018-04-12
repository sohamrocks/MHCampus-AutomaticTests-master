package com.mcgraw.test.automation.ui.tegrity;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage Ad-hoc Courses / Enrollments (Course Builder)"))) 
public class TegrityInstanceDashboardForHelpDeskAdminScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage Ad-hoc Courses / Enrollments (Course Builder)")) 
	Element courseBuilderLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'sign out')]"))
	Element logoutLink;

	public TegrityInstanceDashboardForHelpDeskAdminScreen(Browser browser) {
		super(browser);
	}
	
	public TegrityInstanceLoginScreen logoutFromTegrity() {
		Logger.info("Logging out...");
		logoutLink.click();
		return browser.waitForPage(TegrityInstanceLoginScreen.class);
	}

	public boolean isCourseBuilderLinkPresent() {
		Logger.info("Check if 'Course Builder' link is present");
		return courseBuilderLink.waitForPresence(10);
	}
	
}
