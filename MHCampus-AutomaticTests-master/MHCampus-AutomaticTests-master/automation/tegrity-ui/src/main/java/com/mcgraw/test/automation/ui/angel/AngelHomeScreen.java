package com.mcgraw.test.automation.ui.angel;

import java.util.List;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext;
import com.mcgraw.test.automation.ui.angel.course.AngelCourseContext.TabMenuItem;

@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin")))
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//span[@class='pageTitleSpan' and . = 'Home']")))
public class AngelHomeScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin"))
	private Element contentWinFrame;

	@DefinedLocators(@DefinedLocator(using = "//a[contains(text(),'Find a Course')]"))
	private Element findCourseLink;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnAdmin"))
	private Element adminConsoleBtn;

	@DefinedLocators(@DefinedLocator(using = "//div[@id='nugMyCourses']/div[@class='nugBody']//tr/td[@class='normalDiv']//a"))
	List<Element> enrolledCoursesLinks;

	public AngelHomeScreen(Browser browser) {
		super(browser);
	}

	private Element getEnrolledCourseLinkByName(String course) {
		contentWinFrame.waitForPresence();
		browser.switchTo().frame(contentWinFrame);
		for (Element enrolledCourse : enrolledCoursesLinks) {
			if (enrolledCourse.getText().contains(course))
				return enrolledCourse;
		}
		throw new CommonTestRuntimeException("No enrolled course with name " + course + "found");
	}

	public <T extends AngelCourseContext> T setCourseContext(String course, TabMenuItem tabMenuItem) {
		Logger.info("Going to the created course...");
		getEnrolledCourseLinkByName(course).click();
		browser.pause(5000);
		@SuppressWarnings("unchecked")
		T context = (T) browser.waitForPage(AngelCourseContext.class);
		browser.pause(2000);
		browser.makeScreenshot();
		
		return context.getTabContext(tabMenuItem);
	}

	public AngelFindCourseScreen goToSearchCourseScreen() {
		browser.navigate().refresh();
		browser.switchTo().frame(contentWinFrame);
		findCourseLink.click();
		browser.switchTo().defaultContent();
		return browser.waitForPage(AngelFindCourseScreen.class);
	}

	public AngelAdministratorConsoleScreen goToAdministratorConsole() {
		browser.navigate().refresh();
		browser.waitForElementPresent(adminConsoleBtn, 20);
		adminConsoleBtn.click();
		AngelAdministratorConsoleScreen angelAdministratorConsoleScreen = browser.waitForPage(AngelAdministratorConsoleScreen.class);
		browser.switchTo().frame(contentWinFrame);
		return angelAdministratorConsoleScreen;
	}
}
