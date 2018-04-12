package com.mcgraw.test.automation.tests.d2l;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySSOlinkWithDataConnector extends BaseTest {
	
	@Autowired
	protected D2LApplication d2lApplication;

	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;
	
	TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;

	protected static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	protected static String linkName = "Tegrity Campus";

	private String studentRandom = getRandomString();
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorRandom = getRandomString();
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurame" + instructorRandom;

	private String password = "123qweA@";

	private String courseName1 = "CourseName" + getRandomString();
	protected String courseId1;
	private String courseName2 = "CourseName" + getRandomString();
	protected String courseId2;

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInD2l();

	    tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
	    tegrityInstanceConnectorsScreen.configureCustomAuthorizationConnector(d2lApplication.d2lDataTitle, d2lApplication.d2lDataServiceUrl, 
	    		         d2lApplication.d2lDataExtendedProperties.replace("<customer_number>", tegrityInstanceApplicationNoLocalConnector.customerNumber));
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Check Tegrity link is present for D2L instructor")
	public void checkTegrityLinkIsPresentInInstructorsCourse() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		Assert.assertEquals(d2lCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for instructor's course "
				+ courseName1);
	}

	@Test(description = "Check Tegrity link is present for D2L student")
	public void checkTegrityLinkIsPresentInStudentsCourse() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		Assert.assertEquals(d2lCourseDetailsScreen.getTegrityLinksCount(), 1, "Wrong count of Tegrity links for student's course "
				+ courseName1);
	}

	@Test(description = "For D2L instructor Tegrity link baheves correctly", dependsOnMethods = "checkTegrityLinkIsPresentInInstructorsCourse")
	public void checkTegrityLinkBehavesCorrectlyForD2LInstructor() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		
		String expectedGreetingText = instructorName + " " + instructorSurname;
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		  
	}
	
	@Test(description = "For D2L student Tegrity link baheves correctly", dependsOnMethods = "checkTegrityLinkIsPresentInStudentsCourse")
	public void checkTegrityLinkBehavesCorrectlyForD2LStudent() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		
		String expectedGreetingText = studentName + " " + studentSurname;

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), expectedGreetingText, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");				
	}

	@Test(description = "Check Tegrity Welcome page for Sakai instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityWelcomePageForInstructorForCourse1() throws Exception {
	
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorName + " " + instructorSurname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		String sandboxCourse = instructorName + " " + instructorSurname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}
	
	@Test(description = "Check Tegrity Welcome page for Sakai instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourse" })
	public void checkTegrityWelcomePageForInstructorForCourse2() throws Exception {
	
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = instructorName + " " + instructorSurname;   
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);	
			
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = instructorName + " " + instructorSurname+ " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");	
		
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for D2L student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityWelcomePageForStudentForCourse1() throws Exception {
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();
	
		String expectedUserName = studentName + " " + studentSurname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
	
	@Test(description = "Check Tegrity Welcome page for D2L student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourse" })
	public void checkTegrityWelcomePageForStudentForCourse2() throws Exception {
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		tegrityCourseDetailsScreen = d2lCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		String expectedUserName = studentName + " " + studentSurname;
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), expectedUserName);
				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");
		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
	
	protected void prepareDataInD2l() throws Exception {

		studentRS = d2LApiUtils.createUser(studentName, studentSurname, studentLogin, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		
		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS1, D2LUserRole.INSTRUCTOR);

		d2LApiUtils.createEnrollment(studentRS, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5).toUpperCase();
	}
	
	private void clearD2lData() throws Exception {
	
		if(studentRS != null)
			d2LApiUtils.deleteUser(studentRS);
		if(instructorRS != null)
			d2LApiUtils.deleteUser(instructorRS);
		if(courseOfferingRS1 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS1);
		if(courseTemplateRS1 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS1);
		if(courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if(courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
	}
}
