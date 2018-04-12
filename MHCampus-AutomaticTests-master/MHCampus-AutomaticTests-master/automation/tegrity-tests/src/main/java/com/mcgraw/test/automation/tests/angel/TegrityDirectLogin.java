package com.mcgraw.test.automation.tests.angel;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.service.AngelApiUtils;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.AngelApplication;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceLoginScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityDirectLogin extends BaseTest {

	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;

	@Autowired
	private AngelApplication angelApplication;

	@Autowired
	private AngelApiUtils angelApiUtils;

	private String password = "123qweA@";

	private AddUserModel instructor;
	private String instructorUserName;
	private String instructorFirstname;
	private String instructorLastname;

	private AddUserModel student;
	private String studentUserName;
	
	private String studentFirstname;
	private String studentLastname;

	private AddSectionModel course1;
	private String courseid1;
	private String courseName1;
	
	private AddSectionModel course2;
	private String courseid2;
	private String courseName2;	

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		angelApplication.completeTegritySetupWithAngel(tegrityInstanceApplication.sharedSecret, tegrityInstanceApplication.customerNumber);
		prepareTestDataInAngel();

		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureAngelAuthorizationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthorizationServiceUrl, angelApplication.angelAuthorizationExtendedProperties);
		tegrityInstanceConnectorsScreen.configureAngelAuthenticationConnector(angelApplication.angelTitle,
				angelApplication.angelAuthenticationServiceUrl, angelApplication.angelAuthenticationExtendedProperties);
		
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(courseid1 != null)
			angelApplication.deleteCourseWithEnrollUsers(courseid1);
		if(courseid2 != null)
			angelApplication.deleteCourseWithEnrollUsers(courseid2);
		if(instructorUserName != null)
			angelApplication.deleteUserFromAngel(instructorUserName);
		if(studentUserName != null)
			angelApplication.deleteUserFromAngel(studentUserName);
	}
	
	@Test(description = "Check connectors are availiable")
	public void checkAuthenticationAndAuthorizationConnectorsAreAvailable() throws InterruptedException {	
		
		tegrityInstanceConnectorsScreen = tegrityInstanceDashboardScreen.clickManageAairs();
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthenticationConnectorsAvailable());
		Assert.assertTrue(tegrityInstanceConnectorsScreen.isAuthorizationConnectorsAvailable());
		tegrityInstanceConnectorsScreen.logOut();
	}
	
	@Test(description = "For Angel instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForAngelInstructor() {
		int rerunCount=3;
		
		tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorUserName, password);
		
		for (int i = 0; i < rerunCount; i++){
			if(!tegrityIntroductionScreen.isCoursePresent(courseName1)){
				Logger.info(String.format("<%s> rerun loginToTegrityAsUser(instructorUserName, password)", i));
				browser.findElement(By.xpath(".//a[@id='SignOutLink']")).click();
				browser.waitForPage(TegrityInstanceLoginScreen.class, 10);
				browser.pause(3000);
				browser.makeScreenshot();
				tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorUserName, password);
				browser.pause(3000);
				browser.makeScreenshot();
			} else {
				break;
			}
		}

		String expectedUserName = (instructorFirstname + " " + instructorLastname);   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);		
		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = (instructorFirstname + " " + instructorLastname+ " " + "sandbox course");   
		if(!tegrityIntroductionScreen.isCoursePresent(sandboxCourse))
		{
			tegrityIntroductionScreen.logOut();
			tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(instructorUserName, password);
		}
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
	}

	@Test(description = "For Angel student Tegrity link bahaves correctly", dependsOnMethods = { "checkAuthenticationAndAuthorizationConnectorsAreAvailable" })
	public void checkDirectLoginForAngelStudent() {

		tegrityIntroductionScreen = tegrityInstanceApplication.loginToTegrityAsUser(studentUserName, password);
		
		String expectedUserName = (studentFirstname + " " + studentLastname);
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
		
		tegrityIntroductionScreen.logOut();
	}

	private void prepareTestDataInAngel() throws RestEndpointIOException, UnsupportedEncodingException {
		
		String instructorRandom = getRandomString(10);
		String student1Random = getRandomString(10);
		String courseRandom1 = getRandomString(5);
		String courseRandom2 = getRandomString(5);

		instructorUserName = "instructor" + instructorRandom;
		instructorFirstname = "instructorFirstname" + instructorRandom;
		instructorLastname = "instructorLastname" + instructorRandom;

		studentUserName = "student" + student1Random;
		studentFirstname = "studentFirstname" + student1Random;
		studentLastname = "studentLastname" + student1Random;

		courseid1 = "id" + courseRandom1;
		courseName1 = "courseName" + courseRandom1;		
		courseid2 = "id" + courseRandom2;
		courseName2 = "courseName" + courseRandom2;		

		student = angelApiUtils.createStudent(studentUserName, password, studentFirstname, studentLastname);
		instructor = angelApiUtils.createInstructor(instructorUserName, password, instructorFirstname, instructorLastname);
		course1 = angelApiUtils.createCourse(instructor, courseid1, courseName1);
		course2 = angelApiUtils.createCourse(instructor, courseid2, courseName2);
		angelApiUtils.enrolUserToCourse(student, course1, AngelSectionRole.STUDENT);
		angelApiUtils.enrolUserToCourse(student, course2, AngelSectionRole.STUDENT);
	}

	private String getRandomString(int count) {
		return RandomStringUtils.randomNumeric(count);
	}
}
