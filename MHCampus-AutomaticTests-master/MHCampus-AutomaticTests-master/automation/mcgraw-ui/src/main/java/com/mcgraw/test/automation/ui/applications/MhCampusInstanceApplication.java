package com.mcgraw.test.automation.ui.applications;

import java.io.FileWriter;
import java.io.IOException;

import org.testng.Assert;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceLoginScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceTermsOfUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.MhCampusCourseBlock;
import com.mcgraw.test.automation.ui.mhcampus.course.activesim.MhCampusActivSimScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectCourseSectionPairScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectSaveCourseSectionPair;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connectmath.MhCampusConnectMathReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connectmath.MhCampusConnectMathScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.gdp.MhCampusGDPPairingPortal;
import com.mcgraw.test.automation.ui.mhcampus.course.gdp.MhCampusGDPScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetScreen;

public class MhCampusInstanceApplication {

	public long LINKS_PROCESSING_TIMEOUT_MS = 20 * 60 * 1000; // 20 minutes
	public long CREATE_NEW_INSTANCE_TIMEOUT = 180 * 1000; // 3 minutes
	public long DIRECT_LOGIN_TIMEOUT = 60 * 1000; // 1 minute
	
	private MhCampusInstanceLoginScreen mhCampusInstanceLoginScreen;
	private MhCampusInstanceTermsOfUseScreen mhCampusInstanceTermsOfUseScreen;
	private MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private Browser browser;

	public MhCampusInstanceApplication(Browser browser) {
		this.browser = browser;
	}

	public MhCampusIntroductionScreen loginToMhCampusAsUser(String loginPageAddress, String institution, String login, String password) {

		Logger.info("Logging in to MH Campus instance: URL=" + loginPageAddress + " as user: username=" + login + ", password=" + password);
		browser.manage().deleteAllCookies();
		browser.pause(2000);	//AlexandrY added to fix instability
		try{
			mhCampusInstanceLoginScreen = browser.openScreen(loginPageAddress, MhCampusInstanceLoginScreen.class);
		}catch(Exception e){
			Logger.info("Try open 'Instance Login Screen' again...");
			mhCampusInstanceLoginScreen = browser.openScreen(loginPageAddress, MhCampusInstanceLoginScreen.class);
		}
		mhCampusIntroductionScreen = mhCampusInstanceLoginScreen.loginToCampusAsUser(institution, login, password);
		if( !(mhCampusIntroductionScreen.isWelcomeFrameOpened() || mhCampusIntroductionScreen.isIntroductionScreenOpened()) ){
			Logger.info("Try Login in to MH Campus instance again...");
			mhCampusInstanceLoginScreen = browser.openScreen(loginPageAddress, MhCampusInstanceLoginScreen.class);
			mhCampusIntroductionScreen = mhCampusInstanceLoginScreen.loginToCampusAsUser(institution, login, password);
		}	
		browser.pause(6000);
		return mhCampusIntroductionScreen;
	}

	public MhCampusInstanceConnectorsScreen loginToMhCampusInstanceAndAcceptTermsOfUse(String url, String institution, String user, String password){

		Logger.info("Logging in to MH Campus instance...");
		browser.manage().deleteAllCookies();
		browser.pause(2000);	//AlexandrY added to fix instability
		try{
			mhCampusInstanceLoginScreen = browser.openScreen(url, MhCampusInstanceLoginScreen.class);
			mhCampusInstanceTermsOfUseScreen = mhCampusInstanceLoginScreen.loginToCampusFirstTime(institution, user, password);
		}catch(Exception e){
			Logger.info("Try Login in to MH Campus instance again...");
			mhCampusInstanceLoginScreen = browser.openScreen(url, MhCampusInstanceLoginScreen.class);
			mhCampusInstanceTermsOfUseScreen = mhCampusInstanceLoginScreen.loginToCampusFirstTime(institution, user, password);
		}
		mhCampusInstanceDashboardScreen = mhCampusInstanceTermsOfUseScreen.acceptTheRules();
		return mhCampusInstanceDashboardScreen.clickManageAairs();
	}
	
	public MhCampusInstanceConnectorsScreen loginToMhCampusInstanceAsAdmin(String url, String institution, String user, String password){

		Logger.info("Logging in to MH Campus instance...");
		browser.manage().deleteAllCookies();
		browser.pause(2000);	//AlexandrY added to fix instability
		try{
			mhCampusInstanceLoginScreen = browser.openScreen(url, MhCampusInstanceLoginScreen.class);
			mhCampusInstanceDashboardScreen = mhCampusInstanceLoginScreen.loginToCampus(institution, user, password);
		}catch(Exception e){
			Logger.info("Try Login in to MH Campus instance again...");
			mhCampusInstanceLoginScreen = browser.openScreen(url, MhCampusInstanceLoginScreen.class);
			mhCampusInstanceDashboardScreen = mhCampusInstanceLoginScreen.loginToCampus(institution, user, password);
		}
		return mhCampusInstanceDashboardScreen.clickManageAairs();
	}

