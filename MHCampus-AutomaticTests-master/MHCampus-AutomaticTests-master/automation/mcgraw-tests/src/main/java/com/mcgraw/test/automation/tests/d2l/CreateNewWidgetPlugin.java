//package com.mcgraw.test.automation.tests.d2l; 
//
//import org.apache.commons.lang.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import com.mcgraw.test.automation.tests.base.BaseTest;
//import com.mcgraw.test.automation.ui.applications.D2lApplicationV_10_7_5;
//
//
//public class CreateNewWidgetPlugin extends BaseTest {
//	
//	@Autowired
//	private D2lApplicationV_10_7_5 d2lApplication;
//
//	
//	private final String username = "mheducation.A1";
//	private final String password = "mheducation123";
//	
//	private final String name = getRandomString();
//	private final String lunchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";
//	private final String customerNumber = "1SMY-5MKV-GUQV";
//	private final String sharedSecret = "0356A2";
//	
//	
//
//	@BeforeClass
//	public void testSuiteSetup() throws Exception {
//
//	}
//
//	//@AfterClass(alwaysRun=true)
//	@AfterClass
//	public void testSuiteTearDown() throws Exception {
//
//	}
//
//	@Test(description = "Check TestScorableItem form is submitted successfully with SOType: New")
//	public void createNewWidgetPlugin() throws Exception {
////		d2lApplicationV_10_7_5.loginToD2lAsAdmin();
//		d2lApplication.loginToD2l(username, password);
//		d2lApplication.createNewWidgetPlugin(name ,lunchUrl, customerNumber, sharedSecret);
//
//	}
//	
//	private String getRandomString() {
//		return RandomStringUtils.randomAlphanumeric(5);
//	}
//
//}
