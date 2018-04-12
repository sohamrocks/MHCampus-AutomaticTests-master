package com.mcgraw.test.automation.tests.canvas;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
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
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;

public class TegrityCsvFilter extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	// For the local run is recommended change to: FULL_PATH_FILE = "C:\\Temp\\filter_canvas.csv";
	private static String FULL_PATH_FILE = "C:\\filter_canvas.csv";
	
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = getRandomString();
	private String courseRandom2 = getRandomString();
	private String courseRandom3 = getRandomString();
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;

	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseName2 = "CourseName2" + courseRandom2;
	private String courseName3 = "CourseName3" + courseRandom3;

	private String password = "123qweA@";
	
	private String fakeCourseId="1234683737643762387";
	private String fakeCourseId2="12346837376437623872";
	private String fakeCourseId3="123468373764376238723";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course1;
	private CanvasCourseRS course2;
	private CanvasCourseRS course3;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;
	private CanvasUserEnrollmentRS studentEnrollment3;
	private CanvasUserEnrollmentRS instructorEnrollment3;

	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareTestDataInCanvas();
		//added by Yuval 24.09.2017
		//tegrityInstanceApplication.createCsvFile(FULL_PATH_FILE, Integer.toString(course1.getId()));
		String[] courses = new String[]{fakeCourseId,Integer.toString(course1.getId()),fakeCourseId2,Integer.toString(course3.getId()),fakeCourseId3};
		tegrityInstanceApplication.createCsvFile(FULL_PATH_FILE, courses);
		
		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceConnectorsScreen.configureCanvasAuthorizationConnector(canvasApplication.canvasTitle, canvasApplication.canvasFqdn,
				canvasApplication.canvasAccessToken, canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin,
				canvasApplication.canvasSecureGateway);
		tegrityInstanceConnectorsScreen.configureCanvasSsoLinkConnectorWithCsvFilter(canvasApplication.canvasTitle,
				canvasApplication.canvasFqdn, canvasApplication.canvasAccessToken, canvasApplication.canvasInterlinkType,
				canvasApplication.canvasUserIdOrigin, canvasApplication.canvasCourseIdOrigin, canvasApplication.canvasSecureGateway,
				FULL_PATH_FILE);
		tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		tegrityInstanceConnectorsScreen.logOut();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@Test(description = "Verify the non-existant courses were deleted from the CSV")
	public void checkNonExistedCourseIsDeleted() {
		tegrityInstanceConnectorsScreen = tegrityInstanceApplication.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String coursesList = tegrityInstanceConnectorsScreen.getCoursesList();
		//boolean fakeCourseIdExist= coursesList.contains(fakeCourseId);
		Assert.verifyFalse(coursesList.contains(fakeCourseId), "Fake course ID was not deleted");
		Assert.verifyFalse(coursesList.contains(fakeCourseId2), "Fake course ID was not deleted");
		Assert.verifyFalse(coursesList.contains(fakeCourseId3), "Fake course ID was not deleted");
	}

	@Test(description = "For Canvas instructor Tegrity link is only present for CSV courses")
	public void checkTegrityLinkIsPresentInInstructorsCoursesAccordingToCsvFilter() {
		
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for instructor's course " + courseName1);
		canvasCourseDetailsScreen = canvasCourseDetailsScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 0, "Wrong count of Tegrity links for instructor's course " + courseName2);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName3);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of MH Campus links for instructor's course " + courseName3);
		canvasApplication.logoutFromCanvas();
	}

	@Test(description = "For Canvas student Tegrity link is only present for CSV courses")
	public void checkTegrityLinkIsPresentInStudentsCoursesAccordingToCsvFilter() {

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of Tegrity links for student's course " + courseName1);
		canvasCourseDetailsScreen = canvasCourseDetailsScreen.goToCreatedCourse(courseName2);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 0, "Wrong count of Tegrity links for student's course " + courseName2);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName3);
		Assert.verifyEquals(canvasCourseDetailsScreen.getTegrityLinkCount(), 1, "Wrong count of MH Campus links for instructor's course " + courseName3);
	    canvasApplication.logoutFromCanvas();
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
		course3 = canvasApiUtils.createPublishedCourse(courseName3);
		studentEnrollment3 = canvasApiUtils.enrollToCourseAsActiveStudent(student, course3);
		instructorEnrollment3 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course3);
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
}
