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
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen.canvasMappingType;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCreateAccountScreen;

/**
* LMS = Canvas
* DI: Enabled
* Course ID – User ID Mapping: Login – SIS
* Gradebook Connector: Yes
* SSO Connector: No
* Canvas Mapping: Yes
* Instructor Level Token: On
* Use Existing Assignments: On
* Fallback to user_id and context_id: On
* Async: Off
*/
public class CustomCanvasConfiguration77 extends BaseTest {
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	@Autowired
	private GradebookApplication gradebookApplication;
	
	@Autowired
	private RestApplication restApplication;
	
	private String password = "123qweA@";
		//for Classic integration
	private String studentRandom1 = getRandomString();
	private String instructorRandom1 = getRandomString();
	private String courseRandom1 = getRandomString();
	
	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	//private String studentSis1 = getRandomString();
	
	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	//private String instructorSis1 = getRandomString();
		
	private String courseName1 = "CourseName1" + courseRandom1;
	private String courseSis1 = getRandomString();
	
	private CanvasUser student1;
	private CanvasUser instructor1;
	private CanvasCourseRS course1;
	private CanvasUserEnrollmentRS studentEnrollment1;
	private CanvasUserEnrollmentRS instructorEnrollment1;
	//for Deep integration
	private String studentRandom2 = getRandomString();
	private String instructorRandom2 = getRandomString();
	private String courseRandom2 = getRandomString();
	
	private String studentLogin2 = "student2" + studentRandom2;
	private String studentName2 = "StudentName2" + studentRandom2;
	//private String studentSis2 = getRandomString();
	
	private String instructorLogin2 = "instructor2" + instructorRandom2;
	private String instructorName2 = "InstructorName2" + instructorRandom2;
	//private String instructorSis2 = getRandomString();
	
	private String courseName2 = "CourseName2" + courseRandom2;
	private String courseSis2 = getRandomString();
		
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
	private String assignmentId = getRandomNumber();
	private String assignmentIdExist = getRandomNumber();
	private String assignmentIdDeep = getRandomNumber();
	private String assignmentTitle = "title_" + getRandomString();
	private String assignmentTitleDeep = "title_" + getRandomString();
	private String category = "Test";
	private String description = "description_" + getRandomString();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String scoreType = "Percentage";
	private String scorePossible = "100";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	private String commentForStudent1 = "comment_" + getRandomString();
	private String commentForStudent2 = "comment_" + getRandomString();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
	// screen objects
	private CanvasHomeScreen canvasHomeScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private CanvasConnectCreateAccountScreen canvasConnectCreateAccountScreen;
	//
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	
	//private TestRestApi testRestApi;
	private CanvasAssignmentRS canvasAssignmentRS;
	private String xmlFileConfiguration;
	
	// other data
	//private String institutionName = "CustomCanvasConfiguration77";
	private String customerKey = "1Y85-W063-BCGC";
	private String customerNumber = customerKey;
	private String sharedSecret = "C3DA17";
		
	private String appName = "McGraw Hill Campus";
	private String moduleName = "MH Campus";
	private static final String CANVAS_FRAME = "tool_content";
	private String tool_ID = "Connect";	
	
	// this is for DI
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String deepAppName = "AConnectDI";
	
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {	

		Logger.info("Starting test for configuration:");
		Logger.info("LMS = Canvas | DI: Enabled ");
		Logger.info("Course ID – User ID Mapping: Login – SIS | Gradebook Connector: Yes | SSO Connector: NO | Canvas Mapping: Yes");
		Logger.info("* Instructor Level Token: On | Use Existing Assignments: On | Fallback to user_id and context_id: On | Async: Off ");

		prepareTestDataInCanvasClassic();
		prepareTestDataInCanvasDeep();		
		setupInCanvasClassic();		
		setupInCanvasDeep();		  
		
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasDataClassic();
		clearCanvasDataDeep();
	}
	
	@Test(description = "Check SSO to MHC as Instructor Classic",
			enabled = true)
	public void SSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		mhCampusIntroductionScreen = canvasCourseDetailsScreen.clickMhCampusLinkAndAcceptTolkenForLti(true);

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
	
