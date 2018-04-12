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
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[contains(text(),'News forum')]")))
public class MoodleCourseDetailsScreen extends Screen {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	private static final String POPUP_TITLE = "popup_blocker_msg";

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Tegrity Campus')]"))
	Element tegrityCampusLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[contains(text(),'Grades')]"))
	Element gradesLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@value = 'Turn editing on']"))
	Element turnEditingOnBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'select autosubmit singleselect']"))
	Element addBlockSelect;

	private static By tegrityLink = By.xpath("//a[contains(text(),'Tegrity Campus')]");

	private static By turnEditingOff = By.xpath("//*[@value = 'Turn editing off']");

	private static By tegrityBlock = By.xpath("//h2[contains(text(),'McGraw-Hill AAIRS')]"); 

	public MoodleCourseDetailsScreen(Browser browser) {
		super(browser);
	}

	public int getTegrityLinksCount() {
		if(! tegrityCampusLink.waitForPresence()){
			Logger.info("Tegrity link doesn't present. Trying to refresh the page...");
			browser.navigate().refresh();
			tegrityCampusLink.waitForPresence();
		}
		browser.makeScreenshot(); 
		
		return browser.getElementsCount(tegrityLink);
	}
	
	public TegrityCourseDetailsScreen clickTegrityLink() {
		Logger.info("Clicking Tegrity link...");
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		try{
			if(! tegrityCampusLink.waitForPresence()){
				browser.navigate().refresh();
				tegrityCampusLink.waitForPresence();
			}
			tegrityCampusLink.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}catch (Exception e) {
			Logger.info("Click 'Open Tegrity' Button...");
			browser.switchToWindow(POPUP_TITLE);
			Element openTegrityButton = browser.waitForElement(By.id("btnOpenSessionList"));
			openTegrityButton.click();
			browser.pause(2000);
			browser.switchToWindow(TEGRITY_TITLE);
			tegrityCourseDetailsScreen = 
					browser.waitForPage(TegrityCourseDetailsScreen.class, 30);
		}
		
		browser.makeScreenshot();
		return tegrityCourseDetailsScreen;
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
		Logger.info("Adding Tegrity block to course...");
		turnEditingOn();
		addBlockSelect.sendKeys(nameOfBlock);
		addBlockSelect.click();
		browser.waitForElement(tegrityBlock);
		browser.makeScreenshot();
	}
}
