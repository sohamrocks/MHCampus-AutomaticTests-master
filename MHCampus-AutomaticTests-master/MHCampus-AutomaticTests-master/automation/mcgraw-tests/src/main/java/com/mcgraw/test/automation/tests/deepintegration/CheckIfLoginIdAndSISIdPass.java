package com.mcgraw.test.automation.tests.deepintegration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.RandomStringUtils;
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
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class CheckIfLoginIdAndSISIdPass extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;
	//------------------With Sis_id------------------------------
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();
	
	private String appName = "AConnectDI";
	
	private String studentWithSisLogin = "student" + studentRandom;
	private String studentWithSisName = "StudentName" + studentRandom;
	private String StuSisiD = getRandomString();
	
	private String instructorWithSisLogin = "instructor" + instructorRandom;
	private String instructorWithSisName = "InstructorName" + instructorRandom;
	private String InsSisiD = getRandomString();
	
	private String courseWithSisName = "CourseName" + courseRandom;
	//------------------Without Sis_id--------------------------------
	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom2 = getRandomString();
	
	private String studentLogin = "student" + studentRandom2;
	private String studentName = "StudentName" + studentRandom2;
	
	private String instructorLogin = "instructor" + instructorRandom2;
	private String instructorName = "InstructorName" + instructorRandom2;
	
	private String courseName = "CourseName" + courseRandom2;

	private String password = "123qweA@";
	//-------------------First Users-------------------------
	private CanvasUser studentWithSis;
	private CanvasUser instructorWithSis;
	private CanvasCourseRS courseWithSis;
	private CanvasUserEnrollmentRS studentWithSisEnrollment;
	private CanvasUserEnrollmentRS instructorWithSisEnrollment;
	//------------------Second Users-------------------------
	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS instructorEnrollment;
	//------------------------------------------------------
	private String xmlFileConfiguration;
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String CustomerNumber = "KG1I-9I3Z-M7PU";
	static boolean Insrole = true;
	static boolean Sturole = false;
	private boolean GatewayPreviewMode = true;
	
	private int numOfSlave = 5;
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private InstanceCredentials instance;

		@BeforeClass
		public void testSuiteSetup() throws Exception {
		
			prepareTestDataInCanvas();
			xmlFileConfiguration = getFile();
		}
	
		@AfterClass
		public void testSuiteTearDown() throws Exception {
			tegrityAdministrationApplication.editSettingsInMhCampusInstanceGatewayPreviewMode(CustomerNumber, GatewayPreviewMode);
			clearCanvasData();
		}
//----------------------------------------------------Test user With Sis_id------------------------------------------------------------------------------------
		@Test(description = "Instructor Pass LoginId and SIS ID on all LTI Launches from Canvas" ,priority=1)  
		public void InstructorPassLoginIdandSISIdOnAllLTILaunchesFromCanvas() throws Exception {
			
			instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorWithSisLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
			canvasCourseDetailsScreen.createApplicationLink(instance.customerNumber, instance.sharedSecret, xmlFileConfiguration, appName);
			
			String pageSource = canvasCourseDetailsScreen.PrepereToTestWidgetWithGatewayPreviewMode(appName,Insrole);
			
			Assert.verifyTrue(pageSource.contains("value="+"\""+instructorWithSisLogin+"\""+" name=\"custom_mhc_login_id\" />"), "custom_mhc_login_id isn't correct");
			Assert.verifyTrue(pageSource.contains("value="+"\""+InsSisiD+"\""+" name=\"custom_mhc_sis_id\" />"), "custom_mhc_sis_id isn't correct");
			
			canvasApplication.logoutFromCanvas();
		}
		
		@Test(description = "Student Pass LoginId and SIS ID on all LTI Launches from Canvas",priority=3)
		public void StudentPassLoginIdandSISIdOnAllLTILaunchesFromCanvas() throws Exception {
			
			instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentWithSisLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseWithSisName);
			
			String pageSource = canvasCourseDetailsScreen.PrepereToTestWidgetWithGatewayPreviewMode(appName,Sturole);
			
			Assert.verifyTrue(pageSource.contains("value="+"\""+studentWithSisLogin+"\""+" name=\"custom_mhc_login_id\" />"), "custom_mhc_login_id isn't correct");
			Assert.verifyTrue(pageSource.contains("value="+"\""+StuSisiD+"\""+" name=\"custom_mhc_sis_id\" />"), "custom_mhc_sis_id isn't correct");
			
			canvasApplication.logoutFromCanvas();
		}
