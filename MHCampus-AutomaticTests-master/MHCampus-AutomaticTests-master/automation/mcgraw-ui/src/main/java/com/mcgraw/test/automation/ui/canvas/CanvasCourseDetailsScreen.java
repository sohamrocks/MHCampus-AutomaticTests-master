package com.mcgraw.test.automation.ui.canvas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.MouseAction.Button;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectCourseSectionPairScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.BookForConnect;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentSuccessScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentTestStartScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentCourseDetailsScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='right-side']/div[2]/h2")))
// @PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using =
// "//*[contains(text(),'Recent Activity in')]")))
public class CanvasCourseDetailsScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'grades']"))
	Element gradesBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='courses_menu_item']/a"))
	Element courseMenu;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Mcgraw-Hill Campus')]"))
	Element mhCampusLinkElement;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Cancel')]"))
	Element cancelBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@class = 'module-item-title']//a[contains(text(),'McGraw Hill Campus')]"))
	Element mhCampusWithTolken;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'settings'][contains(text(),'Settings')]"))
	Element settingsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'Modules')]"))
	Element modules;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='oauth2_accept_form']/div/input"))
	Element authorizeBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Assignments')]"))
	Element assignmentsLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@id='assignment_group_upcoming_assignments']/ul/li[1]/div/div/div[2]/a"))
	Element assignmentLink1;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@id='assignment_group_upcoming_assignments']/ul/li[2]/div/div/div[2]/a"))
	Element assignmentLink2;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Grades')]"))
	Element gradesLink;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "global_nav_profile_link"))
	Element account;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button [contains(text(),'Logout')]"))
	Element logoutButton;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class = 'border-top large-text margin-top pointer']"))
	Element notNow;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[@id='user_reset_link']"))
	Element unlinkAutomaticLink;

	@DefinedLocators(@DefinedLocator(how = How.CSS, using = "a#section_reset_link"))
	Element resetSection;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(), 'Reset')]"))
	Element reset;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'Begin')]"))
	Element begin;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='right-side']/div/a[contains(., 'Copy')]"))
	Element CopyCourseBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='right-side']/div/a[6]"))
	Element DeleteCourseBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//form[@id='copy_course_form']//input[@id='course_name']"))
	Input CopiedCourseName;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//form[@id='copy_course_form']//input[@id='course_course_code']"))
	Input CopiedCourseCode;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='copy_course_form']//button[@type=\"submit\"]"))
	Element CreateCopiedCourseBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='progress']//span[contains(@class,\"label-success\")]"))
	Element SuccessCopyLabel;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='section-tabs']/li[1]/a"))
	Element courseHomeBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='course_status_form']/button[2]"))
	Element publishCourseBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//div[@id = 'left-side']//a[contains(text(),'People')]"))
	Element peopleBtn;

	public enum canvasMappingType {
		DEFAULTMAPPING, INTERNALTOINTERNAL, INTERNALTOSIS, LOGINTOSIS, LOGINTOINTERNAL, SISTOINTERNAL, SISTOSIS
	}

	private Properties properties;
	private FileInputStream in;

	private String toolId = "Connect";
	private String propertiesPath = "src/main/resources/base.properties";

	private static By mhCampusLink = By.xpath("//a[contains(text(),'Mcgraw-Hill Campus')]");
	private static By mhCampusLinkWithTolken = By
			.xpath("//div[@class = 'module-item-title']//a[contains(text(),'McGraw Hill Campus')]");

	public CanvasCourseDetailsScreen(Browser browser) {
		super(browser);
		try {
			LoadProperties();
		} catch (IOException ex) {
			Logger.info("base.properties wasn't loaded ");
		}
	}

	private void LoadProperties() throws IOException {
		properties = new Properties();
		in = new FileInputStream(propertiesPath);
		properties.load(in);
		in.close();
	}

	public CanvasLoginScreen logout() {
		Logger.info("Logout from Canvas...");
		browser.pause(1000);
		browser.switchTo().defaultContent();
		account.click();
		browser.pause(1000);
		browser.makeScreenshot();
		logoutButton.click();
		return browser.waitForPage(CanvasLoginScreen.class);
	}

	public CanvasGradebookScreen clickGradesButton() {
		Logger.info("Clicking Grades Button...");
		gradesBtn.click();
		browser.pause(2000);
		browser.makeScreenshot();
		return browser.waitForPage(CanvasGradebookScreen.class);
	}

	public int getMhCampusLinkCount() {
		browser.makeScreenshot();
		return browser.getElementsCount(mhCampusLink);
	}

	public int getMhCampusLinkWithTolkenCount() {
		modules.click();
		browser.makeScreenshot();
		return browser.getElementsCount(mhCampusLinkWithTolken);
	}

	public void clickGradesLink() {
		Logger.info("Clicking Grades Link...");
		gradesLink.click();
		browser.pause(3000);
		browser.makeScreenshot();
	}

	public void clickAssignmentsLink() {
		Logger.info("Clicking Assignments Link...");
		assignmentsLink.click();
		browser.pause(3000);
		browser.makeScreenshot();
	}

	public String getStudentGradeFromAssignmentsList(String assignmentName) {
		Logger.info("Get student grade of assignment: " + assignmentName + " from assignments list");
		String studentGrade = null;
		clickAssignmentsLink();
		browser.pause(6000);
		List<WebElement> assignments = browser
				.findElements(By.xpath("//li[@class = 'assignment sort-disabled search_show']"));
		for (WebElement assignment : assignments) {
			String name = assignment.findElement(By.xpath("div/div/div[2]/a")).getText();
			if (name.equals(assignmentName)) {
				studentGrade = assignment.findElement(By.xpath("div/div/div[2]/div/div[2]/span[1]/span/b")).getText();
				break;
			}
		}

		Logger.info("Student grade of assignment " + assignmentName + " is " + studentGrade);
		return studentGrade;
	}

	public String getStudentGradeFromGradesTable(String assignmentName) {
		Logger.info("Get student grade of assignment: " + assignmentName + " from grades table");
		String studentGrade = null;

		clickGradesLink();
		Element table = browser.waitForElement(By.id("grades_summary"));
		Element assignment1 = table.findElement(By.xpath("tbody/tr[1]/th/a"));

		if (assignment1.getText().equals(assignmentName)) {
			studentGrade = table.findElement(By.xpath("tbody/tr[1]/td[2]/div/span")).getText();
		} else {
			Element assignment2 = table.findElement(By.xpath("tbody/tr[5]/th/a"));
			if (assignment2.getText().equals(assignmentName)) {
				studentGrade = table.findElement(By.xpath("tbody/tr[5]/td[2]/div/span/span")).getText();
			} else {
				Logger.info("Assignment: " + assignmentName + " doesn't present");
				return null;
			}
		}

		studentGrade = studentGrade.substring(studentGrade.length() - 2, studentGrade.length());
		Logger.info("Student grade of assignment " + assignmentName + " is " + studentGrade);
		return studentGrade;
	}

	public void clickApplicationLink(String appName) {
		Logger.info("Clicking Application Link...");
		Element appLink = browser.waitForElement(By.xpath("//a[contains(text(),'" + appName + "')]"));
		appLink.click();
		browser.switchTo().frame("tool_content");
		browser.pause(10000);
		browser.makeScreenshot();
	}

	public void checkAndAcceptAuthorizeIfNeed() {
		try {
			browser.waitForElement(By.xpath("//input[@value='Authorize']")).click();
		} catch (Exception e) {
			Logger.info("");
		}
	}

	public boolean isGoToConnectSectionLinkPresent(String appName) {
		Logger.info("Check 'Go to Connect section' link present...");
		Element goToConnectSectionLink = null;
		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				goToConnectSectionLink = browser.waitForElement(By.id("go_to_connect_section_link"), 5);
				break;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}

		if (goToConnectSectionLink.getText().contains("Go to my Connect section"))
			return true;
		return false;
	}

	public boolean isSectionOfConnectPresent(String name) {
		Logger.info("Check section " + name + " is present...");
		try {
			Element goToConnectSectionLink = browser.waitForElement(By.id("go_to_connect_section_link"), 5);
			if (goToConnectSectionLink.getText().contains(name))
				return true;
		} catch (Exception e) {
			Logger.info("Section " + name + " doesn't present...");
		}
		return false;
	}

	public boolean isSynchronizeWithConnectPresent() {
		Logger.info("Check course Synchronize with Connect present...");
		try {
			// browser.waitForElement(By.xpath("//*[contains(text(),'Synchronize
			// with Connect')]"), 5);
			// Added by Or
			browser.waitForElement(By.xpath("//*[contains(text(),'Tools')]"), 5);
			return true;
		} catch (Exception e) {
			Logger.info("Synchronize with Connect part doesn't present...");
		}
		return false;
	}

	public boolean isBeginButtonPresent(String appName) {
		Logger.info("Check 'Begin' button is present...");

		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				browser.waitForElement(By.xpath("//a[contains(text(),'Begin')]"), 5);
				return true;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}

		return false;
	}

	public boolean isUnlinkAutomaticLinkPresent() {
		// Defined properly now. Fix by Or
		Logger.info("Check 'Unlink automatic sign-in' link present...");
		browser.switchTo().defaultContent();
		browser.switchTo().frame("tool_content");
		Element resetLink = browser.findElement(By.xpath("//a[@id='user_reset_link']"));
		String resetPairingLink = resetLink.getText();
		System.out.println(resetPairingLink);
		browser.pause(2000);
		unlinkAutomaticLink.waitForPresence(30);
		return unlinkAutomaticLink.isElementPresent();
	}

	public void unlinkConnectAccountOfCourse(String appName) {
		Logger.info("Click 'Unlink automatic sign-in' link ...");

		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				unlinkAutomaticLink.waitForPresence(20);
				unlinkAutomaticLink.click();
				browser.makeScreenshot();
				reset.waitForPresence(20);
				reset.click();
				browser.pause(2000);
				browser.makeScreenshot();
				break;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}
	}

	public void resetSectionPairing(String appName) {
		Logger.info("Reset section pairing under instructor...");

		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				resetSection.waitForPresence(20);
				resetSection.click();
				browser.makeScreenshot();
				reset.waitForPresence(20);
				reset.click();
				browser.pause(2000);
				browser.makeScreenshot();
				break;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}
	}

	public boolean isUserEmailPresent(String user) {
		Logger.info("Check email for user " + user + " present...");
		// Element unlinkAutomaticContainer =
		// browser.waitForElement(By.id("user_reset_link"));
		// Put in my own xpath (Or Kowalsky)
		Element unlinkAutomaticContainer = browser
				.waitForElement(By.xpath("//widget-link[@id='user_reset_link']/div/div[2]/div"));
		Logger.info(unlinkAutomaticContainer.getText());
		if (unlinkAutomaticContainer.getText().contains(user.toLowerCase() + "@gmail.com"))
			return true;
		return false;
	}

	// added by Maxym Klymenko
	public String getUserEmail() {
		Logger.info("Getting user email");
		Element userEmailElement = browser
				.waitForElement(By.xpath("//widget-link[@id='user_reset_link']/div/div[2]/div//b"));
		return userEmailElement.getText();
	}

	public int getQuantityOfAsignments() {
		Logger.info("Get quantity of assignments...");
		List<WebElement> assignments = browser.waitForElements(By.xpath("//*[@class = 'assignment search_show']"), 20);
		return assignments.size();
	}

	public int getQuantityOfAsignmentsForStudent() {
		Logger.info("Get quantity of assignments for student...");
		List<WebElement> assignments = browser
				.waitForElements(By.xpath("//*[@class = 'assignment sort-disabled search_show']"), 20);
		return assignments.size();
	}

	public boolean isMessageForAssignmentStartInFuturePresent(String assignment) {
		Logger.info("Check message for assignment " + assignment + " , that start in future is present");
		assignmentLink1.waitForPresence(20);
		assignmentLink2.waitForPresence(20);
		if (assignmentLink1.getText().equals(assignment))
			assignmentLink1.click();
		else if (assignmentLink2.getText().equals(assignment))
			assignmentLink2.click();
		else
			Logger.info("Assignment " + assignment + " doesn't exist");
		browser.pause(6000);

		browser.makeScreenshot();
		Element message = browser.waitForElement(By.id("content"));
		if (message.getText().contains("This assignment is locked until"))
			return true;
		return false;
	}

	public CanvasConnectStudentCourseDetailsScreen clickAssignmentLinkThatAlreadyStarted(String assignment) {
		Logger.info("Clicking assignment " + assignment);
		assignmentLink1.waitForPresence(20);
		assignmentLink2.waitForPresence(20);

		if (assignmentLink1.getText().equals(assignment))
			assignmentLink1.click();
		else if (assignmentLink2.getText().equals(assignment))
			assignmentLink2.click();
		else {
			Logger.info("Assignment " + assignment + " doesn't exist");
			return null;
		}

		browser.pause(20000);
		try {
			browser.switchToLastWindow();
		} catch (Exception e) {
			browser.pause(20000);
			browser.switchToLastWindow();
		}
		browser.makeScreenshot();
		try {
			browser.switchTo().frame(0);
			browser.switchTo().frame(0);
		} catch (Exception e) {
			browser.closeAllWindowsExceptFirst();
			if (assignmentLink1.getText().equals(assignment))
				assignmentLink1.click();
			else if (assignmentLink2.getText().equals(assignment))
				assignmentLink2.click();
			browser.pause(20000);
			browser.switchToLastWindow();
			browser.makeScreenshot();
		}

		CanvasConnectStudentCourseDetailsScreen canvasConnectStudentCourseDetailsScreen = browser
				.waitForPage(CanvasConnectStudentCourseDetailsScreen.class, 20);
		browser.makeScreenshot();
		return canvasConnectStudentCourseDetailsScreen;
	}

	public CanvasConnectStudentRegistrationScreen clickBeginAsStudent(String appName) {
		Logger.info("Register as student to Connect...");

		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				browser.waitForElement((By.xpath("//a[contains(text(),'Begin')]")), 5);
				break;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}

		browser.makeScreenshot();
		// begin.click(); old version
		browser.waitForElement((By.xpath("//a[contains(text(),'Begin')]")), true, 5).click();
		browser.pause(6000);
		browser.switchToLastWindow();
		CanvasConnectStudentRegistrationScreen canvasConnectStudentRegistrationScreen = browser
				.waitForPage(CanvasConnectStudentRegistrationScreen.class, 20);
		browser.makeScreenshot();
		return canvasConnectStudentRegistrationScreen;
	}

	public boolean isAssignmentPresent(String assignmentName) {
		Logger.info("Check created in Connect Assignment " + assignmentName + " present in Canvas...");
		if (browser.isElementPresentWithWait(By.xpath("//*[contains(text(),'" + assignmentName + "')]"), 20))
			return true;
		return false;
	}

	public String getMaxPoints(String assignmentName) {
		Logger.info("Get max points of assignment " + assignmentName + " present in Canvas...");
		clickEditAssignment(assignmentName);
		String maxPoints = browser.waitForElement(By.id("assignment_points_possible")).getIdentifyingText();
		Logger.info("Max points of assignment " + assignmentName + " is " + maxPoints);
		return maxPoints;
	}

	public Boolean isDatesOfAssignmentEqual(String assignmentName, String startDate, String endDate,
			Boolean onThisDate) {
		Logger.info("Check the dates of assignment " + assignmentName);
		clickEditAssignment(assignmentName);
		Boolean result = checkTheDates(startDate, endDate, onThisDate);
		return result;
	}

	public CanvasCourseDetailsScreen goToCreatedCourse(String course) {
		CanvasCourseDetailsScreen canvasCourseDetailsScreen = null;
		Logger.info("Going to the created course " + course + "...");
		Element courses = browser.waitForElement(By.id("global_nav_courses_link"));
		courses.click();
		Element cooseCourse = browser.waitForElement(By.xpath("//a[contains(text(),'" + course + "')]"), 20);
		cooseCourse.jsClick(browser);
		canvasCourseDetailsScreen = browser.waitForPage(CanvasCourseDetailsScreen.class);
		browser.makeScreenshot();
		return canvasCourseDetailsScreen;
	}

	public MhCampusIntroductionScreen clickMhCampusLink() {
		Logger.info("Clicking Mh Campus link...");
		browser.makeScreenshot();
		mhCampusLinkElement.click();
		browser.pause(6000);
		browser.switchTo().frame("tool_content");
		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser.waitForPage(MhCampusIntroductionScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusIntroductionScreen;
	}

	public MhCampusIntroductionScreen clickMhCampusLinkAndAcceptTolkenForLti(Boolean createTolken) {
		Logger.info("Clicking Mh Campus link...");
		browser.makeScreenshot();
		modules.click();
		mhCampusWithTolken.click();
		browser.waitForAlert(6000);
		browser.clickOkInAlertIfPresent();
		browser.switchTo().frame("tool_content");
		if (createTolken) {
			authorizeBtn.waitForPresence(10);
			authorizeBtn.click();
		}

		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser.waitForPage(MhCampusIntroductionScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusIntroductionScreen;
	}

	public MhCampusIntroductionScreen clickMhCampusLinkAndAcceptTolkenForCsvFilter(Boolean createTolken) {
		Logger.info("Clicking Mh Campus link...");
		browser.pause(2000);
		browser.makeScreenshot();
		mhCampusLinkElement.click();
		browser.pause(8000);
		browser.switchTo().frame("tool_content");
		browser.makeScreenshot();
		if (createTolken) {
			Logger.info("Accepting level token...");
			authorizeBtn.waitForPresence(10);
			authorizeBtn.click();
		} else {
			Logger.info("Click 'Cansel' button under level token...");
			cancelBtn.waitForPresence(10);
			cancelBtn.click();
		}

		MhCampusIntroductionScreen mhCampusIntroductionScreen = browser.waitForPage(MhCampusIntroductionScreen.class);
		browser.makeScreenshot();
		browser.switchTo().defaultContent();
		return mhCampusIntroductionScreen;
	}

	public void createMhCampusApplication(String appName, String key, String secret, String ltiLaunchUrl)
			throws Exception {
		try {
			createMhCampusApplication(appName, key, secret);
		} catch (Exception e) {
			Logger.info("Failed to create a new MhCampus application, trying again...");
			createMhCampusApplication(appName, key, secret);
		}
		try {
			configureMhCampusApplication(appName, ltiLaunchUrl);
		} catch (Exception e) {
			Logger.info("Failed to replace Url in new crwated MhCampus application, trying again...");
			configureMhCampusApplication(appName, ltiLaunchUrl);
		}
	}

	public void createMhCampusApplication(String appName, String key, String secret, String ltiLaunchUrl,
			canvasMappingType canvasmappingtype, boolean isDeepIntegration) throws Exception {
		try {
			createMhCampusApplication(appName, key, secret);
		} catch (Exception e) {
			Logger.info("Failed to create a new MhCampus application, trying again...");
			createMhCampusApplication(appName, key, secret);
		}
		try {
			configureMhCampusApplication(appName, ltiLaunchUrl, canvasmappingtype, isDeepIntegration);
		} catch (Exception e) {
			Logger.info(
					"Failed to configure MhCampus Application in new created MhCampus application, trying again...");
			configureMhCampusApplication(appName, ltiLaunchUrl, canvasmappingtype, isDeepIntegration);
		}
	}

	public void createModuleWithApplication(String moduleName, String appName) {
		createModule(moduleName);
		addApplicationToModule(appName);
	}

	public void createApplicationLink(String key, String secret, String xmlFileConfiguration, String appName) {
		Logger.info("Creating application with name: " + appName);
		createAppForConnect(appName);
		fillTheForm(appName, key, secret, xmlFileConfiguration);
	}

	public boolean isApplicationAdded(String appName) {
		Logger.info("Check, that application with name: " + appName + " was added...");
		String nameOfCreatedLink = browser
				.waitForElement(By.xpath("//*[@id = 'external-tools-table']/tbody/tr[1]/td[2]/span"), 20).getText();
		if (nameOfCreatedLink.equals(appName))
			return true;
		return false;
	}

	public boolean checkApplicationLinkPresentInCanvas(String appName) {
		Logger.info("Check application link: " + appName + " present in Canvas");
		if (browser.isElementPresentWithWait(By.xpath("//a[contains(text(),'" + appName + "')]")))
			return true;
		return false;
	}

	public CanvasConnectScreen createCourceInConnect(String user, String appName, String courseName, String sectionName,
			BookForConnect book) {
		Logger.info("Create course in Connect");
		CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen = beginWithConnect(appName);
		canvasConnectCreateAccountScreen.createConnectAccount(user);
		CanvasConnectScreen canvasConnectScreen = canvasConnectCreateAccountScreen.createCourceInConnect(courseName,
				sectionName, book);
		return canvasConnectScreen;
	}

	// added by Maxym Klymenko
	public CanvasConnectCourseDetailsScreen clickOnGoToMyConnectSection() throws InterruptedException {
		Logger.info("Clicking Go to my Connect section...");
		Element appLink = browser.waitForElement(By.xpath("//a[@id='go_to_connect_section_link']"));
		appLink.click();

		browser.pause(20000);
		browser.switchToLastWindow();

		return browser.waitForPage(CanvasConnectCourseDetailsScreen.class, 20);
	}

	// added by Maxym Klymenko & Andrii Vozniuk
	public MhCampusConnectCourseSectionPairScreen clickOnPairWithAConnectSection() {

		Logger.info("Clicking pair with a Connect section...");
		
		browser.waitForElement(By.xpath("//a[@id='section_pair_link']")).click();
		browser.pause(10000);
		browser.switchToLastWindow();

		return browser.waitForPage(MhCampusConnectCourseSectionPairScreen.class, 20);
	}

	// added by Maxym Klymenko & Andrii Vozniuk
	public void clickOnRelinkAssignmentsFromCopiedCourse() {
		
		Logger.info("Clicking on Re-link assignments from copied course");
		
		browser.waitForElement(By.xpath("//a[@id='manual_assignment_link']")).click();
		browser.pause(5000);
	}

	public String PrepereToTestWidgetWithGatewayPreviewMode(String appName, boolean role) {
		Logger.info("Create course in Connect");
		String Source = GetScourceOfThePage(appName, role);
		Logger.info("Source Ready To compare");
		return Source;

	}

	public CanvasConnectCreateAccountScreen ClickOnToolLink(String appName, boolean isInstructorWithIlt) {

		CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen = null;
		Logger.info("Begin with Connect");
		clickApplicationLink(appName);
		authorizeBtn.waitForPresence(10);
		if (isInstructorWithIlt) {
			authorizeBtn.click();
		}
		browser.pause(10000);
		browser.switchTo().defaultContent();
		browser.switchTo().frame("tool_content");
		try {
			canvasConnectCreateAccountScreen = browser.waitForPage(CanvasConnectCreateAccountScreen.class);
		} catch (Exception e) {
			browser.switchTo().defaultContent();
			clickApplicationLink(appName);
			browser.switchTo().frame("tool_content");
			canvasConnectCreateAccountScreen = browser.waitForPage(CanvasConnectCreateAccountScreen.class);
		}
		browser.makeScreenshot();
		return canvasConnectCreateAccountScreen;

	}

	// modified by Maxym Klymenko
	public Boolean copyThisCourse(String copiedCourseName) {

		Logger.info("Going to copy current course to course " + copiedCourseName + "...");
		Boolean isCopySuccessful = false;
		settingsBtn.click();
		CopyCourseBtn.click();
		CopiedCourseName.clearAndTypeValue(copiedCourseName);
		CopiedCourseCode.clearAndTypeValue(copiedCourseName);
		CreateCopiedCourseBtn.click();
		browser.makeScreenshot();
		isCopySuccessful = browser.isElementPresentWithWait(
				By.xpath("//*[@class='label label-success' and contains(text(),'Completed')]"), 15);
		return isCopySuccessful;
	}
	
	// modified by Maxym Klymenko
	public void deleteThisCourse() {

		Logger.info("Going to delete current course ...");
		settingsBtn.click();
		DeleteCourseBtn.click();
		browser.pause(3000);
		browser.waitForElement(By.xpath("//button[contains(text(),'Delete Course')]")).click();
		browser.makeScreenshot();

	}

	// modified by Maxym Klymenko
	public Boolean publishCopiedCourse() {

		Logger.info("Going to publish copied course ...");
		
		courseHomeBtn.click();
		publishCourseBtn.click();

		// in case i need to chose homepage
		try {
			browser.waitForElement(By.xpath("//span[contains(text(), 'Course Activity Stream')]"), 10).click();
			browser.pause(5000);
			browser.waitForElement(By.xpath("//span[contains(text(), 'Choose and Publish')]/..")).jsClick(browser);
		} catch (Exception e) {

		}
		if (publishCourseBtn.toString().equals("Published")) return true;
		
		return false;
		
	}

	// added by Maxym Klymenko
	public Boolean enrollUserToCourseByLoginIdAsStudent(String loginId) throws InterruptedException {
		
		Logger.info("Going to enroll student to course ...");
		
		browser.pause(3000);
		clickPeopleBtn();
		clickAddUsersBtn();
		clickAddUsersByOption("Login ID");
		Element input = browser.waitForElement(By.xpath("//textarea"), 10);
		input.clear();
		input.sendKeys(loginId);
		clickAddPeopleNextButton();
		if (!isPeopleAddedByLoginId(loginId))
			return false;
		clickAddPeopleNextButton();
		browser.pause(1500);
		return true;
		// return false;
	}

	// added by Maxym Klymenko
	public void clickAddUsersByOption(String option) throws InterruptedException {
		Logger.info("Setting Login Id option...");
		browser.pause(3000);
		browser.waitForElement(By.xpath("//span[contains(text(), '" + option + "')]"), 10).click();
	}

	// added by Maxym Klymenko
	public void clickPeopleBtn() {
		Logger.info("Clicking People button...");
		peopleBtn.click();
	}

	// added by Maxym Klymenko
	public void clickAddUsersBtn() {
		Logger.info("Clicking People button...");
		browser.waitForElement(By.xpath("//a[@id = 'addUsers']"), 10).click();
	}

	// added by Andrii Vozniuk
	public void clickAddPeopleNextButton() {
		Logger.info("Clicking Next button...");
		browser.waitForElement(By.id("addpeople_next"), 10).click();
	}

	// added by Andrii Vozniuk
	private Boolean isPeopleAddedByLoginId(String loginId) {
		try {
			browser.pause(2000);
			browser.waitForElement(By.xpath("//table/tbody/tr/td[3][text()='" + loginId + "']"));
			/// html/body/span/span/span/span/div[2]/div/div/div/table/tbody/tr/td[3]
			browser.waitForElement(
					By.xpath("//div[text()='The following users are ready to be added to the course.']"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// added by Maxym Klymenko & Andrii Vozniuk
		public void clickSyncButton() {
			
			Logger.info("Clicking Sync Button");
			
			browser.findElement(By.xpath("//button[contains(text(),'Sync')]")).click();
		}
		
		
		// added by Maxym Klymenko
		public Boolean isAssignmentRelinkedFromCopiedCourse(String deepAppName) {
			browser.pause(3000);
			clickOnRelinkAssignmentsFromCopiedCourse();
			return browser.waitForElement(By.xpath("//h1[contains(text(), 'Connect assignments have already been synced.')]")).isDisplayed();
		}

		// added by Maxym Klymenko & Andrii Vozniuk
		public CanvasConnectAssignmentScreen launchAssignmentByNameAsInstructor(String name) {
			
			Logger.info("Going to launch " + name + " assignment");
			
			clickAssignmentLinkByName(name);
			browser.pause(10000);

			try {
				browser.switchToLastWindow();
				CanvasConnectAssignmentScreen resultScreen = browser.waitForPage(CanvasConnectAssignmentScreen.class, 20);
				return resultScreen;
			} catch (Exception e) {
				clickLaunchButton();
			}

			return browser.waitForPage(CanvasConnectAssignmentScreen.class, 20);
		}

		// added by Maxym Klymenko & Andrii Vozniuk
		public Screen launchAssignmentByNameAsStudent(String name, Boolean firstly) {
			
			Logger.info("Going to launch " + name + " assignment");
			
			browser.pause(4000);
			clickAssignmentLinkByName(name);
			clickLaunchButton();
			browser.pause(10000);
			browser.switchToLastWindow();
			browser.pause(5000);
			
			if (firstly)
				return browser.waitForPage(CanvasConnectAssignmentSuccessScreen.class, 20);
			else{
				//do not touch, magic!!
				List<WebElement> frames = browser.findElements(By.tagName("iframe"));
				browser.switchTo().frame(frames.get(0));
				frames = browser.findElements(By.tagName("iframe")); 
				browser.switchTo().frame(frames.get(1));
				System.out.println(browser.findElement(By.xpath("//button[text()='Start']")));
				return browser.waitForPage(CanvasConnectAssignmentTestStartScreen.class, 20);
			}

		}

		// added by Maxym Klymenko & Andrii Vozniuk
		public void clickAssignmentLinkByName(String name) {
			
			Logger.info("Clicking on " + name + "assignment");
			
			browser.waitForElement(By.xpath("//a[contains(text(), '" + name + "')]")).click();
		}

		// added by Maxym Klymenko & Andrii Vozniuk
		public void clickLaunchButton() {
			
			Logger.info("Trying to click Launch Button if it present");
			
			try {
				browser.waitForElement(By.xpath("//button[contains(text(), 'Launch')]"), 15).click();
			} catch (Exception e) {
			}
		}

	// ----------------------------------- Private methods
	// ----------------------------------------

	private void clickEditAssignment(String assignmentName) {
		browser.pause(1000);
		if (browser.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[1]/div/div/div[3]/a"), 10).getText()
				.contains(assignmentName)) {
			browser.pause(1000);
			Element selectEdit = browser
					.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[1]/div/div/div[4]/div/a"));
			selectEdit.click();
			Element edit = browser
					.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[1]/div/div/div[4]/div/ul/li[1]/a"));
			edit.click();
		} else if (browser.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[2]/div/div/div[3]/a"), 10)
				.getText().contains(assignmentName)) {
			browser.pause(1000);
			Element selectEdit = browser
					.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[2]/div/div/div[4]/div/a"));
			selectEdit.click();
			Element edit = browser
					.waitForElement(By.xpath("//*[@class = 'assignment-list']/ul/li[2]/div/div/div[4]/div/ul/li[1]/a"));
			edit.click();
		} else {
			Logger.info("The assignment " + assignmentName + " was not found");
			return;
		}
		browser.makeScreenshot();
		Element moreOptions = browser.waitForElement(By.xpath("//form[@id='ui-id-3']/div/div[2]/button[1]"), 20);
		moreOptions.click();
		browser.makeScreenshot();
	}

	private boolean checkTheDates(String startDate, String endDate, Boolean onThisDate) {
		Element startInput = browser.waitForElement(By.xpath(
				"//div[@id='overrides-wrapper']/div[2]/div/div/div/div[2]/div[2]/div/div/div[1]/div/div/div[1]/input"));
		String startDateFromInput = startInput.getAttribute("value");
		Element dueInput = browser.waitForElement(
				By.xpath("//div[@id='overrides-wrapper']/div[2]/div/div/div/div[2]/div[1]/div/div/div/div[1]/input"));
		String endDateFromInput = dueInput.getAttribute("value");

		Boolean isStartDatesEqual = null;
		Boolean isEndDatesEqual = null;
		if (onThisDate) {
			try {
				Logger.info("Compare 2 START dates: " + startDate + " and: " + startDateFromInput);
				isStartDatesEqual = isDatesEqual(startDate, startDateFromInput);
			} catch (Exception e) {
				Logger.info("The problem occurred during the comparison the dates");
				e.printStackTrace();
			}
		} else {
			Logger.info("Compare 2 START dates: today and: " + startDateFromInput);
			isStartDatesEqual = isStartDatesFromTodayEqual(startDateFromInput);
		}

		try {
			Logger.info("Compare 2 DUE dates: " + endDate + " and: " + endDateFromInput);
			isEndDatesEqual = isDatesEqual(endDate, endDateFromInput);
		} catch (Exception e) {
			Logger.info("The problem occurred during the comparison the dates");
			e.printStackTrace();
		}

		return isStartDatesEqual && isEndDatesEqual;
	}

	private void configureMhCampusApplication(String appName, String ltiLaunchUrl) throws Exception {
		Logger.info("Replacing URL in MhCampus application...");
		Element viewApp = browser.waitForElement(By.xpath("//*/a[contains(text(),'View App Configurations')]"));
		viewApp.click();

		WebElement app, editApp, edit, launchUrl, customFields, submit;
		String applikationName;
		Element tableOfUsers = browser.waitForElement(By.xpath("//*[@id='external-tools-table']/tbody"));
		List<WebElement> apps = tableOfUsers.findElements(By.tagName("tr"));

		for (int i = 0; i <= apps.size(); i++) {
			app = apps.get(i);
			applikationName = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[2]/span[1]")).getText();
			Logger.info("applikationName: " + applikationName);
			if (applikationName.equals(appName)) {
				Logger.info("Trying to replase: ");
				editApp = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[3]/div/a/i[2]"));
				editApp.click();
				edit = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[3]//*/a[contains(text(),'Edit')]"));
				edit.click();
				break;
			} else {
				if (i == apps.size()) {
					Logger.info("Was not faund applikation with name: " + appName);
					throw new Exception();
				}
			}
		}

		launchUrl = browser.waitForElement(By.xpath("//*/form/div[1]/div/div/div[3]/label/input"));
		launchUrl.clear();
		launchUrl.sendKeys(ltiLaunchUrl);
		submit = browser.waitForElement(By.xpath("//*/button[contains(text(),'Submit')]"));
		submit.click();

		browser.makeScreenshot();
	}

	private void configureMhCampusApplication(String appName, String ltiLaunchUrl, canvasMappingType canvasmappingtype,
			boolean isDeepIntegration) throws Exception {
		Logger.info("Replacing URL in MhCampus application...");
		Element viewApp = browser.waitForElement(By.xpath("//*/a[contains(text(),'View App Configurations')]"));
		viewApp.click();

		WebElement app, editApp, edit, launchUrl, customFields, submit;
		String applikationName;

		Element tableOfUsers = browser.waitForElement(By.xpath("//*[@id='external-tools-table']/tbody"));
		List<WebElement> apps = tableOfUsers.findElements(By.tagName("tr"));

		for (int i = 0; i <= apps.size(); i++) {
			app = apps.get(i);
			applikationName = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[2]/span[1]")).getText();
			Logger.info("applikationName: " + applikationName);
			if (applikationName.equals(appName)) {
				Logger.info("Trying to replase: ");
				editApp = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[3]/div/a/i[2]"));
				editApp.click();
				edit = app.findElement(By.xpath("//tr[" + (i + 1) + "]/td[3]//*/a[contains(text(),'Edit')]"));
				edit.click();
				break;
			} else {
				if (i == apps.size()) {
					Logger.info("Was not faund applikation with name: " + appName);
					throw new Exception();
				}
			}
		}

		launchUrl = browser.waitForElement(By.xpath("//*/form/div[1]/div/div/div[3]/label/input"));
		launchUrl.clear();
		launchUrl.sendKeys(ltiLaunchUrl);
		Logger.info("Replacing custom ids parameters in MhCampus application...");

		customFields = browser.waitForElement(By.id("customFields"));
		customFields.clear();
		customFields.sendKeys("application_type=MHCampus");
		customFields.sendKeys(Keys.RETURN);
		customFields.sendKeys("custom_courseid_parameter=");
		customFields.sendKeys(properties.getProperty("di.canvas." + canvasmappingtype.name() + ".courseid"));
		customFields.sendKeys(Keys.RETURN);
		customFields.sendKeys("custom_userid_parameter=");
		customFields.sendKeys(properties.getProperty("di.canvas." + canvasmappingtype.name() + ".userid"));
		customFields.sendKeys(Keys.RETURN);
		if (isDeepIntegration) {
			customFields.sendKeys("tool_id=");
			customFields.sendKeys(toolId);
		}
		submit = browser.waitForElement(By.xpath("//*/button[contains(text(),'Submit')]"));
		submit.click();

		browser.makeScreenshot();
	}

	private void createMhCampusApplication(String appName, String key, String secret) throws Exception {
		Logger.info("Creating MhCampus application...");
		Element setting = browser.waitForElement(By.xpath("//*[@id='section-tabs']//*/a[contains(text(),'Settings')]"));
		setting.click();
		Element apps = browser.waitForElement(By.id("tab-tools-link"));
		apps.click();
		Element filter = browser.waitForElement(By.id("filterText"));
		filter.sendKeys(appName);
		Element application = browser.waitForElement(By.id("app_75"));
		application.click();
		Element addApp = browser.waitForElement(By.xpath("//a[contains(text(),'Add App')]"));
		addApp.click();
		browser.makeScreenshot();

		Logger.info("Adding the paramerers of MhCampus instance...");
		Element consumerKey = browser.waitForElement(By.name("consumer_key"));
		consumerKey.sendKeys(key);
		Element sharedSecret = browser.waitForElement(By.name("shared_secret"));
		sharedSecret.sendKeys(secret);
		Element add = browser.waitForElement(By.xpath("//button[contains(text(),'Add App')]"));
		add.click();
		// browser.pause(2000); //commented by AlexandrY start
		// browser.makeScreenshot();
		// if
		// (!browser.isElementPresent(By.xpath("//span[contains(text(),'McGraw
		// Hill Campus')]"))) {
		// Logger.info("MhCampus application was not added");
		// throw new Exception();
		// } //commented by AlexandrY end
		try { // added by AlexandrY start
			browser.waitForElement(By.xpath("//span[contains(text(),'McGraw Hill Campus')]"), false, 30);
		} catch (Exception e) {
			Logger.info("MhCampus application was not added");
			browser.makeScreenshot();
			throw e;
		} // added by AlexandrY end
		browser.navigate().refresh();
		browser.pause(2000);
	}

	private void createModule(String moduleName) {
		Logger.info("Creating module for MhCampus application...");
		Element modules = browser.waitForElement(By.xpath("//*/a[contains(text(),'Modules')]"));
		modules.click();
		browser.pause(1000);
		/*
		 * Element newModule = browser.waitForElement(By.xpath(
		 * "//*[@id='content']/div[1]/div/button"));
		 */
		Element newModule = browser.waitForElement(By.xpath(".//*[@id='content']/div[2]/div/div/button"));
		newModule.click();
		browser.pause(1000);
		Element name;
		try {
			name = browser.waitForElement(By.id("context_module_name"), 30);
		} catch (Exception e) {
			newModule.click();
			name = browser.waitForElement(By.id("context_module_name"), 30);
		}
		name.sendKeys(moduleName);
		Element addModule = browser.waitForElement(By.xpath("//*[@id='add_context_module_form']/div[2]/button[2]"));
		addModule.click();
		browser.pause(1000);

		Logger.info("Mark as Published...");
		Element modulePublished = browser.waitForElement(By.xpath("//*/div[@class='ig-header-admin']//*/i"));
		modulePublished.click();
		browser.makeScreenshot();
	}

	private void addApplicationToModule(String appName) {
		Logger.info("Adding application to module...");
		Element addApp = browser.waitForElement(By.xpath("//*/button [@class='add_module_item_link btn']"));
		addApp.click();
		Element externalTool = browser.waitForElement(By.xpath("//*/option[contains(text(),'External Tool')]"));
		externalTool.click();
		Element applicationLink = browser.waitForElement(By.xpath("//*/a[contains(text(),'" + appName + "')]"));
		applicationLink.click();
		Element addItem = browser.waitForElement(By.xpath("//*/button [@class='"
				+ "add_item_button btn btn-primary ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']"));
		addItem.click();
		browser.pause(1000);

		Logger.info("Mark as Published...");
		Element applicationPublished = browser.waitForElement(By.xpath("//*/div[@class='ig-admin']//*/i"));
		applicationPublished.click();
		browser.makeScreenshot();
	}

	private void createAppForConnect(String appName) {
		Logger.info("Create application for Connect");
		settingsBtn.click();
		Element apps = browser.waitForElement(By.id("tab-tools-link"));
		apps.click();
		Element viewApp = browser.waitForElement(By.xpath("//a[contains(text(),'View App Configurations')]"));
		viewApp.click();
		Element addApp = browser.waitForElement(By.xpath("//*[@id = 'external_tools']/div/div/div[1]/h2/div/span/a"));
		addApp.click();
		browser.makeScreenshot();
	}

	private void fillTheForm(String appName, String key, String secret, String xmlFileConfiguration) {
		Logger.info("Fill the form for application...");
		Element configurationType = browser.waitForElement(By.id("configuration_type_selector"));
		List<WebElement> options = configurationType.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals("Paste XML")) {
				Logger.info("Choose option: " + option.getText());
				option.click();
				break;
			}
		}

		Element name = browser.waitForElement(By.xpath("//*[@class = 'ConfigurationFormXml']/div[1]/label/input"));
		name.sendKeys(appName);
		Element consumerKey = browser
				.waitForElement(By.xpath("//*[@class = 'ConfigurationFormXml']/div[2]/div[1]/div[1]/label/input"));
		consumerKey.sendKeys(key);
		Element sharedSecret = browser
				.waitForElement(By.xpath("//*[@class = 'ConfigurationFormXml']/div[2]/div[2]/div[1]/label/input"));
		sharedSecret.sendKeys(secret);

		Element xmlConfiguration = browser.waitForElement(By.id("xml"));
		xmlConfiguration.clear();
		xmlConfiguration.sendKeys(xmlFileConfiguration);
		browser.makeScreenshot();
		Element submit = browser.waitForElement(By.xpath("//button[contains(text(),'Submit')]"));
		submit.click();
		browser.pause(5000);
		browser.makeScreenshot();
		browser.navigate().refresh();
	}

	private String GetScourceOfThePage(String appName, boolean role) {
		Logger.info("Begin with Connect");
		browser.navigate().refresh();
		browser.pause(2000);
		clickApplicationLink(appName);
		if (role) {
			browser.switchTo().defaultContent();
			browser.switchTo().frame(1);
			authorizeBtn.waitForPresence(10);
			Logger.info("Click on Authorize Button");
			authorizeBtn.click();
			browser.pause(2000);
			browser.makeScreenshot();
			Logger.info("Get Source of the page");
			String Source = browser.getPageSource();
			return Source;
		} else {
			browser.makeScreenshot();
			Logger.info("Get Source of the page");
			String Source = browser.getPageSource();
			return Source;
		}

	}

	private CanvasConnectCreateAccountScreen beginWithConnect(String appName) {
		Logger.info("Begin with Connect");
		clickApplicationLink(appName);
		authorizeBtn.waitForPresence(10);
		browser.pause(2000);
		authorizeBtn.click();
		browser.pause(10000);

		for (int i = 0; i < 10; i++) {
			try {
				browser.switchTo().defaultContent();
				clickApplicationLink(appName);
				browser.waitForElement((By.xpath("//a[contains(text(),'Begin')]")), 5);
				break;
			} catch (Exception ex) {
				Logger.info("NOT FOUND " + i);
			}
		}

		browser.makeScreenshot();
		begin.click();
		browser.pause(7000);
		browser.switchToLastWindow();
		CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen = browser
				.waitForPage(CanvasConnectCreateAccountScreen.class);
		browser.makeScreenshot();
		return canvasConnectCreateAccountScreen;
	}

	private Boolean isStartDatesFromTodayEqual(String dateFromInput) {
		Date date = new Date();
		StringBuilder today = new StringBuilder(date.toString());
		if (today.charAt(8) == '0') {
			today.deleteCharAt(8);
		}

		String monthAndDay = dateFromInput.substring(0, 6);
		if (today.toString().contains(monthAndDay)) {
			return true;
		} else {
			return false;
		}
	}

	private Boolean isDatesEqual(String ExpectedDate, String dateFromInput) throws Exception {

		SimpleDateFormat dateFormatForExpectedDate = new SimpleDateFormat("MM/dd/yyyy hh:mma");
		Date date1 = null;
		date1 = dateFormatForExpectedDate.parse(ExpectedDate);
		SimpleDateFormat dateFormatForDateFromInput = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mmaa");
		Date date2 = null;
		date2 = dateFormatForDateFromInput.parse(dateFromInput);

		if (date1.equals(date2)) {
			Logger.info("The dates are equal");
			return true;
		} else {
			Logger.info("The dates: " + date1 + " and: " + date2 + " don't equal");
			return false;
		}

	}


}
