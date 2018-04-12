package com.mcgraw.test.automation.tests.canvas.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasCourseRemoveEventRQ;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasDeleteRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasGradebookScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.BookForConnect;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentSuccessScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentTestPassingScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectAssignmentTestStartScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCourseConfigScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentCompleteRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectCourseSectionPairScreen;


public class CustomCanvasConfiguration144To150 extends BaseTest{
	
	@Autowired
	private CanvasApplication canvasApplication;
	
	private String customerNumber = "KG1I-9I3Z-M7PU";
	private String sharedSecret = "89405A";
	
	private String studentLogin = "automationstu1";
	private String instructorLogin = "automationins1";
	private String courseName = "Original course For automation";
	private String copiedCourseName = "Copied course For automation 1";
	
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasConnectCourseDetailsScreen canvasConnectCourseDetailsScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;
	private CanvasConnectCourseConfigScreen canvasConnectCourseConfigScreen;
	private MhCampusConnectCourseSectionPairScreen mhCampusConnectCourseSectionPairScreen;
	private CanvasConnectStudentCompleteRegistrationScreen canvasConnectStudentCompleteRegistrationScreen;
	private CanvasConnectStudentRegistrationScreen canvasConnectStudentRegistrationScreen;
	
	
	private String deepAppName = "Connect QA";

	private String password = "123456789";


	
	
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Check copy and publish copied course")
	public void copyCourse() throws InterruptedException {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName); 
		Boolean result = canvasCourseDetailsScreen.copyThisCourse(copiedCourseName);

		Assert.verifyTrue(result, "Course copy failed");
		Assert.assertTrue(canvasCourseDetailsScreen.publishCopiedCourse(), "Copied course is not published");
		
		canvasCourseDetailsScreen.enrollUserToCourseByLoginIdAsStudent(studentLogin);
		
		canvasCourseDetailsScreen.logout();
	}
	
	
	
	@Test(description = "Check Copy Connect section for copied course", dependsOnMethods ="copyCourse")
	public void copySectionInConnect() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		
		canvasCourseDetailsScreen.clickApplicationLink(deepAppName);
		
		String email = canvasCourseDetailsScreen.getUserEmail();
		
		canvasConnectCourseDetailsScreen = canvasCourseDetailsScreen.clickOnGoToMyConnectSection();
		canvasConnectCourseConfigScreen = canvasConnectCourseDetailsScreen.goToMyCoursesPage();
		canvasConnectCourseConfigScreen.checkAndClickOkOnTipsWindow();
		Boolean result = canvasConnectCourseConfigScreen.copySection(courseName, email, copiedCourseName);
		
		Assert.assertTrue(result, "Section copy failed");
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
	}
	
	@Test(description = "Check pairing copied course and copied section", dependsOnMethods ="copySectionInConnect")
	public void pairCopiedCourseAndCopiedSection() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.clickApplicationLink(deepAppName);
		canvasCourseDetailsScreen.checkAndAcceptAuthorizeIfNeed();
		mhCampusConnectCourseSectionPairScreen = canvasCourseDetailsScreen.clickOnPairWithAConnectSection();
		
		Boolean result = mhCampusConnectCourseSectionPairScreen.selectExistingCourseByName(copiedCourseName, courseName);
		Assert.assertTrue(result, "Pairing failed");
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
		
	}
	
	@Test(description = "check the assignment relinked", dependsOnMethods ="pairCopiedCourseAndCopiedSection")
	public void relinkAssignments() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.clickApplicationLink(deepAppName);
		canvasCourseDetailsScreen.clickOnRelinkAssignmentsFromCopiedCourse();
		canvasCourseDetailsScreen.clickSyncButton();
		
		Boolean result = canvasCourseDetailsScreen.isAssignmentRelinkedFromCopiedCourse(deepAppName);
		
		Assert.assertTrue(result, "Assignment is not relinked");
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
	}
	

	@Test(description = "check that the copied assignment launch for intructor", dependsOnMethods ="relinkAssignments")
	public void launchCopiedAssignmentAsInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Assert.assertTrue(canvasCourseDetailsScreen.launchAssignmentByNameAsInstructor("Assignment 1") instanceof CanvasConnectAssignmentScreen);		
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
	}
	
	
	@Test(description = "check that the copied assignment launch for student", dependsOnMethods ="launchCopiedAssignmentAsInstructor")
	public void launchCopiedAssignmentAsStudent() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Assert.assertTrue(canvasCourseDetailsScreen.launchAssignmentByNameAsStudent("Assignment 1", true) instanceof CanvasConnectAssignmentSuccessScreen);
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
		
	}
	
	@Test(description = "score copied assignment", dependsOnMethods ="launchCopiedAssignmentAsStudent")
	public void scoreCopiedAssignment() throws Exception {
		
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Screen screen = canvasCourseDetailsScreen.launchAssignmentByNameAsStudent("Assignment 1", false);
		Assert.assertTrue(screen instanceof CanvasConnectAssignmentTestStartScreen);
		CanvasConnectAssignmentTestStartScreen testStartScreen = (CanvasConnectAssignmentTestStartScreen)screen;
		screen = testStartScreen.clickStartTestButton();
		Assert.assertTrue(screen instanceof CanvasConnectAssignmentTestPassingScreen);
		CanvasConnectAssignmentTestPassingScreen testPassingScreen = (CanvasConnectAssignmentTestPassingScreen)screen;
		testPassingScreen.clickOptionInTest(0);
		testPassingScreen.clickSubmitButton();
		testPassingScreen.acceptSubmitting();
		testPassingScreen.clickViewResultsButton();
		browser.closeAllWindowsExceptFirst();
		CanvasGradebookScreen gradesScreen = canvasCourseDetailsScreen.clickGradesButton();
		Assert.assertTrue(gradesScreen.checkScoreGrid());
		
		browser.closeAllWindowsExceptFirst();
		canvasCourseDetailsScreen.logout();	
		
	}
	
	
	private void clearCanvasData() throws Exception {
				
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.clickApplicationLink(deepAppName);
		
		//Deleting Section
		canvasConnectCourseDetailsScreen = canvasCourseDetailsScreen.clickOnGoToMyConnectSection();
		canvasConnectCourseConfigScreen = canvasConnectCourseDetailsScreen.goToMyCoursesPage();
		canvasConnectCourseConfigScreen.checkAndClickOkOnTipsWindow();
		canvasConnectCourseConfigScreen.deleteLastCopiedCourseSection();
		
		browser.closeAllWindowsExceptFirst();
				
		//Deleting course
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(copiedCourseName);
		canvasCourseDetailsScreen.deleteThisCourse();
		
	}
	
}