	@Test(description = "Check TestScorableItem form is submitted successfully Classic", dependsOnMethods = {"SSOasStudent"},
			enabled = true)
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentId, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScoreItems form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitle did not match");

	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully Classic Exist", dependsOnMethods = {"checkSubmittingTestScorableItemFormIsSuccessfull"},
			enabled = true)
	public void checkSubmittingTestScorableItemFormIsSuccessfullExist() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentIdExist, assignmentTitle, category, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForCanvas(), "TestScorableItemsExist form submit failed");
		canvasAssignmentRS = canvasApiUtils.getCourseAssignmentByTitle(course1, assignmentTitle);
		Assert.verifyEquals(canvasAssignmentRS.getTitle(), assignmentTitle, "AssignmentTitleExist did not match");
	}
	
	@Test(description = "Check TestScore form is submitted successfully Classic Exist", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfullExist"},
			enabled = true)
	public void checkSubmittingTestScoreFormIsSuccessfullForStudentExist() throws Exception {
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId,
				course1.getSisCourseId(), assignmentIdExist, studentLogin1, commentForStudent1, dateSubmitted,
				scoreReceivedForStudent1, canvasApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForCanvas(), "TestScore + Exist form submit failed");
		List<CanvasSubmissionRS> canvasSubmissions = canvasApiUtils.getSubmissionAssignmentForUser(canvasAssignmentRS, student1);
		Assert.assertEquals(canvasSubmissions.size(), 1,
				"Student " + studentLogin1 + " can see the comment and Score received of");
		
	}		
/*	=============================================================
			DEEP INTEGRATION
	============================================================= */
	//   
	@Test(description = "Check Deep integration SSO to MHC as Instructor Deep", dependsOnMethods = {"checkSubmittingTestScoreFormIsSuccessfullForStudentExist"},
			enabled = true)
	public void DeepSSOasInstructor() throws Exception {
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin2, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName2);

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");
		
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

		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(deepAppName),"link doesn't present");
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
		//		"Student " + studentLogin2 + " can see the comment and Score received of");
		"Submission does not match");

	}
	
	private void prepareTestDataInCanvasClassic() throws Exception {
		student1 = canvasApiUtils.createUser(studentLogin1, password, studentName1);
		instructor1 = canvasApiUtils.createUser(instructorLogin1, password, instructorName1);
		
		course1 = canvasApiUtils.createPublishedCourseWithSis(courseName1, courseSis1);
		
		studentEnrollment1 = canvasApiUtils.enrollToCourseAsActiveStudent(student1, course1);		
		instructorEnrollment1 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor1, course1);
	}
	
	private void setupInCanvasClassic() throws Exception{

		canvasHomeScreen = canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin,
				canvasApplication.canvasAdminPassword);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourseAsAdmin(courseName1);
		canvasCourseDetailsScreen.createMhCampusApplication(appName, customerNumber, sharedSecret, canvasApplication.ltiLaunchUrl, canvasMappingType.LOGINTOSIS, true);
		canvasApplication.logoutFromCanvas();

		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin1, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName1);
		canvasCourseDetailsScreen.createModuleWithApplication(moduleName, appName);
		canvasApplication.logoutFromCanvas();	
		
	}
	
	private void prepareTestDataInCanvasDeep() throws Exception {
		
		student2 = canvasApiUtils.createUser(studentLogin2, password, studentName2);	
		instructor2 = canvasApiUtils.createUser(instructorLogin2, password, instructorName2);		
		course2 = canvasApiUtils.createPublishedCourseWithSis(courseName2, courseSis2);				
		studentEnrollment2 = canvasApiUtils.enrollToCourseAsActiveStudent(student2, course2);		
		instructorEnrollment2 = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor2, course2);
	}
	
	private void setupInCanvasDeep() throws Exception{
		// *this is not used in 
		//canvasInstructor2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(instructor2.getLoginId()).getResult();		
		canvasCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(course2.getSisCourseId()).getResult();				
		canvasStudent2Encrypted = restApplication
				//if configuration for user is SIS - use .getLoginId()
				//if configuration for user is Internal - use .getLoginId()
				//if configuration for user is Login - use .getId()
				.fillUnencryptedIdAndPressEncrypt(Integer.toString(student2.getId())).getResult(); //getId()
		
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


