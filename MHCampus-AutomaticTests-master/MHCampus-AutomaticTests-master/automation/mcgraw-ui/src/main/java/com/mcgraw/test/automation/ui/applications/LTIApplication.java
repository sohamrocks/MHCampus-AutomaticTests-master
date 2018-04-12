package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.lti.LtiScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;


public class LTIApplication {

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
	//private static final String OldOutcomeURL = "http://aairs-aws-qa.tegrity.com/sso/di/Lti1p1CompliantLms/Assignment.aspx";
	private static final String outcomeURL = "http://aairs-aws-qa.tegrity.com/sso/di/Lti1p1CompliantLms/LtiHandler.aspx";

	public LTIApplication(Browser browser) {
		this.browser = browser;
	}

	public MhCampusIntroductionScreen fillLtiForm(String customerNumber,
			String sharedSecret, String userId, String fullName,
			String courseId, String courseTitle, String role) {
		goToImsLtiTestPage();
		ltiScreen = browser.waitForPage(LtiScreen.class);
		ltiScreen.clickClearData();
		browser.clickOkInAlertIfPresent();
		fillFormHelper(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, role, ltiLaunchUrl,ltiCustomParameters);
		ltiScreen.clickSaveAndSubmit();
		return browser.waitForPage(MhCampusIntroductionScreen.class, 10);
	}
	
	//Added by Yuval to support LTI 1.1 tests
	public TestScoreScreen fillLtiForm(String customerNumber,
			String sharedSecret, String userId, String fullName,
			String courseId, String courseTitle, String role, String toolId, String assignmentId,String resultSourcedId) {
		goToImsLtiTestPage();
		ltiScreen = browser.waitForPage(LtiScreen.class);
		fillFormHelper(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, role,outcomeURL, toolId);
		ltiScreen.typeCustomParameters(toolId,assignmentId); // add assignmentId
		ltiScreen.typeResourceId(resultSourcedId);
		ltiScreen.typeResultSourcedId(resultSourcedId);
		ltiScreen.clickSaveAndSubmit();
		browser.switchToLastWindow();
		return browser.waitForPage(TestScoreScreen.class, 10);
	}
	
	//Added by Yuval to support LTI 1.1 tests
	public TestScoreItemsScreen fillLtiForm(String customerNumber,
			String sharedSecret, String userId, String fullName,
			String courseId, String courseTitle, String role, String toolId,String resourceId) {
		goToImsLtiTestPage();
		ltiScreen = browser.waitForPage(LtiScreen.class);
		fillFormHelper(customerNumber, sharedSecret, userId, fullName,
		courseId, courseTitle, role, outcomeURL,toolId);
		ltiScreen.typeResourceId(resourceId);
		//ltiScreen.clearResultSourcedId();
		ltiScreen.clickSaveAndSubmit();
		browser.switchToLastWindow();
		return browser.waitForPage(TestScoreItemsScreen.class, 10); // change to scorable item test page 
	}
	
	public void fillFormHelper(String customerNumber,
			String sharedSecret, String userId, String fullName,
			String courseId, String courseTitle, String role, String url,String customParameter){
		
		//ltiScreen.clickClearData();
		//browser.clickOkInAlertIfPresent();
		ltiScreen.displayOptionalParameters();
		ltiScreen.typeLaunchUrl(url);
		ltiScreen.typeCustomerKey(customerNumber);
		ltiScreen.typeSharedSecret(sharedSecret);
		ltiScreen.typeUserId(userId);
		ltiScreen.typeUserFullName(fullName);
		ltiScreen.typeCourseId(courseId);
		ltiScreen.typeCourseTitle(courseTitle);
		ltiScreen.typeRoleOfUser(role);
		ltiScreen.typeCustomParameters(customParameter);
		browser.makeScreenshot();
		
	}
	
	public MhCampusIntroductionScreen fillLtiFormAsStudent(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, STUDENT_ROLE);
	}
	//Added by Yuval for LTI 1.1 test	
	public TestScoreScreen fillLtiFormAsStudent(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle, String tool_id, String assignmentId,String resultSourcedId) {
			return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
					courseId, courseTitle, STUDENT_ROLE, tool_id, assignmentId,resultSourcedId);
	}
	//Added by Yuval for LTI 1.1 test	
	public TestScoreItemsScreen fillLtiFormAsStudent(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle, String tool_id,String resourceId) {
			return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
					courseId, courseTitle, STUDENT_ROLE, tool_id,resourceId);
	}
	

	public MhCampusIntroductionScreen fillLtiFormAsInstructor(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, INSTRUCTOR_ROLE);
	}
	//Added by Yuval for LTI 1.1 test
	public TestScoreItemsScreen fillLtiFormAsInstructor(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle, String tool_id, String resourceId) {
		return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, INSTRUCTOR_ROLE, tool_id, resourceId);
	}
	public TestScoreScreen fillLtiFormAsInstructor(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle, String tool_id, String assignmentId,String resultSourcedId) {
			return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
					courseId, courseTitle, INSTRUCTOR_ROLE, tool_id, assignmentId,resultSourcedId);
	}
	
	public LtiScreen goToImsGradebookPage(){
		browser.closeAllWindowsExceptFirst();
		browser.switchToFirstWindow();
		ltiScreen.clickGradeBookButton();
		browser.pause(3000);
		browser.makeScreenshot();
		
		return browser.waitForPage(LtiScreen.class,10);
	}
	
	public void moveToGradeBookFrame(){
		browser.switchTo().defaultContent();
		browser.switchTo().frame("TB_iframeContent");
	}
	private void goToImsLtiTestPage(){
		browser.navigate().to(ltiBaseUrl);
	}
}
