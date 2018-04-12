package com.mcgraw.test.automation.tests.d2l.configurations;

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
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
 * LMS = D2L
 * DI: Enabled 
 * Course ID – User ID Mapping: None
 * Gradebook Connector: No
 * Gradebook Connector type : None
 * SSO Connector: No
 * Data Connector: No
 * Canvas Mapping: No
 * Instructor Level Token: On 
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 * Legacy: Off
 * Async: Off
 */
public class CustomD2LConfiguration99 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private D2LApiUtils d2LApiUtils;
	
	@Autowired
	private RestApplication restApplication;
	
	private String customerNumber = "24XH-7YS2-FPHU";
	private String sharedSecret = "7630C1";
	private String institution = "CustomCanvasConfiguration99";
	private String pageAddressFromEmail = "http://"+institution+".mhcampus.com";
	


	// prepare user data
	private String studentRandom1 = getRandomString();
	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	private String studentSurname1 = "StudentSurname" + studentRandom1;
	
	private String studentRandom2 = getRandomString();
	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String studentSurname2 = "StudentSurname" + studentRandom2;	

	private String instructorRandom1 = getRandomString();
	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	private String instructorSurname1 = "InstructorSurame" + instructorRandom1;
	
	private String instructorRandom2 = getRandomString();
	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String instructorSurname2 = "InstructorSurame" + instructorRandom2;	

	private String password = "123qweA@";

	private String courseName1 = "CourseName1" + getRandomString();
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId1;
	private String courseId2;
	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;
	

	// D2L user data
	private D2LUserRS studentRS1;
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS1;
	private D2LUserRS instructorRS2;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseOfferingRS courseOfferingRS2;

	// data for creation assignment
	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private String providerId = "Connect";//"provider_" + getRandomString();
	private String description = " ";
	
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;
	
	// ===================== Deep ===============
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditWidgetScreen d2lEditWidgetScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	//===========================================

	private static String linkName = "McGraw-Hill Campus";
	private static String D2L_FRAME;
	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private String tool_ID = "Connect";
	

	//================== deep ===============
	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: 95");
		Logger.info("LMS = D2L | DI: Enabled  ");
		Logger.info("Course ID – User ID Mapping: None | Gradebook Connector: No | Gradebook Connector type : none | SSO Connector: No | Data Connector: No | Canvas Mapping: No ");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: Off | Async: Off");
		
		prepareDataInD2lDeep();
		
		
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lDataDeep();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}	

	
	//================== Deep Integration ====================
      
	@Test(description = "For D2L instructor2 MH Campus link baheves correctly")//, dependsOnMethods = "checkGradebookItemsAreCorrectForInstructorInCourse1")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor2() throws Exception {
		
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
//		Assert.verifyTrue(d2lCourseDetailsScreen.isExistsPlugInErrorMessage(), "plugin is not shown as expected");//Deep
		d2lCourseDetailsScreen.clickOnContinueBtn();
		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
		
	}
	
	@Test(description = "For D2L student2 MH Campus link baheves correctly", 
			dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {				
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);		
//		Assert.verifyTrue(d2lCourseDetailsScreen.isExistsPlugInErrorMessage(), "plugin is not shown as expected");//Deep
		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");   		
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}
	//============= S C O R A B L E ============
	@Test(description = "Test scorableitem form success submit for DI", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent2")
	  public void testScorableItemSuccessSubmitDI() throws Exception {
	   String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId, 
			   Integer.toString(courseOfferingRS2.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

	   TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithModuleId(customerNumber,
		     providerId, d2LCourse2Encrypted, assignmentId, assignmentTitle, category, description,
		     startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
		     gradebookApplication.tegrityServiceLocation,tool_ID);
	
	
	   Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lDI(), "TestScoreItems form submit failed");
	  }
	  
	  @Test(description = "Test score form success submit for DI", dependsOnMethods = "testScorableItemSuccessSubmitDI")
	  public void testScoreSuccessSubmitDI() throws Exception {

	  TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
		     d2LCourse2Encrypted, assignmentId, d2LStudent2Encrypted, commentForStudent2, dateSubmitted,
		     scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation, tool_ID);
		  
	   Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lDI(), "TestScore form submit failed");
	   
	   d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
	    d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
	    d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
	    Assert.verifyEquals(
	      d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
	        studentSurname2 + ", " + studentName2),
	      scoreReceivedForStudent2, "ScoreReceived did not match");
	  }
	
	// Test Finish

	// private methods for tests
	
	private void prepareDataInD2lDeep() throws Exception {
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password, D2LUserRole.STUDENT);
		instructorRS2 = d2LApiUtils.createUser(instructorName2, instructorSurname2, instructorLogin2, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name2" + getRandomString(), "code2" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		
		//===============test stroke
	    //d2lApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
		
	    d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
	    d2LStudent2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRS2.getUserId())).getResult();

		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS2, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
		//======================================================================================
		d2lHomeScreen =d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, launchUrl, customerNumber, sharedSecret, courseName2);		
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();		
		//d2lEditWidgetScreen = d2lManageExternalToolsScreen.findAndClickOnWidgetByName(nameOfWidget);
		d2lApplication.bindingPluginToCourse(courseName2,customerNumber,sharedSecret, nameOfWidget);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(nameOfWidget);
		
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);

	}


	private void clearD2lDataDeep() throws Exception {

		if (studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if (instructorRS2 != null)
			d2LApiUtils.deleteUser(instructorRS2);
		if (courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if (courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
		d2lApplication.loginToD2lAsAdmin();
		browser.pause(2000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
