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
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;

/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping:  Internal - Internal
* Gradebook Connector: No
* SSO Connector: No
* Canvas Mapping: Yes
* Instructor Level Token: On
* Use Existing Assignments: Off
* Fallback to user_id and context_id: On
* Async: Off
*/
public class CustomCanvasConfiguration88 extends BaseTest {
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	@Autowired
	private RestApplication restApplication;
	
	private String password = "123qweA@";

	//for Deep integration
	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom2 = getRandomString();
	
	private String studentLogin2 = "student2" + studentRandom2;
	private String studentName2 = "StudentName2" + studentRandom2;
	
	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName2" + instructorRandom2;
	
	private String courseName2 = "CourseName2" + courseRandom2;
		
	private CanvasUser student2;	
	private CanvasUser instructor2;
	private CanvasCourseRS course2;
	private CanvasUserEnrollmentRS studentEnrollment2;
	private CanvasUserEnrollmentRS instructorEnrollment2;
	
	private String canvasCourse2Encrypted;
	//private String canvasInstructor2Encrypted;
	private String canvasStudent2Encrypted;
	// data for creating assignment	
	private String providerId = "Connect";//"provider_" + getRandomString();
	private String assignmentIdDeep = getRandomNumber();
	private String assignmentTitleDeep = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;
	//
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	
	//private TestRestApi testRestApi;
	private CanvasAssignmentRS canvasAssignmentRS;
	private String xmlFileConfiguration;
	
	// other data
	//private String institutionName = "CustomCanvasConfiguration88";
	private String customerKey = "75VF-GAIK-VB8E";
	private String customerNumber = customerKey;
	private String sharedSecret = "566A1B";	
	
	// this is for DI
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String deepAppName = "AConnectDI";
	private String tool_ID = "Connect";	
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {	

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID – User ID Mapping: Internal - Internal | Gradebook Connector: No | SSO Connector: No | Canvas Mapping: Yes");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: Off | Fallback to user_id and context_id: On | Async: Off ");

		prepareTestDataInCanvasDeep();			
		setupInCanvasDeep();		  
		
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasDataDeep();
	}	
	
/*	=============================================================
			DEEP INTEGRATION
	============================================================= */
	//   
	@Test(description = "Check Deep integration SSO to MHC as Instructor Deep",
			enabled = true)
	public void DeepSSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"Link doesn't present");
		
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, true); // iLt is ON
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();

		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
		
	}
	
	@Test(description = "Check Deep integration SSO to MHC as Student Deep", dependsOnMethods = {"DeepSSOasInstructor"},
			enabled = true)
	public void DeepSSOasStudent() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"Link doesn't present");
		canvasConnectCreateAccountScreen = canvasCourseDetailsScreen.ClickOnToolLink(deepAppName, false); // iLt is Off
		String errorMsg = canvasConnectCreateAccountScreen.getErrorMessageText();

		Assert.verifyEquals(canvasConnectCreateAccountScreen.getErrorMessageText(), errorMsg);
		canvasApplication.logoutFromCanvas();
	}
	// ============ Here is TestScoreableItem Form for DI ============
	@Test(description = "Check TestScorableItem form is submitted successfully Deep", dependsOnMethods = {"DeepSSOasStudent"},
			enabled = true)
	public void checkSubmittingTestScorableItemFormIsSuccessfullDeep() throws Exception {

			TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(customerNumber, providerId,
					canvasCourse2Encrypted, assignmentIdDeep, assignmentTitleDeep, category, description, startDate, dueToDate, scoreType,
					scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation, tool_ID);
			//   get module ID and
			//   Fill in module ID
			//   String moduleId= testScoreItemsScreen.getModuleID();

			Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScorableItem form failed");
			canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course2, assignmentTitleDeep);

			Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitleDeep, "AssignmentTitle did not match");

	}
	// ============ Here is TestScore Form for DI ============
	@Test(description = "Check TestScore form is submitted successfully Deep", dependsOnMethods = {"checkSubmittingTestScorableItemFormIsSuccessfullDeep"},
			enabled = true)
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentDeep() throws Exception {
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				canvasCourse2Encrypted, assignmentIdDeep, canvasStudent2Encrypted, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, canvasApplication.tegrityServiceLocation, tool_ID);
		//Fill in module ID
		//Click submit
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvasDI(), "TestScore form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student2);
		Assert.assertEquals(canvasSubmissions.size(), 1,
		"Submission does not match");

	}
	
	private void prepareTestDataInCanvasDeep() throws Exception {
		
		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);	
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);		
		course2 = canvasApiUtils.createPublishedCourse(courseName2);				
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);		
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
	}
	
	private void setupInCanvasDeep() throws Exception{
		// *this is not used in 
		//canvasInstructor2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(instructor2.getLoginId()).getResult();		
		canvasCourse2Encrypted = restApplication
				//if configuration for course is Internal - use .getId()
				//if configuration for course is SIS - use .getSisCourseId()
				.fillUnencryptedIdAndPressEncrypt(Integer.toString(course2.getId())).getResult();				
		canvasStudent2Encrypted = restApplication
				//if configuration for user is SIS - use .getLoginId()
				//if configuration for user is Internal - use .getLoginId()
				//if configuration for user is Login - use .getId()
				.fillUnencryptedIdAndPressEncrypt(student2.getLoginId()).getResult();
		
		xmlFileConfiguration = getFile();
		
		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName2);
		canvasCourseDetailsScreen.createApplicationLink(customerNumber, sharedSecret, xmlFileConfiguration, deepAppName);
		canvasApplication.logoutFromCanvas();
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

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private void clearCanvasDataDeep() throws Exception {
		
		// deleting students enrollments
		if((studentEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment2, course2);
		
		//deleting instructors enrollments
		if((instructorEnrollment2 != null) & (course2 != null))
			canvasApiUtils.deleteEnrollment(instructorEnrollment2, course2);
		
		//deleting courses
		if(course2 != null)
			canvasApiUtils.deleteCourse(course2);
		
		if(student2 != null){
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student2);
			canvasApiUtils.deleteUser(studentToDelete);
		}
		
		if(instructor2 != null){
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(instructor2);
			canvasApiUtils.deleteUser(instructorToDelete);
		}
		
	}
}


