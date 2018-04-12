package com.mcgraw.test.automation.tests.d2l.configurations;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
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
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;


/**
 * LMS = D2L
 * DI: Enabled 
 * Course ID – User ID Mapping: None
 * Gradebook Connector: No
 * Gradebook Connector type : none
 * SSO Connector: No
 * Data Connector: No
 * Canvas Mapping: No
 * Instructor Level Token: On 
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 * Legacy: Off
 * Async: On
 */
public class CustomD2LConfiguration126 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private D2LApiUtils d2LApiUtils;
	
	@Autowired
	private RestApplication restApplication;
	
	private String customerNumber = "3STY-G7YO-REV0";
	private String sharedSecret = "FB828C";
//	private String institution = "CustomCanvasConfiguration126";
//	private String pageAddressFromEmail = "http://"+institution+".mhcampus.com";
	
	private String password = "123qweA@";
	private boolean checkBoxShowActiveCourse = false;
	//============ prepare user data deep ================
	private String studentRandom2 = getRandomString();
	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String studentSurname2 = "StudentSurname" + studentRandom2;	
	
	private String instructorRandom2 = getRandomString();
	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String instructorSurname2 = "InstructorSurame" + instructorRandom2;	
	
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId2;
	//============= D2l user data Deep =============
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS2;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS2;
	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;
	//============ data for creation assignment ====
	private String assignmentId = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	private String providerId = "Connect";
	private String description = " ";

	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;

	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);	

	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";
	private String tool_ID = "Connect";	


	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Enabled  ");
		Logger.info("Course ID – User ID Mapping: None | Gradebook Connector: Yes | Gradebook Connector type : none | SSO Connector: No | Data Connector: No | Canvas Mapping: No ");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: On | Async: Off");
		
		prepareDataInD2lDeep();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lDataDeep();
	}
	// Tests starts
	//================== Deep Integration ====================
	@Test(description = "SSO for instructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor2() throws Exception {
		
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		browser.pause(3000);
		d2lCourseDetailsScreen.clickOnContinueBtn();
		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
		
	}
	
	@Test(description = "SSO for student2", 
			dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {				
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);		
		
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");   		
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}
	//============= S C O R A B L E ============
	@Test(description = "Check TestScorableItem form with Module and Async is submitted successfully", 
			dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent2")
	   public void testScorableItemDeepAsync() throws Exception {
	    String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId, 
	      Integer.toString(courseOfferingRS2.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

	    TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithAsyncAndModuleId
	    		(customerNumber, providerId, d2LCourse2Encrypted, assignmentId, assignmentTitle, category, description,
	       startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
	       gradebookApplication.tegrityServiceLocation,tool_ID);
	    
	    gradebookApplication.getAsyncInScorableResult();
		browser.pause(2000);
	 
	    Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lAsync(), "TestScoreItems form submit failed");
	}
	   
	@Test(description = "Check TestScore form with Async is submitted successfully", dependsOnMethods = "testScorableItemDeepAsync")
		public void testScoreDeepAsync() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDIAsync(customerNumber, providerId,
				d2LCourse2Encrypted, assignmentId, d2LStudent2Encrypted, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation, tool_ID);
		gradebookApplication.getAsyncInScoreResult();
		browser.pause(2000);
					    
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lAsync(), "TestScore form submit failed");
	}
	   
	@Test(description = "Check Gradebook data related to the Instructor2", 
			dependsOnMethods = "testScoreDeepAsync")
	public void checkGradebookItemsAreCorrectForInstructorInCourse2Deep() throws Exception {
		
		// check scorable  item is exists
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
		
	    d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
	    d2LStudent2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRS2.getUserId())).getResult();

		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS2, courseOfferingRS2, D2LUserRole.INSTRUCTOR);
		
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();

		d2lHomeScreen =d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, launchUrl, customerNumber, sharedSecret, courseName2);		
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();		

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
		browser.pause(3000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		browser.pause(3000);
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}
