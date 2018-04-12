package com.mcgraw.test.automation.tests.d2l.configurations;
/**
* LMS = D2L
* DI: Enabled 
* Course ID – User ID Mapping: None
* Gradebook Connector: Yes
* Gradebook Connector type : lti + exist
* SSO Connector: No
* Data Connector: Yes
* Canvas Mapping: No
* Instructor Level Token: Off 
* Use Existing Assignments: On
* Fallback to user_id and context_id: Off
* Legacy: On
* Async: Off
*/

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseOfferingRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCourseTemplateRS;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LUserRS;
import com.mcgraw.test.automation.api.rest.d2l.service.D2LApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.RestApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesDetailsScreen;

public class CustomD2LConfiguration109 extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private D2LApplication d2lApplication;

	@Autowired
	private D2LApiUtils d2LApiUtils;
	
	@Autowired
	private RestApplication restApplication;
	
	private String customerNumber = "2TPJ-ZPUP-OE3R";
	private String sharedSecret = "BC9476";
	private String institution = "CustomCanvasConfiguration109";
	private String pageAddressFromEmail = "http://"+institution+".mhcampus.com";

	// prepare user data
	private String studentRandom1 = getRandomString();
	private String studentLogin1 = "student" + studentRandom1;
	private String studentName1 = "StudentName" + studentRandom1;
	private String studentSurname1 = "StudentSurname" + studentRandom1;
	
	private String studentRandom2 = getRandomString();
	private String studentLogin2 = "student" + studentRandom2;
	private String studentName2 = "StudentName" + studentRandom2;
	private String studentSurname2 = "StudentSurname" + studentRandom2;	

	private String instructorRandom1 = getRandomString();
	private String instructorLogin1 = "instructor" + instructorRandom1;
	private String instructorName1 = "InstructorName" + instructorRandom1;
	private String instructorSurname1 = "InstructorSurame" + instructorRandom1;
	
	private String instructorRandom2 = getRandomString();
	private String instructorLogin2 = "instructor" + instructorRandom2;
	private String instructorName2 = "InstructorName" + instructorRandom2;
	private String instructorSurname2 = "InstructorSurame" + instructorRandom2;	

	private String password = "123qweA@";

	private String courseName1 = "CourseName1" + getRandomString();
	private String courseName2 = "CourseName2" + getRandomString();
	private String courseId1;
	private String courseId2;
	private String d2LCourse2Encrypted;
	private String d2LStudent2Encrypted;
	

	// D2L user data
	private D2LUserRS studentRS1;
	private D2LUserRS studentRS2;
	private D2LUserRS instructorRS1;
	private D2LUserRS instructorRS2;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseTemplateRS courseTemplateRS2;
	private D2LCourseOfferingRS courseOfferingRS1;
	private D2LCourseOfferingRS courseOfferingRS2;

	// data for creation assignment
	private String assignmentId = getRandomNumber();
	private String assignmentIdExist = getRandomNumber();
	private String assignmentIdDeep = getRandomNumber();
	
	private String assignmentTitle = "title_" + getRandomString();
	private String category = "category_" + getRandomString();
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
	private String providerId = "Connect";
	private String description = " ";
	
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private MhCampusIntroductionScreen mhCampusIntroductionScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;
	private D2lGradesDetailsScreen d2lGradesScreen;
	
	// ===================== Deep ===============
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditWidgetScreen d2lEditWidgetScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;

	private static String linkName = "McGraw-Hill Campus";
	private static String D2L_FRAME;
	private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
	private String tool_ID = "Connect";	
	private boolean checkBoxShowActiveCourse = false;

	//================== deep ===============
	private String nameOfWidget = "MHE_PQA_" + getRandomString();
	private String launchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("Starting test for configuration:");
		Logger.info("LMS = D2L | DI: Enabled  ");
		Logger.info("Course ID – User ID Mapping: None | Gradebook Connector: Yes | Gradebook Connector type : lti + exist | SSO Connector: No | Data Connector: Yes | Canvas Mapping: Yes ");
		Logger.info("* Instructor Level Token: Off | Use Existing Assignments: On | Fallback to user_id and context_id: Off | Legacy: On | Async: Off");
		
		prepareDataInD2lClassic();
		prepareDataInD2lDeep();
	}

	// @AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lDataClassic();
		clearD2lDataDeep();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}	
	// Tests Start
	// CLASSIC	
	@Test(description = "For D2L instructor1 MH Campus link baheves correctly")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor1() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for instructor's course " + courseName1);
		
		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		if (!d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")) {
			Assert.verifyTrue(
					mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		String expectedGreetingText = "Hi " + instructorName1 + " " + instructorSurname1 + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (instructorName1 + " " + instructorSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME),
				"Course " + courseName1 + " is absent");

		// TODO Uncomment the following line when the issue with presence of the
		// 2nd course will be resolved
		// if(!checkBoxShowActiveCourse){
		// Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2,
		// D2L_FRAME), "Course " + courseName2 + " is absent");
		// }
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is absent");
		browser.closeAllWindowsExceptFirst();
	    //browser.switchTo().defaultContent();
	    d2lApplication.d2lLogout(d2lHomeScreen);
	
	}

	@Test(description = "For D2L student1 MH Campus link baheves correctly", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor1")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent1() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lContentCourseScreen.chooseModuleBlock(moduleName);
		
		Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1,
				"Wrong count of MH Campus links for student's course " + courseName1);
		
		mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + studentName1 + " " + studentSurname1 + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		if (!d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")) {
			Assert.verifyTrue(
					mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressFromEmail.toLowerCase()),
					"The address of frame is not contain instance login page address");
		}

		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(D2L_FRAME), "Rules text is incorrect");

		mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

		String expectedUserName = (studentName1 + " " + studentSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName,
				"User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME),
				"Course " + courseName1 + " is absent");

		// TODO Uncomment the following line when the issue with presence of the
		// 2nd course will be resolved
		// if(!checkBoxShowActiveCourse){
		// Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2,
		// D2L_FRAME), "Course " + courseName2 + " is absent");
		// }
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is present");
		browser.closeAllWindowsExceptFirst();
	    //browser.switchTo().defaultContent();
	    d2lApplication.d2lLogout(d2lHomeScreen);
	}
	
	@Test(description = "Check TestScorableItem form is submitted successfully" , dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent1")
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId, Integer.toString(courseOfferingRS1.getId()) ,
				assignmentId, assignmentTitle, category, "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}
	
	@Test(description = "Check TestScorableItem Exist form is submitted successfully" , dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfull")
	public void checkSubmittingTestScorableItemFormIsSuccessfullExist() throws Exception {
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId, Integer.toString(courseOfferingRS1.getId()) ,
				assignmentIdExist, assignmentTitle, category, "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
				isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore Exist form is submitted successfully", dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfullExist")
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId, Integer.toString(courseOfferingRS1.getId()), assignmentIdExist,
				Integer.toString(studentRS1.getUserId()) , commentForStudent1, dateSubmitted, scoreReceivedForStudent1, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2l(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Gradebook data related to the Instructor1", 
			dependsOnMethods = "checkSubmittingTestScoreFormIsSuccessfullForStudent1")
	public void checkGradebookItemsAreCorrectForInstructorInCourse1() throws Exception {

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		// check the instructor has correct data
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCategory(), category, "Category did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getAssignmentTitle(),assignmentTitle, "Assignment title did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentTitle), scorePossible,"Score Possible did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle, studentSurname1+", "+studentName1),scoreReceivedForStudent1,"ScoreReceived did not match");
	    //browser.switchTo().defaultContent();
	    d2lApplication.d2lLogout(d2lHomeScreen);
	}
	
	//================== Deep Integration ====================
	      
	@Test(description = "SSO for instructor1", dependsOnMethods = "checkGradebookItemsAreCorrectForInstructorInCourse1")
	public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor2() throws Exception {
		
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		browser.pause(3000);
		d2lCourseDetailsScreen.clickOnContinueBtn();
		browser.pause(3000);
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
		
	}
	
	@Test(description = "SSO for student2", 
			dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor2")
	public void checkMHCampusLinkBehavesCorrectlyForD2LStudent2() throws Exception {				
		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin2, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);		
		browser.pause(3000);
		Assert.verifyTrue(d2lCourseDetailsScreen.isExistErrorMessageText(), "Error message is not shown");   		
		browser.pause(3000);
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}
	//============= S C O R A B L E ============
	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent2")
	   public void testScorableItemDeep() throws Exception {
	    String moduleId = gradebookApplication.fillTestScorableItemFormGetModuleID(customerNumber, providerId, 
	      Integer.toString(courseOfferingRS2.getId()), gradebookApplication.tegrityServiceLocation, moduleName);

	    TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDIWithModuleId(customerNumber,
	       providerId, d2LCourse2Encrypted, assignmentIdDeep, assignmentTitle, category, description,
	       startDate, dueToDate, scoreType, scorePossible, moduleId, isStudentViewable, isIncludedInGrade,
	       gradebookApplication.tegrityServiceLocation,tool_ID);	 
	 
	    Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2lDI(), "TestScoreItems form submit failed");
	}
	
	@Test(description = "Check TestScore Exist form is submitted successfully", dependsOnMethods = "testScorableItemDeep")
		public void testScoreDeepExist() throws Exception {
				
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreFormDI(customerNumber, providerId,
				d2LCourse2Encrypted, assignmentIdDeep, d2LStudent2Encrypted, commentForStudent2, dateSubmitted,
				scoreReceivedForStudent2, gradebookApplication.tegrityServiceLocation, tool_ID);
					    
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForD2lDI(), "TestScore form submit failed");
	}
	   
	@Test(description = "Check Gradebook data related to the Instructor2", 
			dependsOnMethods = "testScoreDeepExist")
	public void checkGradebookItemsAreCorrectForInstructorInCourse2Deep() throws Exception {
		
				d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin2, password);
				d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
				d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
				Assert.verifyEquals(
						d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentTitle,
								studentSurname2 + ", " + studentName2),
						scoreReceivedForStudent2, "ScoreReceived did not match");
	}
	   
	// Test Finish

	// private methods for tests
	private void prepareDataInD2lClassic() throws Exception {
		studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password, D2LUserRole.STUDENT);
		instructorRS1 = d2LApiUtils.createUser(instructorName1, instructorSurname1, instructorLogin1, password, D2LUserRole.INSTRUCTOR);
		
		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());
		
		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS1, courseOfferingRS1, D2LUserRole.INSTRUCTOR);
		
		// SSO part
		if (d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
			D2L_FRAME = "First";
		else
			D2L_FRAME = "NoFrame";
		checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();

		d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseId1, customerNumber, sharedSecret);
		d2lApplication.addCreatedLinkToD2lModule(courseId1, linkName, moduleName);
		
	}
	
	private void prepareDataInD2lDeep() throws Exception {
		studentRS2 = d2LApiUtils.createUser(studentName2, studentSurname2, studentLogin2, password, D2LUserRole.STUDENT);
		instructorRS2 = d2LApiUtils.createUser(instructorName2, instructorSurname2, instructorLogin2, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS2 = d2LApiUtils.createCourseTemplate("name2" + getRandomString(), "code2" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS2 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS2, courseName2,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId2 = Integer.toString(courseOfferingRS2.getId());
		
	    d2LCourse2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(courseId2).getResult();
	    d2LStudent2Encrypted = restApplication.fillUnencryptedIdAndPressEncrypt(Integer.toString(studentRS2.getUserId())).getResult();

		d2LApiUtils.createEnrollment(studentRS2, courseOfferingRS2, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS2, courseOfferingRS2, D2LUserRole.INSTRUCTOR);

		d2lHomeScreen =d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(nameOfWidget, launchUrl, customerNumber, sharedSecret, courseName2);		
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();		

		d2lApplication.bindingPluginToCourse(courseName2,customerNumber,sharedSecret, nameOfWidget);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName2);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(nameOfWidget);		
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId2);
		d2lContentCourseScreen.addModule(moduleName);
		
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);

	}

	private void clearD2lDataClassic() throws Exception {

		if (studentRS1 != null)
			d2LApiUtils.deleteUser(studentRS1);
		if (instructorRS1 != null)
			d2LApiUtils.deleteUser(instructorRS1);
		if (courseOfferingRS1 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS1);
		if (courseTemplateRS1 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS1);
	}
	
	private void clearD2lDataDeep() throws Exception {

		if (studentRS2 != null)
			d2LApiUtils.deleteUser(studentRS2);
		if (instructorRS2 != null)
			d2LApiUtils.deleteUser(instructorRS2);
		if (courseOfferingRS2 != null)
			d2LApiUtils.deleteCourseOffering(courseOfferingRS2);
		if (courseTemplateRS2 != null)
			d2LApiUtils.deleteCourseTemplate(courseTemplateRS2);
		d2lApplication.loginToD2lAsAdmin();
		browser.pause(2000);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(nameOfWidget);
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}
}

