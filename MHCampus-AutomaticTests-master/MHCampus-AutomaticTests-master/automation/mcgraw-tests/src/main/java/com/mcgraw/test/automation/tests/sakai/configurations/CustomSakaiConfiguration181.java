package com.mcgraw.test.automation.tests.sakai.configurations;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiAdminHomePage;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseGradeOptionsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiExternalToolsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiSetupExternalToolsScreen;

/**
* LMS = Sakai
* DI: Disabled
* Course ID – User ID Mapping: None
* Gradebook Connector: Yes
* Gradebook Connector type : none
* SSO Connector: No
* Direct Login Authentication Connector: Yes
* Authorization Connector: Yes"
* Data Connector: No
* Canvas Mapping: No
* Instructor Level Token: Off
* Use Existing Assignments: Off
* Fallback to user_id and context_id: Off
* Legacy: off
* Async: Off
*/

public class CustomSakaiConfiguration181 extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private ISakaiApiService sakaiApiService;
	private SakaiGradesScreen sakaiGradesScreen;
	
	private AddNewSite courseSakai;
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom1 = "epamcourse1" + getRandomString();
	private String studentLogin1 = "student" + studentRandom;
	private String studentName1 = "StudentName" + studentRandom;
	private String studentSurname1 = "StudentSurname" + studentRandom;

	private String instructorLogin1 = "instructor" + instructorRandom;
	private String instructorName1 = "InstructorName" + instructorRandom;
	private String instructorSurname1 = "InstructorSurname" + instructorRandom;

	private String password = "123qweA@";
	
	private String providerId = "provider_" + getRandomString();
	private String description = "description_" + getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String commentForStudent1 = "comment_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
	private String scorePossible = "100";
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;

	private static String SAKAI_FRAME = "First";

	private AddNewUser student1;
	private AddNewUser instructor1;
	private AddNewSite course1;

	MhCampusIntroductionScreen mhCampusIntroductionScreen;
	MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	SakaiHomeScreen sakaiHomeScreen;
	SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
	SakaiAdminHomePage sakaiAdminHomePage;
	SakaiExternalToolsScreen sakaiExternalToolsScreen;
	SakaiSetupExternalToolsScreen sakaiSetupExternalToolsScreen;

	private boolean checkBoxShowActiveCourse = false;
	
    private final String customerNumber = "P6QZ-JZYH-BRUG";
    private final String institution = "CustomSakaiConfiguration181";
    private final String sharedSecret = "3168F1";
    private final String instPassword = "klcdkcxh";
	private final String pageAddressFromEmail = "http://"+ institution +".mhcampus.com";


	@BeforeClass
	public void testSuiteSetup() throws Exception {
		Logger.info("LMS = Sakai");
		Logger.info("DI: Disabled");
		Logger.info("ID – User ID Mapping: None");
		Logger.info("Gradebook Connector: Yes");
		Logger.info("Gradebook Connector type : none");
		Logger.info("SSO Connector: No");
		Logger.info("Direct Login Authentication Connector: Yes");
		Logger.info("Authorization Connector: Yes");
		Logger.info("Data Connector: No");
		Logger.info("Canvas Mapping: No");
		Logger.info("Instructor Level Token: Off");
		Logger.info("Use Existing Assignments: Off");
		Logger.info("Fallback to user_id and context_id: Off");
		Logger.info("Legacy: On");
		Logger.info("Async: Off");
		
		prepareDataInSakai();
		
		// allow the students to see the grades
		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();
		SakaiCourseGradeOptionsScreen sakaiCourseGradeOptionsScreen = sakaiGradesScreen.goToCourseGradeOptions();
		sakaiCourseGradeOptionsScreen.allowDisplayCourseGradeForStudents();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		if(course1 != null)
			sakaiApiService.deletePageWithToolFromSite(course1, SakaiTool.LINK_TOOL);
		if(student1 != null)
			sakaiApiService.deleteUser(student1.getEid());
		if(instructor1 != null)
			sakaiApiService.deleteUser(instructor1.getEid());
		if(course1 != null)
			sakaiApiService.deleteSite(course1.getSiteid());
	}
	
	@AfterMethod
	public void logoutFromSakai() throws Exception {		
		sakaiApplication.logoutFromSakai();
	}

	@Test(description = "Check MH Campus link present and behaves correctly for Sakai instructor")
	public void checkMhCampusLinkBehavesCorrectlyForInstructor() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());

		Logger.info("Check MH Campus link is present for Sakai instructor");
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for instructor's course "
				+ course1.getSiteid());

		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();

		String expectedGreetingText = "Hi " + instructorName1 + " " + instructorSurname1 + " -";
		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isInstructorInfoPresent(SAKAI_FRAME));
		
		Logger.error("mhCampusIntroductionScreen.getFrameAddress(SAKAI_FRAME): " + mhCampusIntroductionScreen.getFrameAddress(SAKAI_FRAME));
		Logger.error("instance.pageAddressFromEmail.toLowerCase(): " + pageAddressFromEmail.toLowerCase());
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);

		String expectedUserName = (instructorName1 + " " + instructorSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is absent");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(course1.getSiteid(), SAKAI_FRAME), "Course " + course1.getSiteid() + " is absent");
		// Temporarily disabled
		/*if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getSiteid(), SAKAI_FRAME), "Course " + site2.getSiteid() + " is absent");
		}*/
	}

	@Test(description = "Check MH Campus link present behaves correctly for Sakai student", dependsOnMethods = { "checkMhCampusLinkBehavesCorrectlyForInstructor" })
	public void checkMhCampusLinkBehavesCorrectlyForStudent() throws Exception {

		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		
		Logger.info("Check MH Campus link is present for Sakai student");
		Assert.assertEquals(sakaiCourseDetailsScreen.getMhCampusLinksCount(), 1, "Wrong count of MH Campus links for student's course "
				+ course1.getSiteid());
		
		mhCampusIntroductionScreen = sakaiCourseDetailsScreen.clickMhCampusLink();
		String expectedGreetingText = "Hi " + studentName1 + " " + studentSurname1 + " -";

		Assert.verifyEquals(mhCampusIntroductionScreen.getGreetingTextFromRulesFrame(SAKAI_FRAME), expectedGreetingText,
				"Greeting text is incorrect");
		Assert.verifyTrue(mhCampusIntroductionScreen.isStudentInfoPresent(SAKAI_FRAME), "Rules text is incorrect");
		mhCampusIntroductionScreen.acceptRules(SAKAI_FRAME);

		String expectedUserName = (studentName1 + " " + studentSurname1).toUpperCase();
		Assert.verifyEquals(mhCampusIntroductionScreen.getUserNameText(SAKAI_FRAME), expectedUserName, "User name is incorrect");
		Assert.verifyFalse(mhCampusIntroductionScreen.isSearchOptionPresent(SAKAI_FRAME), "Search option is present");
		Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(course1.getSiteid(), SAKAI_FRAME), "Course " + course1.getSiteid() + " is absent");
		// Temporarily disabled
		/*if(!checkBoxShowActiveCourse){
			Assert.verifyTrue(mhCampusIntroductionScreen.isCoursePresent(site2.getSiteid(), SAKAI_FRAME), "Course " + site2.getSiteid() + " is absent");
		}*/
	}

	@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = {"checkMhCampusLinkBehavesCorrectlyForStudent"})
	public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
				course1.getSiteid(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
	}

	@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = { "checkSubmittingTestScorableItemFormIsSuccessfull" })
	public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId, course1.getSiteid(),
				assignmentId, studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
				gradebookApplication.tegrityServiceLocation);

		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
		
		// check the students has correct data
		sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin1, password);
		sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
		Assert.verifyEquals(sakaiGradesScreen.getScoreReceived(), scoreReceivedForStudent1,
				"ScoreReceived did not match");
		sakaiApplication.logoutFromSakai();
	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
	
	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInSakai() throws Exception {

		student1 = sakaiApiService.createUser(studentLogin1, password, studentName1, studentSurname1);
		instructor1 = sakaiApiService.createUser(instructorLogin1, password, instructorName1, instructorSurname1);

		course1 = sakaiApiService.addNewSite(courseRandom1);
		sakaiApiService.addMemberToSiteWithRole(course1, student1, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course1, instructor1, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(course1, SakaiTool.LINK_TOOL);
		sakaiApplication.completeMhCampusSetupWithSakai(course1.getSiteid(), customerNumber, sharedSecret);
	}
}
