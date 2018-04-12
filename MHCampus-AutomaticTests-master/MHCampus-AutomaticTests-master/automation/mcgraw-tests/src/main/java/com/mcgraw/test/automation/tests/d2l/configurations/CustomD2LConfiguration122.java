package com.mcgraw.test.automation.tests.d2l.configurations;

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
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;

/**
* LMS =  D2L 
* DI: Disabled
* Course ID – User ID Mapping: None 
* Gradebook Connector: Yes 
* Gradebook Connector type : none
* SSO Connector:  No
* Data Connector: Yes 
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Legacy: On 
* Async: Off
*/

public class CustomD2LConfiguration122 extends BaseTest {	

		@Autowired
		private GradebookApplication gradebookApplication;

		@Autowired
		private D2LApplication d2lApplication;
		
		@Autowired
		private D2LApiUtils d2LApiUtils;
		
		private String institution = "CustomCanvasConfiguration122";// 	
		private String customerKey = "7VLK-U1C5-YF7T";		
		private String sharedSecret = "34DB8A";
//		private String adminUserName = "admin@"+institution+".mhcampus.com";
//		private String adminPassword = "mprwcxde";
		private String pageAddressForLogin = "http://"+institution+".mhcampus.com/";
		private String customerNumber = customerKey;
		
		//SSO part		
		private D2lContentCourseScreen d2lContentCourseScreen;
		private MhCampusIntroductionScreen mhCampusIntroductionScreen;
		private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;


		private static String moduleName = "Module" + RandomStringUtils.randomNumeric(5);
		private static String linkName = "McGraw-Hill Campus";
		private static String D2L_FRAME;
		private boolean checkBoxShowActiveCourse = false;		
		//=============================================================
		private String providerId = "Connect";
		private String assignmentId = getRandomNumber();
		private String assignmentTitle = "title_" + getRandomString();
		private String category = "category_" + getRandomString();
		private String startDate = GradebookApplication.getRandomStartDate();
		private String dueToDate = GradebookApplication.getRandomDueToDate();
		private String scoreType = "Percentage";
		private String scorePossible = "100";
		private Boolean isIncludedInGrade = false;
		private Boolean isStudentViewable = false;
		private String commentForStudent1 = "comment_" + getRandomString();
		private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
		private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
		
		private D2lHomeScreen d2lHomeScreen;
		private D2lCourseDetailsScreen d2lCourseDetailsScreen;;

		private String studentRandom = getRandomString();
		private String studentLogin1 = "student" + studentRandom;
		private String studentName1 = "StudentName" + studentRandom;
		private String studentSurname1 = "StudentSurname" + studentRandom;

		private String instructorRandom = getRandomString();
		private String instructorLogin1 = "instructor" + instructorRandom;
		private String instructorName1 = "InstructorName" + instructorRandom;
		private String instructorSurname1 = "InstructorSurame" + instructorRandom;

		private String password = "123qweA@";

		private String courseName1 = "CourseName1" + getRandomString();
		private String courseCode1;
		
		private D2LUserRS studentRS1;
		private D2LUserRS instructorRS1;
		private D2LCourseTemplateRS courseTemplateRS1;
		private D2LCourseOfferingRS courseOfferingRS1;
				
		@BeforeClass
		public void testSuiteSetup() throws Exception {
			
			Logger.info("Starting test for configuration:");
			Logger.info("LMS =  D2L");
			Logger.info("Course ID – User ID Mapping: None | Gradebook Connector: Yes | Gradebook Connector type : none | SSO Connector: No | Data Connector: Yes | Canvas Mapping: No");
			Logger.info("* Instructor Level Token: Off | Use Existing Assignments: Off | Fallback to user_id and context_id: Off | Legacy: On | Async: Off");

			prepareTestDataInD2l();
		}

		//@AfterClass(alwaysRun=true)
		@AfterClass
		public void testSuiteTearDown() throws Exception {
			clearD2lData();
		}
		
		@AfterMethod  
		public void closeAllWindowsExceptFirst() throws Exception {
			browser.closeAllWindowsExceptFirst();
		}		
		
		@Test(description = "For D2L instructor MH Campus link baheves correctly")
		public void checkMHCampusLinkBehavesCorrectlyForD2LInstructor1() throws Exception {

			d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
			d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
			d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
			d2lContentCourseScreen.chooseModuleBlock(moduleName);
			
			Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course "
					+ courseName1);
			
			mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();

