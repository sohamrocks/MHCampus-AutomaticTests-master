package com.mcgraw.test.automation.tests.canvas.configurations;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
* LMS = Canvas
* DI: Disabled
* Course ID – User ID Mapping: SIS - SIS
* Gradebook Connector: Yes
* SSO Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Async: On
*/

public class CustomCanvasConfiguration50 extends BaseTest {
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	private String password = "123qweA@";
		//for Classic integration
	private String studentRandom1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String courseRandom1 = getRandomString();
	
	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	private String studentSis1 = getRandomString();
	
	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	private String instructorSis1 = getRandomString();
	
	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseSis1 = getRandomString();
	
	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;

	// data for creating assignment	
	private String providerId = "Connect";//"provider_" + getRandomString();
	private String assignmentId = getRandomNumber();
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
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	//
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasAssignmentRS canvasAssignmentRS;
	
	// other data
	//private String institutionName = "CustomCanvasConfiguration50";
	private String customerKey = "MG72-9NEZ-BTZX";
	private String customerNumber = customerKey;
	private String sharedSecret = "ED1405";
		
	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	private static final String CANVAS_FRAME = "tool_content";
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {	

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Disabled ");
		Logger.info("Course ID – User ID Mapping: SIS - SIS | Gradebook Connector: Yes | SSO Connector: No | Canvas Mapping: No");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Async: On");

		prepareTestDataInCanvasClassic();	
		setupInCanvasClassic();				  
		
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasDataClassic();
	}
	
	@Test(description = "Check SSO to MHC as Instructor Classic",
			enabled = true)
	public void SSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false); // iLt is Off

		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(),
				"User name is incorrect");

		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
				"Course" + courseName1 + " is absent");

		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();

	}

	@Test(description = "Check SSO to MHC as Student Classic", dependsOnMethods = {"SSOasInstructor"} ,
			enabled = true)
	public void SSOasStudent() throws Exception {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false); // iLt is OFF
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME),"Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName1.toUpperCase(),
				"User name is incorrect");

		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME),
				"Course" + courseName1 + " is absent");

		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully with Async", dependsOnMethods = { "SSOasStudent" })
	public void checkSubmittingTestScorableItemFormIsSuccessfullAsync() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormAsync(customerNumber, providerId, 
				//if course is Internal use "Integer.toString(course1.getId())"
				//if course is SIS use "course1.getSisCourseId()"
				course1.getSisCourseId(), assignmentId, assignmentTitle, category, description, startDate,
				dueToDate, scoreType, scorePossible, isStudentViewable, isIncludedInGrade,
				canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.isResultContainsRequest(), 
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScorableResult();
		browser.pause(2000);
		
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed for TestScoreItems");		
		
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully with Async", dependsOnMethods = {
			"checkSubmittingTestScorableItemFormIsSuccessfullAsync" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentAsync() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormAsync(customerNumber, providerId,
				//if course is Internal use "Integer.toString(course1.getId())"
				//if course is SIS use "course1.getSisCourseId()"
				course1.getSisCourseId(), assignmentId, 
				//if configuration for user is SIS - use .getLoginId()
				//if configuration for user is Internal - use .getLoginId()
				//if configuration for user is Login - use .getId()
				student1.getLoginId(), commentForStudent1,
				dateSubmitted, scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.isResultContainsRequest(), 
				"TestScoreItems form submit was not successful, invalid requset created");
		gradebookApplication.getAsyncInScoreResult();
		browser.pause(2000);
		
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasAsync(), "getAsync failed for TestScore");
		
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student1);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin1 + " can see the comment and Score received of");
	}

	private void prepareTestDataInCanvasClassic() throws Exception {
		student1 = canvasApiUtils.createUserWithSis(studentLogin1, password, studentName1, studentSis1);
		instructor1 = canvasApiUtils.createUserWithSis(instructorLogin1, password, instructorName1, instructorSis1);
		
		course1 = canvasApiUtils.createPublishedCourseWithSis(courseName1, courseSis1);
		
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);		
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);
	}
	
	private void setupInCanvasClassic() throws Exception{

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
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearCanvasDataClassic() throws Exception {
		
		// deleting students enrollments
		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		
		//deleting instructors enrollments
		if((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		
		//deleting courses
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		
		if(student1 != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student1);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		
		if(instructor1 != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor1);
			canvasApiUtils.deleteUser(instructorToDelete);
		}				
	}
}




