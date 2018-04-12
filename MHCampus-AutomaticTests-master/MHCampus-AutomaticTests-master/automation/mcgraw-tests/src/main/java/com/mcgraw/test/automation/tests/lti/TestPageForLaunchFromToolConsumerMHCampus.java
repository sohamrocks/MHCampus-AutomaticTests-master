package com.mcgraw.test.automation.tests.lti;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.LTIApplication;
import com.mcgraw.test.automation.ui.applications.TegrityAdministrationApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.lti.LtiScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.service.LtiToolConsumerTestPageScreen;

public class TestPageForLaunchFromToolConsumerMHCampus extends BaseTest {

	@Autowired
	protected LTIApplication ltiApplication;
	private TegrityAdministrationApplication tegrityAdministrationApplication;
	private MhCampusInstanceDashboardScreen mhcampusInstanceDashboardScreen;
	private LtiToolConsumerTestPageScreen ltiConsumerTestPageScreen;

	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
//		prepareTestData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}
	
	
	

	
//	private void prepareTestData() {
//		studentLogin = "studentLogin_" + getRandomString();
//		studentFullName = "studentFullName_" + getRandomString();
//		instructorLogin = "instructorLogin" + getRandomString();
//		instructorFullName = "instructorFullName" + getRandomString();
//		courseId = "courseId_" + getRandomString();
//		courseName = "courseName_" + getRandomString();
//		resourceId = "resourceId_" + getRandomString(); // Assignment Id in outcome flow
//	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}

