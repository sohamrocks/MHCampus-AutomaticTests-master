package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;

@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*")))
public class MhCampusIntroductionScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "LabelUserName"))
	Element userNameElement;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "topSearch"))
	Element educationalMaterialSearch;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "player"))
	Element player;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cboxClose"))
	Element closeVideoButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "chkAgreeTerms"))
	CheckBox agreeCheckbox;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnGetStarted"))
	Element startButton;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='TermsDialog']/p[1]"))
	Element greetingText;
 
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='TermsDialog']/p[2]"))
	Element rulesText;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//span[@class='logoTitle']"))
	Element welcomeText;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//iframe[@class='cboxIframe']"))
	Element greetingDialog;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "topSearch"))
	Input educationalMaterialSearchInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ImageButtonSearch"))
	Element educationalMaterialSearchButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "HyperLinkLogOut"))
	Element logoutLink;                                     

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cboxOverlay"))
	Element introductionIdentifyElement;

	private static final String NOFRAME = "NoFrame";
	private static final String FIRST = "First";
	private static final String SECOND = "Second";
	private static final String instructorRules = "As an instructor you have full access to all of our products and content. Feel free to look around and use whatever you like in your courses.";
	private static final String studentRules = "Welcome to McGraw-Hill Campus, where you can easily access McGraw-Hill products and services you use in your courses.";

	public MhCampusIntroductionScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isIntroductionScreenOpened() {
		if(userNameElement.waitForPresence(20)){
			Logger.info("Introduction Screen is opened");
			return true;
		}
		return false;
	}
	
	public boolean isWelcomeFrameOpened() {
		if(greetingDialog.waitForPresence(20)){
			Logger.info("Welcome Frame is opened");
			return true;
		}
		return false;
	}

	public void acceptRules(String frameName) {
		Logger.info("Accepting MH Campus terms and clicking Get Started");
		jumpToFrameOrNewTab(frameName);
		jumpToWelcomeFrame();
		browser.waitForElementPresent(welcomeText, 10);
		agreeCheckbox.setChecked(true);
		browser.pause(1000); 
		startButton.jsClick(browser);
		browser.pause(2000); 
		jumpToFrameOrNewTab(frameName);
		browser.switchTo().defaultContent();
		browser.pause(6000);
	}

	public boolean isInstructorInfoPresent(String frameName) {
		Logger.info("Checking if welcome text for instructor is shown");
		jumpToFrameOrNewTab(frameName);
		jumpToWelcomeFrame();
		browser.waitForElementPresent(welcomeText, 10);

		boolean instructorRulesPresent = rulesText.getText().equals(
				instructorRules);
		Logger.info("Rules text in Welcome dialog is: '" + rulesText.getText()
				+ "'");

		browser.switchTo().defaultContent();
		return instructorRulesPresent;
	}

	public boolean isInstructorInfoPresent() {
		return isInstructorInfoPresent(NOFRAME);
	}

	public boolean isStudentInfoPresent(String frameName) {
		Logger.info("Checking if welcome text for student is shown");
		jumpToFrameOrNewTab(frameName);
		jumpToWelcomeFrame();
		browser.waitForElementPresent(welcomeText, 10);

		boolean studentRulesPresent = rulesText.getText().equals(studentRules);
		Logger.info("Rules text in Welcome dialog is: '" + rulesText.getText()
				+ "'");
		browser.switchTo().defaultContent();
		return studentRulesPresent;
	}

	public String getFrameAddress(String frame) {
		Logger.info("Getting address of MhCampus frame...");
		browser.switchTo().defaultContent();
		
		String frameAddress;
		if (frame.equals(NOFRAME)) {
			browser.switchToLastWindow();
			frameAddress = browser.getCurrentUrl();
		} else if (frame.equals(FIRST)) {
			frameAddress = browser.switchTo().frame(0).getCurrentUrl();
		} else if (frame.equals(SECOND)) {
			// not second frame, but first frame inside first
			frameAddress = browser.switchTo().frame(0).switchTo().frame(0).getCurrentUrl();
		} else {
			frameAddress = browser.switchTo().frame(frame).getCurrentUrl();
		}
				
		Logger.info("Frame address is '" + frameAddress + "'");
		return frameAddress.toLowerCase().replace("https", "http");
	}
	
	public String getCurrentUrl() {
		Logger.info("Getting current url...");
		browser.switchTo().defaultContent();
		
		String CurrentUrl = browser.getCurrentUrl();
			
		Logger.info("Current url is '" + CurrentUrl + "'");
		return CurrentUrl.toLowerCase().replace("https", "http");
	}

	public boolean isStudentInfoPresent() {
		return isStudentInfoPresent(NOFRAME);
	}

	public void acceptRules() {
		acceptRules(NOFRAME);
	}

	public String getGreetingTextFromRulesFrame(String frameName) {
		jumpToFrameOrNewTab(frameName);
		jumpToWelcomeFrame();
		String welcomeText = browser.waitForElementPresent(greetingText)
				.getText();

		Logger.info("Greeting text in Welcome dialog is: '" + welcomeText + "'");
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return welcomeText;
	}

	public String getGreetingTextFromRulesFrame() {
		return getGreetingTextFromRulesFrame(NOFRAME);
	}

	public boolean isWelcomeToMhCampusTextPresent(String frameName) {
		Logger.info("Checking MH Campus welcome dialog...");
		jumpToFrameOrNewTab(frameName);
		boolean isWelcomeTextPresent = browser
				.isElementPresent(By
						.xpath("//*[contains(text(),'Welcome to McGraw-Hill Campus!')]"));
		browser.switchTo().defaultContent();
		return isWelcomeTextPresent;
	}

	public boolean isWelcomeToMhCampusTextPresent() {
		return isWelcomeToMhCampusTextPresent(NOFRAME);
	}

	private void jumpToWelcomeFrame() {
		browser.pause(2000); // without a delay webdriver may not switch to the
								// second frame
		Element welcomeFrame = browser.waitForElementPresent(greetingDialog);
		browser.switchTo().frame(welcomeFrame);
	}

	public MhCampusInstanceLoginScreen logOut() {
		Logger.info("Logging out...");
		logoutLink.click();
		return browser.waitForPage(MhCampusInstanceLoginScreen.class);
	}

	public String getUserNameText(String frameName) {
		jumpToFrameOrNewTab(frameName);
		userNameElement.waitForPresence(10);
		String welcomeText = userNameElement.getText();
		browser.switchTo().defaultContent();
		Logger.info("User name text is: '" + welcomeText + "'");
		return welcomeText;
	}

	public String getUserNameText() {
		return getUserNameText(NOFRAME);
	}

	public boolean isCoursePresent(String course, String frameName) {
		Logger.info("Checking if course " + course + " is present on the page");
		jumpToFrameOrNewTab(frameName);
		browser.makeScreenshot();
		boolean isCourseEnrolled = browser
				.isElementPresent(By.xpath(".//*[contains(text(),'"
						+ course.toUpperCase() + "')]"));
		browser.switchTo().defaultContent();
		return isCourseEnrolled;
	}

	public boolean isCoursePresent(String course) {
		return isCoursePresent(course, NOFRAME);
	}

	public boolean isSearchOptionPresent(String frameName) {
		Logger.info("Checking if 'FIND YOUR TEXTBOOK' option is present on the page");
		jumpToFrameOrNewTab(frameName);
		browser.makeScreenshot();
		boolean isTextSearchFieldPresent = browser.isElementPresent(By.id("q"));
		browser.switchTo().defaultContent();
		return isTextSearchFieldPresent;
	}

	public boolean isSearchOptionPresent() {
		return isSearchOptionPresent(NOFRAME);
	}

	/**
	 * Open course block with insight fields
	 * 
	 * @param courseName
	 *            - name of course
	 * @return course block
	 */
	public MhCampusCourseBlock expandCourseBlock(String courseName) {
		boolean isCoursePresent = browser.isElementPresentWithWait(
				By.xpath("//a[contains(text(),'" + courseName.toUpperCase()
						+ "')]"), 30);
		if (!isCoursePresent) {
			throw new CommonTestRuntimeException("Course " + courseName
					+ " not exist");
		}

		By collapsedCourseTogleBy = By.xpath("//a[contains(text(),'"
				+ courseName.toUpperCase()
				+ "')]/ancestor::h3[1][@class='toggle']");
		if (browser.isElementPresent(collapsedCourseTogleBy)) {
			browser.findElement(collapsedCourseTogleBy).click();
		}
		return browser.waitForPage(MhCampusCourseBlock.class, 15, courseName);
	}

	private void jumpToFrameOrNewTab(String frame) {

		browser.switchTo().defaultContent();
		browser.pause(200);    //AlexandrY added to fix local instability

		if (frame.equals(NOFRAME)) {
			browser.switchToLastWindow();
			browser.manage().window().maximize();
		} else if (frame.equals(FIRST)) {
			browser.switchTo().frame(0);
		} else if (frame.equals(SECOND)) {
			browser.switchTo().frame(0);
			browser.switchTo().frame(0);
		} else {
			browser.switchTo().frame(frame);
		}
	}
}
