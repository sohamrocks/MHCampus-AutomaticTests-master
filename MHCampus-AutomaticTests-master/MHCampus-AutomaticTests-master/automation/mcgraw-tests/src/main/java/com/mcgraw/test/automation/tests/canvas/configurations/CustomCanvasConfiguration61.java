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
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;


/**
 * LMS = Canvas
 * DI: off
 * Course ID – User ID Mapping: sis – sis
 * Gradebook Connector: Yes
 * SSO Connector: NO
 * Canvas Mapping: Yes
 * Instructor Level Token: ON
 * Use Existing Assignments: Off
 * Fallback to user_id and context_id: Off
 */

public class CustomCanvasConfiguration61 extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;

	private String customerNumber = "15G8-OOCO-TEGE";
	private String sharedSecret = "730B47";
	private String password = "123qweA@";

	//for Classic integration
	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	private static final String CANVAS_FRAME = "tool_content";

	private String studentRandom1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String courseRandom1 = getRandomString();

	private String instructorLogin1 = "instructor1" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	private String instructorSis1 = "InstructorSis" + instructorRandom1;
	private String studentLogin1 = "student1" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	private String studendSis1 = "StudentSis" + studentRandom1;
	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseSis1 = "CourseSis" + courseRandom1;
	
	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
		
	// data for creating assignment
	private String providerId = "Connect";
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
	private String commentForStudent1 = "comment_1" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private CanvasAssignmentRS canvasAssignmentRS;

	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		prepareTestDataInCanvas();
		
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret, canvasApplication.ltiLaunchUrl , canvasMappingType.SISTOSIS , false);
		canvasApplication.logoutFromCanvas();
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();	
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Check SSO to MHC as Instructor with ILT Classic")
	public void checkSsoAsInstructorIltClassic() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(true);
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(CANVAS_FRAME));
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is absent");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check SSO to MHC as Student Classic", dependsOnMethods = { "checkSsoAsInstructorIltClassic" })
	public void checkSsoAsStudentClassic() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(false);
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(CANVAS_FRAME));
		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(CANVAS_FRAME), "Search option is present");
		canvasApplication.logoutFromCanvas();
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully Classic using Course SIS", dependsOnMethods = {"checkSsoAsStudentClassic"})
	public void checkSubmittingTestScorableItemFormIsSuccessfullClassic() throws Exception {
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");
	}

	@Test(description = "Check TestScore form is submitted successfully Classic using User SIS", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfullClassic" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentClassic() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentId, studendSis1, commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS,
				student1);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin1 + " can see the comment and Score received of");
	}
	
	private void prepareTestDataInCanvas() throws Exception {
		student1 = canvasApiUtils.createUserWithSis(studentLogin1, password, studentName1, studendSis1);
		instructor1 = canvasApiUtils.createUserWithSis(instructorLogin1, password, instructorName1, instructorSis1);
		course1 = canvasApiUtils.createPublishedCourseWithSis(courseName1, courseSis1);
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearCanvasData() throws Exception {

		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if((instructorEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
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