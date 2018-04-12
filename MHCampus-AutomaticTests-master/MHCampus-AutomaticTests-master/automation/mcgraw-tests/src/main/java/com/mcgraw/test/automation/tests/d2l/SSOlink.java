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
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class SSOlink extends BaseTest {

	@Autowired
	private D2LApplication d2lApplication;

	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private static String linkName = "McGraw-Hill Campus";

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
	private String courseId1;
	private String courseName2 = "CourseName" + getRandomString();

	private static String D2L_FRAME;

	@Autowired
	private D2LApiUtils d2LApiUtils;

	private D2LUserRS studentRS;
	private D2LUserRS instructorRS;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;

	private InstanceCredentials instance;
	
	private boolean checkBoxShowActiveCourse = false;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInD2l();

		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);
		
		if(d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();

		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId1, instance.customerNumber, instance.sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId1, linkName, moduleName);	
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		clearD2lData();
	}

	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}
	
	@Test(description = "Check MH Campus link is present for D2L instructor")
	public void checkMHCampusLinkIsPresentInInstructorsCourses() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course "
				+ courseName1);
	}

	@Test(description = "Check MH Campus link is present for D2L student")
	public void checkMHCampusLinkIsPresentInStudentsCourses() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course "
				+ courseName1);
	}

	@Test(description = "For D2L instructor MH Campus link baheves correctly", dependsOnMethods = "checkMHCampusLinkIsPresentInInstructorsCourses")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		if(! d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")){
			Assert.verifyTrue(mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(instance.pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		String expectedGreetingText = "Hi " + instructorName + " " + instructorSurname + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (instructorName + " " + instructorSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME), "Course " + courseName1 + " is absent");
		
//		TODO Uncomment the following line when the issue with presence of the 2nd course will be resolved
//		if(!checkBoxShowActiveCourse){
//			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, D2L_FRAME), "Course " + courseName2 + " is absent");
//		}
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is absent");
	}

	@Test(description = "For D2L student MH Campus link baheves correctly", dependsOnMethods = "checkMHCampusLinkIsPresentInStudentsCourses")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();
		
		String expectedGreetingText = "Hi " + studentName + " " + studentSurname + " -";		
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		if(! d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")){
			Assert.verifyTrue(mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(instance.pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (studentName + " " + studentSurname).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME), "Course " + courseName1 + " is absent");
		
//		TODO Uncomment the following line when the issue with presence of the 2nd course will be resolved
//		if(!checkBoxShowActiveCourse){
//			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, D2L_FRAME), "Course " + courseName2 + " is absent");
//		}
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is present");
	}

	private void prepareDataInD2l() throws Exception {

		studentRS = d2LApiUtils.createUser(studentName, studentSurname, studentLogin, password, D2LUserRole.STUDENT);
		instructorRS = d2LApiUtils.createUser(instructorName, instructorSurname, instructorLogin, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));

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
