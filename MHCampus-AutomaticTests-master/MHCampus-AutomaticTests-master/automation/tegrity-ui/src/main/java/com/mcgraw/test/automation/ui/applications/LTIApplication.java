package com.mcgraw.test.automation.ui.applications;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.lti.LtiScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;

public class LTIApplication {
	
	private static final String TEGRITY_TITLE = "Tegrity - ";
	private static final String POPUP_TITLE = "popup_blocker_msg";

	@Value("${lti.baseurl}")
	public String ltiBaseUrl;

	@Value("${lti.launch.url}")
	public String ltiLaunchUrl;

	@Value("${lti.customparameters}")
	public String ltiCustomParameters;
	
	@Value("${lti.title}")
	public String title;

	@Value("${lti.serviceurl}")
	public String serviceUrl;

	@Value("${lti.extendedproperties}")
	public String extendedProperties;
	
	Browser browser;

	private LtiScreen ltiScreen;

	private static final String STUDENT_ROLE = "urn:lti:role:ims/lis/Learner";
	private static final String INSTRUCTOR_ROLE = "urn:lti:role:ims/lis/Instructor";

	public LTIApplication(Browser browser) {
		this.browser = browser;
	}

	public TegrityCourseDetailsScreen fillLtiForm(String customerNumber,
			String sharedSecret, String userId, String fullName,
			String courseId, String courseTitle, String role) {
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
		ltiScreen.typeCourseId(courseId);
		ltiScreen.typeCourseTitle(courseTitle);
		ltiScreen.typeRoleOfUser(role);
		ltiScreen.typeCustomParameters(ltiCustomParameters);
		browser.makeScreenshot();
		ltiScreen.clickSaveData();
		browser.makeScreenshot();
		
		TegrityCourseDetailsScreen tegrityCourseDetailsScreen = null;
		try{
			ltiScreen.getLaunchTP();	
			ltiScreen.getLaunchTP().waitForPresence();
			browser.pause(6000);
			ltiScreen.clickLaunchTP();
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
	
	public TegrityCourseDetailsScreen fillLtiFormAsStudent(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, STUDENT_ROLE);
	}

	public TegrityCourseDetailsScreen fillLtiFormAsInstructor(
			String customerNumber, String sharedSecret, String userId,
			String fullName, String courseId, String courseTitle) {
		return fillLtiForm(customerNumber, sharedSecret, userId, fullName,
				courseId, courseTitle, INSTRUCTOR_ROLE);
	}
}