//----------------------------------------------------Test Users Without Sis_id-------------------------------------------------------------------------------	
		
		@Test(description = "Instructor Pass LoginId on all LTI Launches from Canvas",priority=2)
		public void InstructorPassLoginIdOnAllLTILaunchesFromCanvas() throws Exception {
			
			instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
			canvasCourseDetailsScreen.createApplicationLink(instance.customerNumber, instance.sharedSecret, xmlFileConfiguration, appName);
			
			String pageSource = canvasCourseDetailsScreen.PrepereToTestWidgetWithGatewayPreviewMode(appName,Insrole);
			
			Assert.verifyTrue(pageSource.contains("value="+"\""+instructorLogin+"\""+" name=\"custom_mhc_login_id\" />"), "custom_mhc_login_id isn't correct");
			Assert.verifyFalse(pageSource.contains("name=\"custom_mhc_sis_id\" />"), "custom_mhc_sis_id is Present");
			
			canvasApplication.logoutFromCanvas();
		}
		
		@Test(description = "Student Pass LoginId on all LTI Launches from Canvas",priority=4)
		public void StudentPassLoginIdOnAllLTILaunchesFromCanvas() throws Exception {
			
			instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
			canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
			canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
			
			String pageSource = canvasCourseDetailsScreen.PrepereToTestWidgetWithGatewayPreviewMode(appName,Sturole);
			
			Assert.verifyTrue(pageSource.contains("value="+"\""+studentLogin+"\""+" name=\"custom_mhc_login_id\" />"), "custom_mhc_login_id isn't correct");
			Assert.verifyFalse(pageSource.contains("name=\"custom_mhc_sis_id\" />"), "custom_mhc_sis_id is Present");
			
			canvasApplication.logoutFromCanvas();
		}
		
		
		private void prepareTestDataInCanvas() throws Exception {
			
			tegrityAdministrationApplication.editSettingsInMhCampusInstanceGatewayPreviewMode(CustomerNumber, GatewayPreviewMode);
			GatewayPreviewMode = false;
			//--------------------Create User With Sis_id-------------------------------------------------------
			studentWithSis = canvasApiUtils.createUserWithSis(studentWithSisLogin, password, studentWithSisName ,StuSisiD);
			instructorWithSis = canvasApiUtils.createUserWithSis(instructorWithSisLogin, password, instructorWithSisName ,InsSisiD);
			courseWithSis = canvasApiUtils.createPublishedCourse(courseWithSisName);
			studentWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(studentWithSis, courseWithSis);
			instructorWithSisEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructorWithSis, courseWithSis);
			//--------------------Create User Without Sis_id----------------------------------------------------
			student = canvasApiUtils.createUser(studentLogin, password, studentName);
			instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
			course = canvasApiUtils.createPublishedCourse(courseName);
			studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
			instructorEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);
			
		}
		
		private void clearCanvasData() throws Exception {

			if((studentWithSisEnrollment != null) & (courseWithSis != null))
				canvasApiUtils.deleteEnrollment(studentWithSisEnrollment, courseWithSis);
			if((instructorWithSisEnrollment != null) & (courseWithSis != null))
				canvasApiUtils.deleteEnrollment(instructorWithSisEnrollment, courseWithSis);
			if(courseWithSis != null)
				canvasApiUtils.deleteCourse(courseWithSis);
				
			if(studentWithSis != null){
				CanvasUserRS studentToDelete = new CanvasUserRS();
				studentToDelete.setUser(studentWithSis);
				canvasApiUtils.deleteUser(studentToDelete);
			}
			if(instructorWithSis != null){
				CanvasUserRS instructorToDelete = new CanvasUserRS();
				instructorToDelete.setUser(instructorWithSis);
				canvasApiUtils.deleteUser(instructorToDelete);
			}
			if((studentEnrollment != null) & (course != null))
				canvasApiUtils.deleteEnrollment(studentEnrollment, course);
			if((instructorEnrollment != null) & (course != null))
				canvasApiUtils.deleteEnrollment(instructorEnrollment, course);
			if(course != null)
				canvasApiUtils.deleteCourse(course);
				
			if(student != null){
				CanvasUserRS studentToDelete = new CanvasUserRS();
				studentToDelete.setUser(student);
				canvasApiUtils.deleteUser(studentToDelete);
			}
			if(instructor != null){
				CanvasUserRS instructorToDelete = new CanvasUserRS();
				instructorToDelete.setUser(instructor);
				canvasApiUtils.deleteUser(instructorToDelete);
			}
		}
		
		private String getFile() throws Exception {
			Logger.info("Get file from resources folder...");
			fullPathToFile = canvasApplication.pathToFile + fileName;
			File file = new File(fullPathToFile);
			byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			return new String(encoded, "UTF-8");		
		}

		private String getRandomString() {
			return RandomStringUtils.randomAlphanumeric(5);
		}
		
}
