package com.mcgraw.test.automation.ui.moodle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'News forum')]")))
public class MoodleCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'McGraw-Hill Campus')]"))
	Element mcGrawHillCampusLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'Grades')]"))
	Element gradesLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@value = 'Turn editing on']"))
	Element turnEditingOnBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'select autosubmit singleselect']"))
	Element addBlockSelect;

	private static By mhCampusLink = By
			.xpath("//a[contains(text(),'McGraw-Hill Campus')]");

	private static By turnEditingOff = By
			.xpath("//*[@value = 'Turn editing off']");

	private static By mhCampusBlock = By
			.xpath("//*[contains(text(),'McGraw-Hill Campus')]");

	public MoodleCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public int getMhCampusLinksCount() {
		if(! mcGrawHillCampusLink.waitForPresence()){
			Logger.info("MH Campus link doesn't present. Trying to refresh the page...");
			browser.navigate().refresh();
			mcGrawHillCampusLink.waitForPresence();
		}
		browser.makeScreenshot();
		
		return browser.getElementsCount(mhCampusLink);
	}

	public MhCampusIntroductionScreen clickMhCampusLink() {
		Logger.info("Clicking Mh Campus link...");
		if(! mcGrawHillCampusLink.waitForPresence()){
			Logger.info("MH Campus link doesn't present. Trying to refresh the page...");
			browser.navigate().refresh();
			mcGrawHillCampusLink.waitForPresence();
		}
		browser.makeScreenshot();
		mcGrawHillCampusLink.click();
		browser.pause(8000);
		return browser.waitForPage(MhCampusIntroductionScreen.class, 10);
	}

	public MoodleGradesScreen clickGradesLink() {
		Logger.info("Clicking Grades link...");
		gradesLink.click();
		MoodleGradesScreen moodleGradesScreen = browser.waitForPage(MoodleGradesScreen.class, 20);
		browser.makeScreenshot();
		return moodleGradesScreen;
	}
	
	public MoodleGradesScreenOldVer clickGradesLink(int version) {
		Logger.info("Clicking Grades link...");
		gradesLink.click();
		MoodleGradesScreenOldVer moodleGradesScreenOldVer =
				browser.waitForPage(MoodleGradesScreenOldVer.class, 20);
		browser.makeScreenshot();
		return moodleGradesScreenOldVer;
	}

	public void turnEditingOn() {
		if (!isTurnEditingOffAvailiable()) {
			turnEditingOnBtn.click();
		}
	}

	private boolean isTurnEditingOffAvailiable() {
		return (browser.getElementsCount(turnEditingOff) != 0);
	}

	public void addBlockToCourse(String nameOfBlock) {
		Logger.info("Adding Mh Campus block to course...");
		turnEditingOn();
		addBlockSelect.sendKeys(nameOfBlock);
		addBlockSelect.click();
		browser.waitForElement(mhCampusBlock);
	}
}
