/*package com.mcgraw.test.automation.tests.canvas.simnet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetCreateLinkScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetGetStudentCourtesyAccess;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairCourseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForStudent;

public class CustomCanvasSIMnetConfiguration extends BaseTest{
	
	
	@Autowired
	private CanvasApplication canvasApplication;
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasHomeScreen canvasHomeScreen;
	
	private MhCampusSimNetCreateLinkScreen mhCampusSimNetCreateLinkScreen;
	private MhCampusSimNetPairingPortalForInstructor mhCampusSimNetPairingPortalForInstructor;
	private MhCampusSimNetPairCourseScreen mhCampusSimNetPairCourseScreen;
	private MhCampusSimNetPairingPortalForStudent mhCampusSimNetPairingPortalForStudent;
	private MhCampusSimNetGetStudentCourtesyAccess mhCampusSimNetGetStudentCourtesyAccess;
	private MhCampusSimNetHomeScreen mhCampusSimNetHomeScreen;
	
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();
	
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	
	private String instructorLogin1 = "instructor" + instructorRandom;
	private String instructorName1 = "InstructorName" + instructorRandom;
	
	private String courseName1 = "CourseName1" + courseRandom;
	
	
	
	private String password = "123qweA@";
	private String courseWithName = "CourseName1xC1J9";
	
	private String ur = "stagingqal";
	private String l = "v";
	private String deepAppName = "SIMnet QA";
	
	private String sectionTitle = courseWithName+getRandomNumber();
	private String fullPathToFile;
	
	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;
	private CanvasUserEnrollmentRS studentEnrollment11;
	private CanvasUserEnrollmentRS instructorEnrollment11;
	private String fileName = "QA_DI_CartridgeSIMnet.xml";
	private String xmlFileConfiguration;
	
	private String customerNumber = "3CD8-5E32-4VCL";
	private String sharedSecret = "45B603";
	
	
	private String email = "simnetins"+getRandomNumber()+"@mail.com";
	private String email2 = "simnetins2"+getRandomNumber()+"@mail.com";
	
	@BeforeClass
	public void testSuiteSetup()  throws Exception{
		prepareTestDataInCanvas();
		
		xmlFileConfiguration = getFile();
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration,
				deepAppName);
		
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
		@Test(description = "Creating SIMnet link")
		public void createSIMnetLink() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusSimNetCreateLinkScreen = canvasCourseDetailsScreen.clickToSimNetLink();		
			mhCampusSimNetPairingPortalForInstructor = mhCampusSimNetCreateLinkScreen.CreateSimNetLink(ur,l);
			Assert.verifyTrue(mhCampusSimNetPairingPortalForInstructor.simnetLoginMessageIsPresent(),"text message in not present");
		}
		
		
		@Test(description = "Pair instructor", dependsOnMethods ={"createSIMnetLink"})
		public void pairInstructor() throws Exception {
			mhCampusSimNetPairCourseScreen= mhCampusSimNetPairingPortalForInstructor.pairInstructor(email, password);
			Assert.verifyTrue(mhCampusSimNetPairCourseScreen.pairYourClassMessageIsPresent(),"text message in not present");
			
		}
		
		
		@Test(description = "Pair SIMnet course", dependsOnMethods ={"pairInstructor"})
		public void pairSIMnetcourse() throws Exception {
			mhCampusSimNetPairCourseScreen.pairCourse(courseWithName,sectionTitle);
			Assert.verifyTrue(mhCampusSimNetPairCourseScreen.successfullPairedMessageIsPresent(),"text message in not present");
			mhCampusSimNetPairCourseScreen.clickContinueBtn();
			canvasApplication.logoutFromCanvas();
		}
		
		@Test(description = "Pair student", dependsOnMethods ={"pairSIMnetcourse"})
		public void pairStudent() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusSimNetPairingPortalForStudent = canvasCourseDetailsScreen.clickToSimNetLinkStudent();
			
			mhCampusSimNetPairingPortalForStudent.pairStudent(email2, password);
			Assert.verifyTrue(mhCampusSimNetPairingPortalForStudent.successfullyRegisteredTextIsPresent(),"text message in not present");
			mhCampusSimNetGetStudentCourtesyAccess = mhCampusSimNetPairingPortalForStudent.pressContinueToSIMnet();
			Assert.verifyTrue(mhCampusSimNetGetStudentCourtesyAccess.addProductTextIsPresent(),"add product text in not present");
				
		}
		
		
		@Test(description = "Get courtesy acces for student", dependsOnMethods ={"pairStudent"})
		public void getCourtesyAccess() throws Exception {
			mhCampusSimNetGetStudentCourtesyAccess.getCourtesyAccess();
			Assert.verifyTrue(mhCampusSimNetPairingPortalForStudent.EditUserInfoBtnIsPresent(),"edit user button is not present");
			canvasApplication.logoutFromCanvas();
				
		}
		
		@Test(description = "Course level launch as student", dependsOnMethods ={"getCourtesyAccess"})
		public void courseLevelLaunchAsStudent() throws Exception {
			canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin1, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
			mhCampusSimNetHomeScreen = canvasCourseDetailsScreen.clickToSimNetLinkStudentlevelLaunch();
			
			Assert.verifyTrue(mhCampusSimNetHomeScreen.simNetLogoIsPresent(),"SIMnet logo in not present");
			
		}
		
		
		
		
		private void prepareTestDataInCanvas() throws Exception {
			student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
			instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
			
			course1 = canvasApiUtils.createPublishedCourse(courseName1);
			

			studentEnrollment11 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);
			instructorEnrollment11 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);

			

		}
		
		private String getFile() throws Exception {

			Logger.info("Get file from resources folder...");
			fullPathToFile = canvasApplication.pathToFile + fileName;
			File file = new File(fullPathToFile);
			byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

			return new String(encoded, "UTF-8");
		}
		
		private String getRandomString() {
			return RandomStringUtils.randomAlphanumeric(6);
		}
		
		private String getRandomNumber() {
			return RandomStringUtils.randomNumeric(5);
		}
		
		private void clearCanvasData() throws Exception {

			if ((studentEnrollment11 != null) & (course1 != null))
				canvasApiUtils.deleteEnrollment(studentEnrollment11, course1);
			

			if ((instructorEnrollment11 != null) & (course1 != null))
				canvasApiUtils.deleteEnrollment(instructorEnrollment11, course1);
			

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

		*/