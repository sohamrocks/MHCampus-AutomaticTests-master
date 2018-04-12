package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.lti.LtiScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartStudentRegistrationScreen;

public class ExternalToolsApplication {

	@Value("${lti.baseurl}")
	public String ltiBaseUrl;

	@Value("${lti.launch.url}")
	public String ltiLaunchUrl;

	@Value("${lti.customparameters}")
	public String ltiCustomParameters;
	
	Browser browser;

	private LtiScreen ltiScreen;

	private static final String STUDENT_ROLE = "urn:lti:role:ims/lis/Learner";
	private static final String INSTRUCTOR_ROLE = "urn:lti:role:ims/lis/Instructor";
	private final String BOOK_ISBN = "mhcampus_isbn=0000011111";
	private final String CONNECT_PROVIDER = "mhcampus_provider=CONNECT";
	private final String ALEKS_PROVIDER = "mhcampus_provider=ALEKS";
	private final String LEARNSMART_PROVIDER = "mhcampus_provider=LEARNSMART";

	public ExternalToolsApplication(Browser browser) {
		this.browser = browser;
	}

	public void fillLtiForm(String customerNumber,
			String sharedSecret, String userId, String fullName, String email,
			String courseId, String courseTitle, String customParameters, String role) {
		browser.navigate().to(ltiBaseUrl);
		ltiScreen = browser.waitForPage(LtiScreen.class);
		ltiScreen.clickClearData();
		browser.clickOkInAlertIfPresent();
		ltiScreen.displayOptionalParameters();
		ltiScreen.typeLaunchUrl(ltiLaunchUrl);
		ltiScreen.typeCustomerKey(customerNumber);
		ltiScreen.typeSharedSecret(sharedSecret);
		ltiScreen.typeUserId(userId);
		ltiScreen.typeUserFullName(fullName);
		if(!email.isEmpty())
			ltiScreen.typeEmailAddress(email);
		ltiScreen.typeCourseId(courseId);
		ltiScreen.typeCourseTitle(courseTitle);
		ltiScreen.typeRoleOfUser(role);
		ltiScreen.typeCustomParameters(customParameters);
		browser.makeScreenshot();
		ltiScreen.clickSaveData();
		browser.makeScreenshot();
		ltiScreen.clickLaunchTP();	
	}
	
	// In the further must return MhCampusConnectScreen
	public void fillLtiFormAsInstructorForConnect(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String email, String courseId, String courseTitle) {
		
		String connectCustomParameters = ltiCustomParameters + "\n" + BOOK_ISBN + "\n" + CONNECT_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, email,
				courseId, courseTitle, connectCustomParameters, INSTRUCTOR_ROLE);
		browser.pause(5000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
	}
	
	// In the further must return MhCampusConnectScreen
	public void fillLtiFormAsStudentForConnect(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String email, String courseId, String courseTitle) {
		
		String connectCustomParameters = ltiCustomParameters + "\n" + BOOK_ISBN + "\n" + CONNECT_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, email,
				courseId, courseTitle, connectCustomParameters, STUDENT_ROLE);	
		browser.pause(5000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
	}
	
	public void fillLtiFormAsInstructorForAleks(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		
		String aleksCustomParameters = ltiCustomParameters + "\n" + ALEKS_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, "",
				courseId, courseTitle, aleksCustomParameters, INSTRUCTOR_ROLE);
		browser.pause(5000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
	}
	
	public void fillLtiFormAsStudentForAleks(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		
		String aleksCustomParameters = ltiCustomParameters + "\n" + ALEKS_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, "",
				courseId, courseTitle, aleksCustomParameters, STUDENT_ROLE);	
		browser.pause(5000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
	}
	
	public MhCampusLearnSmartScreenWithOutBar fillLtiFormAsInstructorForLearnSmart(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		
		String learnSmartCustomParameters = ltiCustomParameters + "\n" + BOOK_ISBN + "\n" + LEARNSMART_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, "",
				courseId, courseTitle, learnSmartCustomParameters, INSTRUCTOR_ROLE);
		
		browser.pause(30000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
		
		MhCampusLearnSmartScreenWithOutBar mhCampusLearnSmartScreen = browser.waitForPage(MhCampusLearnSmartScreenWithOutBar.class, 10);
		return mhCampusLearnSmartScreen;
	}
	
	public MhCampusLearnSmartStudentRegistrationScreen fillLtiFormAsStudentForLearnSmart(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		
		String learnSmartCustomParameters = ltiCustomParameters + "\n" + BOOK_ISBN + "\n" + LEARNSMART_PROVIDER;
		fillLtiForm(customerNumber, sharedSecret, userId, fullName, "",
				courseId, courseTitle, learnSmartCustomParameters, STUDENT_ROLE);

		browser.waitForAlert(10);
		browser.clickOkInAlertIfPresent();
		browser.pause(5000);
		browser.switchToLastWindow();	
		browser.makeScreenshot();
		
		MhCampusLearnSmartStudentRegistrationScreen mhCampusLearnSmartStudentRegistrationScreen =
				browser.waitForPage(MhCampusLearnSmartStudentRegistrationScreen.class, 10);
		return mhCampusLearnSmartStudentRegistrationScreen;
	}
	
	public Boolean isExpectedPageOpened(String expectedSubURL) {
		String curenrURL = browser.getCurrentUrl();
		if(curenrURL.contains(expectedSubURL))
				return true;
		return false;
	}
}
