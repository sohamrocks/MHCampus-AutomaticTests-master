/*package com.mcgraw.test.automation.tests.canvas;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityCourseDetailsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityIncludeAllFilter extends BaseTest {

	private static final String CANVAS_SUB_URL = "external_tools";
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;

	private String courseName1 = "CourseName" + courseRandom1;
	private String courseName2 = "CourseName" + courseRandom2;

	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityCourseDetailsScreen tegrityCourseDetailsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInCanvas();

		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceConnectorsScreen.configureCanvasAuthorizationConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin,
				canvasApplication.canvasSecureGateway);
		tegrityInstanceConnectorsScreen.configureCanvasSsoLinkConnectorWithIncludeFilter(canvasApplication.canvasTitle,
				canvasApplication.canvasFqdn, canvasApplication.canvasAccessToken, canvasApplication.canvasInterlinkType,
				canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin, canvasApplication.canvasSecureGateway);

		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptCurrentWithSubURL(CANVAS_SUB_URL);
		canvasApplication.logoutFromCanvas();
	}


	@Test(description = "For Canvas instructor Tegrity link is present for all courses")
	public void checkTegrityLinkIsPresentInInstructorsCourses() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for instructor's course "
				+ courseName1);
		canvasCourseDetailsScreen = canvasCourseDetailsScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for instructor's course "
				+ courseName2);
	}

	@Test(description = "For Canvas student Tegrity link is present for all courses")
	public void checkTegrityLinkIsPresentInStudentsCourses() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for student's course "
				+ courseName1);
		canvasCourseDetailsScreen = canvasCourseDetailsScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for student's course "
				+ courseName2);
	}

	@Test(description = "For Canvas instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourses" })
	public void checkTegrityLinkBahavesCorrectlyForInstructor() {

		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = canvasCourseDetailsScreen.clickTegrityLink();
		
		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), instructorName, "Greetin text is incorrect");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");		
	}

	@Test(description = "For Canvas student Tegrity link bahaves correctly", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourses" })
	public void checkTegrityLinkBahavesCorrectlyForStudent() {

		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = canvasCourseDetailsScreen.clickTegrityLink();

		Assert.verifyEquals(tegrityCourseDetailsScreen.getUserNameText(), studentName, "Greeting text is incorrect");
		Assert.verifyFalse(tegrityCourseDetailsScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isCoursePresent(courseName1), "Course is absent");
		Assert.verifyTrue(tegrityCourseDetailsScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Canvas instructor", dependsOnMethods = { "checkTegrityLinkIsPresentInInstructorsCourses" })
	public void checkTegrityWelcomePageForInstructor() throws Exception {
	
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = canvasCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), instructorName);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = instructorName + " " + "sandbox course";   
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");			
		Assert.verifyTrue(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");	
	}

	@Test(description = "Check Tegrity Welcome page for Canvas student", dependsOnMethods = { "checkTegrityLinkIsPresentInStudentsCourses" })
	public void checkTegrityWelcomePageForStudent() throws Exception {                       
		
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		tegrityCourseDetailsScreen = canvasCourseDetailsScreen.clickTegrityLink();
		tegrityIntroductionScreen = tegrityCourseDetailsScreen.goToCourses();

		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), studentName);				
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");	
		String sandboxCourse = "sandbox course";   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");		
		Assert.verifyFalse(tegrityIntroductionScreen.isStartRecordingButtonPresent(), "Start Recording button is present");
		Assert.verifyTrue(tegrityIntroductionScreen.isSearchOptionPresent(), "Search option is absent");
	}
	
	private void prepareTestDataInCanvas() throws Exception {

		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course1);
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course2);
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course2);
	}

	private void clearCanvasData() throws Exception {

		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if((instructorEnrollment1 != null) & (course1 != null))
		    canvasApiUtils.deleteEnrollment(instructorEnrollment1, course1);
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		
		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		if((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);
		
		if(student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}	
		if(instructor != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor);
			canvasApiUtils.deleteUser(instructorToDelete);
		}	
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}*/
