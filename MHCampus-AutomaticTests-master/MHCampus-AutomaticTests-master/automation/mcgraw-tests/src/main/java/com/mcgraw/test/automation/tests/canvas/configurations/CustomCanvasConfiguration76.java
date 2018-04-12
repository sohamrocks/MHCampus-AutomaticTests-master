package com.mcgraw.test.automation.tests.canvas.configurations;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.rest.TestRestApi;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;

import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;

/**
* LMS = Canvas
* DI: Disabled
* Course ID – User ID Mapping: SIS – SIS
* Gradebook Connector: Yes
* SSO Connector: No
* Canvas Mapping: Yes
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async: Off
*/
public class CustomCanvasConfiguration76 extends BaseTest{
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	RestApplication restApp;

	private String studentRandom1 = getRandomString();
	private String stuSisId1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String insSisId1 = getRandomString();

	private String studentRandom2 = getRandomString();
	private String stuSisId2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String insSisId2 = getRandomString();

	private String courseRandom1 = getRandomString();
	private String courseSisId1 = getRandomString();
	private String courseRandom2 = getRandomString();
	private String courseSisId2 = getRandomString();

	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;

	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;

	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;

	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;

	private String password = "123qweA@";

	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasUser student2;
	private CanvasUser instructor2;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;

	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;

	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;


	private String moduleName = "MH Campus";
	private String appName = "McGraw Hill Campus";

	private String providerId = "provider_" + getRandomString();
	private String assignmentId = getRandomString();
	private String assignmentIdExist = getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_2" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();

	private String customerNumber = "BSBB-ZBL7-E2EJ";
	private String sharedSecret = "A3B037";
	private CanvasAssignmentRS canvasAssignmentRS;

	private TestRestApi testRestApp;

	private String courseEncrypted;
	private String studentEncrypted;
	private String instructorEncrypted;

	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private final String CANVAS_FRAME = "tool_content";

	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String deepAppName = "AConnectDI";

	private String xmlFileConfiguration;

	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;
	
	private String commentForStudent = "comment_" + getRandomString();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();

	private String errorMsg = "Error: LTI_REQ_004 - Configuration setup is not validated";
	private String tool_ID = "Connect";
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration: CustomCanvasConfiguration76");
		Logger.info("LMS = Canvas | DI: disabled ");
		Logger.info("Course ID – User ID Mapping: SIS – SIS | Gradebook Connector: Yes | SSO Connector: No  | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: Off");
		
		prepareTestDataInCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret, canvasApplication.ltiLaunchUrl, canvasMappingType.SISTOSIS, false);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();

		
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	//GB(SIS-SIS)

		@Test(description = "Check SSO to MHC as Instructor")
		public void SSOasInstructor() throws Exception {
			
			canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
			
			Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
			
			mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
			
			Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(),
					"User name is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
					"Course" + courseName1 + " is absent");
			Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
			canvasApplication.logoutFromCanvas();
		}
		
		
		@Test(description = "Check SSO to MHC as Student", dependsOnMethods = { "SSOasInstructor" })
		public void SSOasStudent() throws Exception {
			
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
			
			Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
			
			mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
			
			Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName1.toUpperCase(),
					"User name is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
					"Course" + courseName1 + " is absent");
			Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
			canvasApplication.logoutFromCanvas();
		}
	
	
	@Test(description = "Test scorableitem form success submit", dependsOnMethods="SSOasStudent")
	public void testScorableItemSuccessSubmit() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber,
				providerId, course1.getSisCourseId(), assignmentId, assignmentTitle, category, description,
				startDate, dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");

	}
	
	@Test(description = "Test score form success submit", dependsOnMethods ="testScorableItemSuccessSubmit")
	public void testScoreSuccessSubmit() throws Exception {
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentId, stuSisId1, commentForStudent1,
				dateSubmitted, scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		Assert.assertTrue(canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1).size() == 1);

	}

	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private void prepareTestDataInCanvas() throws Exception {

		student1 = canvasApiUtils.createUserWithSis(studentLogin1, password, studentName1, stuSisId1);
		instructor1 = canvasApiUtils.createUserWithSis(instructorLogin1, password, instructorName1, insSisId1);


		course1 = canvasApiUtils.createPublishedCourseWithSis(courseName1, courseSisId1);

		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);

	}

	private void clearCanvasData() throws Exception {

		if ((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);

		if ((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);

		if (course1 != null)
			canvasApiUtils.deleteCourse(course1);

		if (student1 != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student1);
			canvasApiUtils.deleteUser(studentToDelete);
		}


		if (instructor1 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor1);
			canvasApiUtils.deleteUser(teacherToDelete);
		}

	}



}