	public MhCampusCourseBlock findAndSelectBookForCourse(String courseName, String bookIsdn) {
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		Logger.info("Select book ISBN= " + bookIsdn + " within course name=" + courseName);
		mhCampusCourseBlock.setFindTextBookField(bookIsdn);
		return mhCampusCourseBlock.selectBookForcourse(bookIsdn);
	}

	public int findBooks(String courseName, String searchTerm) {
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		Logger.info("Searching book by searchterm = " + searchTerm + " within course name =" + courseName);
		mhCampusCourseBlock.setFindTextBookField(searchTerm);
		return mhCampusCourseBlock.searchAndGetResultsCount();
	}

	public MhCampusCourseBlock expandCourse(String courseName) {
		Logger.info("Expand course name=" + courseName);
		MhCampusIntroductionScreen mhCampusStartScreen = browser.waitForPage(MhCampusIntroductionScreen.class);
		MhCampusCourseBlock mhCampusCourseBlock = mhCampusStartScreen.expandCourseBlock(courseName);
		// pause to let the course be opened for screenshot
		browser.pause(2000);
		return mhCampusCourseBlock;
	}

	public MhCampusCourseBlock checkMhcampusCourseElementsNotPresent(String courseName, CourseBlockElement... buttons) {
		return checkMhcampusCourseElementsState(courseName, false, buttons);
	}

	public MhCampusCourseBlock checkMhcampusCourseElementsPresent(String courseName, CourseBlockElement... buttons) {
		return checkMhcampusCourseElementsState(courseName, true, buttons);
	}

	public MhCampusCourseBlock checkMhcampusCourseElementsState(String courseName, boolean isVisible, CourseBlockElement... elements) {
		String messageValue = (isVisible) ? "present" : "not present";
		if (elements.length == 0)
			throw new CommonTestRuntimeException("Specify elements to check in parameters");
		StringBuilder buttonNames = new StringBuilder();
		for (CourseBlockElement courseBlockElement : elements) {
			buttonNames.append(courseBlockElement.getValue()).append(", ");
		}
		buttonNames.replace(buttonNames.lastIndexOf(", "), buttonNames.lastIndexOf(", ") + 1, "");
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		Logger.info("Check elements " + messageValue + " " + buttonNames + " for course: " + courseName);
		browser.makeScreenshot();
		StringBuilder errors = new StringBuilder();
		for (CourseBlockElement element : elements) {
			if (!isVisible && mhCampusCourseBlock.isCourseBlockElementPresent(element)) {
				errors.append("Element [" + element.getValue() + "] present").append('\n');
			} else if (isVisible && !mhCampusCourseBlock.isCourseBlockElementPresent(element)) {
				errors.append("Element [" + element.getValue() + "] not present").append('\n');
			}
		}

		if (errors.length() > 0) {
			Assert.fail(errors.toString());
		}
		return mhCampusCourseBlock;
	}
	
