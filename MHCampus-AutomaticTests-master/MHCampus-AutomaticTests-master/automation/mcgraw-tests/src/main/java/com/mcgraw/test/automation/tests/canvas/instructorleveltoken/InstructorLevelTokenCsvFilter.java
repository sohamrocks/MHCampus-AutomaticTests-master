package com.mcgraw.test.automation.tests.canvas.instructorleveltoken;

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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class InstructorLevelTokenCsvFilter extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	// For the local run is recommended change to: FULL_PATH_FILE = "C:\\Temp\\tolken_filter_canvas.csv";
	private static String FULL_PATH_FILE = "C:\\Temp\\tolken_filter_canvas.csv";
	
	private String studentRandom = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();

	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;

	private String instructorLogin1 = "instructor1" + instructorRandom1;
	private String instructorName1 = "InstructorName1" + instructorRandom1;
	
	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName2" + instructorRandom2;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;

	private String password = "123qweA@";

	private CanvasUser student;
	private CanvasUser instructor1;
	private CanvasUser instructor2;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructor1Enrollment1;
	private CanvasUserEnrollmentRS instructor1Enrollment2;
	private CanvasUserEnrollmentRS instructor2Enrollment1;
	private CanvasUserEnrollmentRS instructor2Enrollment2;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;

	private static final String CANVAS_FRAME = "tool_content";

	private int numOfSlave = 3;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInCanvas();

		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		mhCampusInstanceApplication.createCsvFile(FULL_PATH_FILE, Integer.toString(course1.getId()), Integer.toString(course2.getId()));
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
			
		mhCampusInstanceConnectorsScreen.configureCanvasAuthorizationConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin,
				canvasApplication.canvasSecureGateway);
		mhCampusInstanceConnectorsScreen.configureCanvasSsoLinkConnectorWithCsvFilter(canvasApplication.canvasTitle,
				canvasApplication.canvasFqdn, canvasApplication.canvasAccessToken, canvasApplication.canvasInterlinkType,
				canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin, canvasApplication.canvasSecureGateway,
				FULL_PATH_FILE);
		MhCampusInstanceDashboardScreen mhCampusInstanceDashboardScreen = mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
		mhCampusInstanceDashboardScreen.useLevelTolkenInCanvas(true);
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "For FIRST Canvas instructor MH Campus link is present for created courses")
	public void checkMHCampusLinkPresentInCoursesForInstructor1() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName1);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName2);
	}
	
	@Test(description = "For SECOND Canvas instructor MH Campus link is present for created courses")
	public void checkMHCampusLinkPresentInCoursesForInstructor2() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName1);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName2);
	}
	
	@Test(description = "For Canvas student MH Campus link is present for created courses")
	public void checkMHCampusLinkPresentInCoursesForStudent() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName1);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getMhCampusLinkCount(), 1, 
				"Wrong count of MH Campus links for instructor's course " + courseName2);
	}
	
	@Test(description = "For Canvas student MH Campus link bahaves correctly", dependsOnMethods = 
		{ "checkMHCampusLinkPresentInCoursesForStudent", "checkAuthorizeNOTPresentForInstructor2Course1AfterInstructor1WasDeleted" })
	public void checkMHCampusLinkBahavesCorrectlyForStudent() {

		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), studentName.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, CANVAS_FRAME), "Course" + courseName2 + " is absent");
	}

	@Test(description = "For Canvas instructor1 and course1 ASK for Authorize Tolken and accept", dependsOnMethods = 
		{ "checkMHCampusLinkPresentInCoursesForInstructor1" })
	public void checkAuthorizePresentForInstructor1Course1() {

		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForCsvFilter(true);

		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, CANVAS_FRAME), "Course" + courseName2 + " is absent");
	}
	
	@Test(description = "For Canvas instructor1 and course2 DON'T ASK for Authorize Tolken", dependsOnMethods =
		{ "checkAuthorizePresentForInstructor1Course1" })
	public void checkAuthorizeNOTPresentForInstructor1Course2() {

		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName1.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, CANVAS_FRAME), "Course" + courseName2 + " is absent");
	}
	
	@Test(description = "For Canvas instructor2 and course1 DON'T ASK for Authorize Tolken", dependsOnMethods = 
		{ "checkAuthorizePresentForInstructor1Course1", "checkMHCampusLinkPresentInCoursesForInstructor2" })
	public void checkAuthorizeNOTPresentForInstructor2Course1() {

		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		mhCampusIntroductionScreen.acceptRules(CANVAS_FRAME);
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName2.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, CANVAS_FRAME), "Course" + courseName2 + " is absent");
	}
	
	@Test(description = "For Canvas instructor2 and course2 DON'T ASK for Authorize Tolken, after instructor1 was deleted", dependsOnMethods = 
		{ "checkAuthorizeNOTPresentForInstructor2Course1", "checkAuthorizeNOTPresentForInstructor1Course2" })
	public void checkAuthorizeNOTPresentForInstructor2Course1AfterInstructor1WasDeleted() throws Exception {

		// delete first instructor and enrollment to courses
		if((instructor1Enrollment1 != null) & (course1 != null))
		    canvasApiUtils.deleteEnrollment(instructor1Enrollment1, course1);		
		if((instructor1Enrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructor1Enrollment2, course2);
		
		CanvasUserRS teacherToDelete = new CanvasUserRS();
		teacherToDelete.setUser(instructor1);
		canvasApiUtils.deleteUser(teacherToDelete);
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLink();

		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(CANVAS_FRAME), instructorName2.toUpperCase(), "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, CANVAS_FRAME), "Course" + courseName1 + " is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, CANVAS_FRAME), "Course" + courseName2 + " is absent");
	}

	private void prepareTestDataInCanvas() throws Exception {

		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);
		
		course1 = canvasApiUtils.createPublishedCourse(courseName1);
		course2 = canvasApiUtils.createPublishedCourse(courseName2);
		
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course1);
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course2);		
		instructor1Enrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);	
		instructor1Enrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course2);		
		instructor2Enrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course1);	
		instructor2Enrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
	}

	private void clearCanvasData() throws Exception {

		if((studentEnrollment1 != null) & (course1 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment1, course1);
		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		
		if((instructor2Enrollment1 != null) & (course1 != null))
		    canvasApiUtils.deleteEnrollment(instructor2Enrollment1, course1);		
		if((instructor2Enrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructor2Enrollment2, course2);
		
		if(course1 != null)
			canvasApiUtils.deleteCourse(course1);
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);		
		if(student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}	
		
		if(instructor2 != null) {
			CanvasUserRS teacherToDelete = new CanvasUserRS();
			teacherToDelete.setUser(instructor2);
			canvasApiUtils.deleteUser(teacherToDelete);
		}	
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}