			if(! d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")){
				Assert.verifyTrue(mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressForLogin.toLowerCase()),
						"The address of frame is not contain instance login page address");
			}

			String expectedGreetingText = "Hi " + instructorName1 + " " + instructorSurname1 + " -";

			Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
					"Greeting text is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(D2L_FRAME), "Rules text is incorrect");

			mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

			String expectedUserName = (instructorName1 + " " + instructorSurname1).toUpperCase();
			Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName, "User name is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME), "Course " + courseName1 + " is absent");
			
//			TODO Uncomment the following line when the issue with presence of the 2nd course will be resolved
//			if(!checkBoxShowActiveCourse){
//				Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, D2L_FRAME), "Course " + courseName2 + " is absent");
//			}
			Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is absent");
		}

		@Test(description = "For D2L student MH Campus link baheves correctly", dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LInstructor1")
		public void checkMHCampusLinkBehavesCorrectlyForD2LStudent1() throws Exception {

			d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
			d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
			d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
			d2lContentCourseScreen.chooseModuleBlock(moduleName);
			
			Assert.assertEquals(d2lContentCourseScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course "
					+ courseName1);
			
			mhCampusIntroductionScreen = d2lContentCourseScreen.clickMhCampusLink();
			
			String expectedGreetingText = "Hi " + studentName1 + " " + studentSurname1 + " -";		
			Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(D2L_FRAME), expectedGreetingText,
					"Greeting text is incorrect");
			if(! d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com")){
				Assert.verifyTrue(mhCampusIntroductionScreen.getFrameAddress(D2L_FRAME).contains(pageAddressForLogin.toLowerCase()),
						"The address of frame is not contain instance login page address");
			}

			Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(D2L_FRAME), "Rules text is incorrect");

			mhCampusIntroductionScreen.acceptRules(D2L_FRAME);

			String expectedUserName = (studentName1 + " " + studentSurname1).toUpperCase();
			Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(D2L_FRAME), expectedUserName, "User name is incorrect");
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName1, D2L_FRAME), "Course " + courseName1 + " is absent");
			
//			TODO Uncomment the following line when the issue with presence of the 2nd course will be resolved
//			if(!checkBoxShowActiveCourse){
//				Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(courseName2, D2L_FRAME), "Course " + courseName2 + " is absent");
//			}
			Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(D2L_FRAME), "Search option is present");
		}		
		//============================================================
		//   
		@Test(description = "Check TestScorableItem form is submitted successfully", 
				dependsOnMethods = "checkMHCampusLinkBehavesCorrectlyForD2LStudent1" )
		public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {
			
			TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId, Integer.toString(courseOfferingRS1.getId()) ,
					assignmentId, assignmentTitle, category, "", startDate, dueToDate, scoreType, scorePossible, isStudentViewable,
					isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

			Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForD2l(), "TestScoreItems form submit failed");
		}

		@Test(description = "Check TestScore form is submitted successfully", 
				dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfull")
		public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

			TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId, Integer.toString(courseOfferingRS1.getId()), assignmentId,
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

		private void prepareTestDataInD2l() throws Exception {

			studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password, D2LUserRole.STUDENT);
			instructorRS1 = d2LApiUtils.createUser(instructorName1, instructorSurname1, instructorLogin1, password, D2LUserRole.INSTRUCTOR);

			courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
			courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
					"code" + RandomStringUtils.randomNumeric(3));
			courseCode1 = Integer.toString(courseOfferingRS1.getId());

			d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
			d2LApiUtils.createEnrollment(instructorRS1, courseOfferingRS1, D2LUserRole.INSTRUCTOR);
			//SSO part						
			if(d2lApplication.d2lBaseUrl.equals("https://tegrity.desire2learn.com"))
				D2L_FRAME = "First";
			else
				D2L_FRAME = "NoFrame";
			
			checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();
			
			d2lApplication.createD2lLinkConnectedWithInstance(linkName, courseCode1, customerNumber, sharedSecret);
			d2lApplication.addCreatedLinkToD2lModule(courseCode1, linkName, moduleName);			
		}

		private String getRandomString() {
			return RandomStringUtils.randomAlphanumeric(5);
		}

		private String getRandomNumber() {
			return RandomStringUtils.randomNumeric(5);
		}
		
		private void clearD2lData() throws Exception {
			
			if(studentRS1 != null)
				d2LApiUtils.deleteUser(studentRS1);
			if(instructorRS1 != null)
				d2LApiUtils.deleteUser(instructorRS1);
			if(courseOfferingRS1 != null)
				d2LApiUtils.deleteCourseOffering(courseOfferingRS1);
			if(courseTemplateRS1 != null)
				d2LApiUtils.deleteCourseTemplate(courseTemplateRS1);
		}
	}