	public void clickActivSimButtonAlreadyConfigured(String courseName) { 
		Logger.info("Perform steps to adopt ActivSim for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		mhCampusCourseBlock.clickActivSimButtonAlreadyConfigured();		
	}

	public void adoptActivSimForCourse(String courseName) {  
		Logger.info("Perform steps to adopt ActivSim for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusActivSimScreen mhCampusActivSimScreen = mhCampusCourseBlock.clickActivSimButton();
		Assert.assertTrue(mhCampusActivSimScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("ActiveSim"));
		mhCampusActivSimScreen.adoptActiveSim();
		browser.makeScreenshot();
	}
	
	public MhCampusConnectSaveCourseSectionPair adoptConnectForCourse(String courseName) {
		return adoptConnectForCourse(courseName, false);
	}

	public MhCampusConnectSaveCourseSectionPair adoptConnectForCourse(String courseName, boolean isSeveralProductsExists) {
		Logger.info("Perform steps to adopt Connect for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusConnectScreen mhCampusConnectScreen = mhCampusCourseBlock.clickConnectButton();
		Assert.assertFalse(mhCampusConnectScreen.isErrorMessagePresent(), "Error message 'We're sorry' is present");
		Assert.assertTrue(mhCampusConnectScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("Connect"));
		if (isSeveralProductsExists) {
			mhCampusConnectScreen.selectProduct();
		}
		MhCampusConnectCourseSectionPairScreen mhCampusConnectCourseSectionPairScreen = mhCampusConnectScreen.adoptConnect();
		MhCampusConnectSaveCourseSectionPair mhCampusConnectSaveCourseSectionPair = mhCampusConnectCourseSectionPairScreen
				.selectCourse(courseName);
		Assert.assertTrue(mhCampusConnectSaveCourseSectionPair.isSuccessMessagePresent(), "Sucess message is absent");
		return mhCampusConnectSaveCourseSectionPair;
	}

	public MhCampusConnectMathReadyToUseScreen adoptConnectMathForCourse(String courseName) {
		Logger.info("Perform steps to adopt Connect Math for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusConnectMathScreen mhCampusConnectMathScreen = mhCampusCourseBlock.clickConnectMathButton();
		Assert.assertTrue(mhCampusConnectMathScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("Connect Math"));
		MhCampusConnectMathReadyToUseScreen mhCampusConnectMathReadyToUseScreen = mhCampusConnectMathScreen.adoptConnectMath();
		browser.makeScreenshot();
		return mhCampusConnectMathReadyToUseScreen;
	}

	public MhCampusLearnSmartScreenWithOutBar adoptLearnSmartForCourse(String courseName) {
		Logger.info("Perform steps to adopt LearnSmart for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusLearnSmartScreen mhCampusLearnSmartScreen = mhCampusCourseBlock.clickLearnSmartButtonAsInstructor();
		Assert.assertTrue(mhCampusLearnSmartScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("LearnSmart"));
		MhCampusLearnSmartScreenWithOutBar mhCampusLearnSmartScreenWithOutBar = mhCampusLearnSmartScreen.adoptLearnSmart();
		browser.makeScreenshotUsingRobot();
		return mhCampusLearnSmartScreenWithOutBar;
	}
	
	public MhCampusALEKSReadyToUseScreen adoptALEKSForCourse(String courseName) {
		Logger.info("Perform steps to adopt ALEKS for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusALEKSScreen mhCampusALEKSScreen = mhCampusCourseBlock.clickAleksButton();
		Assert.assertTrue(mhCampusALEKSScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("ALEKS"));
		MhCampusALEKSReadyToUseScreen mhCampusALEKSReadyToUseScreen = mhCampusALEKSScreen.adoptALEKS();
		browser.makeScreenshot();
		return mhCampusALEKSReadyToUseScreen;
	}

	public MhCampusSimNetPairingPortalForInstructor adoptSimNetForCourse(String courseName) {
		Logger.info("Perform steps to adopt SimNet for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusSimNetScreen mhCampusSimNetScreen = mhCampusCourseBlock.clickSimNetButton();
		Assert.assertTrue(mhCampusSimNetScreen.getiWantThisForMyStudentsBlock().getYouAreCurrentlyInMessage().contains("SimNet"));
		MhCampusSimNetPairingPortalForInstructor mhCampusSimNetPairingPortalForInstructor = mhCampusSimNetScreen.adoptSimNet();
		browser.makeScreenshot();
		return mhCampusSimNetPairingPortalForInstructor;
	}

	public MhCampusGDPPairingPortal adoptGDPForCourse(String courseName) {
		Logger.info("Perform steps to adopt GDP for course " + courseName);
		MhCampusCourseBlock mhCampusCourseBlock = expandCourse(courseName);
		MhCampusGDPScreen mhCampusGDPScreen = mhCampusCourseBlock.clickGDPButton();
		MhCampusGDPPairingPortal mhCampusGDPPairingPortal = mhCampusGDPScreen.adoptGDP();
		browser.makeScreenshot();
		return mhCampusGDPPairingPortal;
	}

	public void createCsvFile(String filePath, String fileContent) {
		Logger.info("Creating CSV file containing the text: " + fileContent);
		FileWriter os;
		try {
			os = new FileWriter(filePath);
			os.write(fileContent);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createCsvFile(String filePath, String fileContent1, String fileContent2) {
		Logger.info("Creating CSV file containing the text: " + fileContent1 + " and: " + fileContent2);
		FileWriter os;
		try {
			os = new FileWriter(filePath);
			os.write(fileContent1);
			os.write(System.getProperty("line.separator"));
			os.write(fileContent2);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createCsvFile(String filePath, String []courses) {
		//Logger.info("Creating CSV file containing the text: " + fileContent1 + " and: " + fileContent2);
		Logger.info("Creating CSV file containing the text: ");
		for (int i=0;i<courses.length;i++){
			Logger.info(courses[i]);
		}
		FileWriter os;
		try {
			os = new FileWriter(filePath);
			for (int i=0;i<(courses.length)-1;i++){
				os.write(courses[i]);
				os.write(System.getProperty("line.separator"));
			}
			os.write(courses[(courses.length)-1]);
			//os.write(fileContent2);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
