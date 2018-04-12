package com.mcgraw.test.automation.tests.sakai.configurations;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
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
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiCourseGradeOptionsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesDetailsScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiGradesScreen;
import com.mcgraw.test.automation.ui.sakai.SakaiHomeScreen;

public class CustomSakaiConfiguration177 extends BaseTest {

@Autowired
private GradebookApplication gradebookApplication;

@Autowired
private SakaiApplication sakaiApplication;

private String customerNumber = "20VA-06YI-PL6Z";
private String sharedSecret = "DF098E";
private String institutionName = "CustomSakaiConfiguration177";
private String pageAddressFromEmail = "http://"+ institutionName +".mhcampus.com";

private String studentRandom1 = getRandomString();
private String instructorRandom1 = getRandomString();
private String courseRandom1 = "courseName1" + getRandomString();

private String studentLogin1 = "student1" + studentRandom1;
private String studentName1 = "StudentName1" + studentRandom1;
private String studentSurname1 = "StudentSurname1" + studentRandom1;

private String instructorLogin1 = "instructor1" + instructorRandom1;
private String instructorName1 = "InstructorName1" + instructorRandom1;
private String instructorSurname1 = "InstructorSurname1" + instructorRandom1;

private String password = "123qweA@";

private String providerId = "provider_" + getRandomString();
private String description = "description_" + getRandomString();
private String assignmentTitle = "title_" + getRandomString();
private String commentForStudent1 = "comment_" + getRandomString();
//private String commentForStudent2 = "comment_" + getRandomString();
private String assignmentId = getRandomNumber();
private String startDate = GradebookApplication.getRandomStartDate();
private String dueToDate = GradebookApplication.getRandomDueToDate();
private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
private String scoreReceivedForStudent1 = GradebookApplication.getRandomScore();
//private String scoreReceivedForStudent2 = GradebookApplication.getRandomScore();
private String scorePossible = "100";
private String categoryType = "Blog";
private String scoreType = "Percentage";
private Boolean isIncludedInGrade = false;
private Boolean isStudentViewable = false;

//private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
private MhCampusIntroductionScreen mhCampusIntroductionScreen;
private SakaiHomeScreen sakaiHomeScreen;
private SakaiCourseDetailsScreen sakaiCourseDetailsScreen;
private SakaiGradesScreen sakaiGradesScreen;

@Autowired
private ISakaiApiService sakaiApiService;
private static String SAKAI_FRAME = "First";
//private boolean checkBoxShowActiveCourse = false;

private AddNewUser student1;
private AddNewUser instructor1;
private AddNewSite course1;

@BeforeClass
public void testSuiteSetup() throws Exception {

	prepareDataInSakai();

}

//@AfterClass(alwaysRun=true)
@AfterClass
public void testSuiteTearDown() throws Exception {
	
	if(course1 != null)
		sakaiApiService.deletePageWithToolFromSite(course1, SakaiTool.GRADEBOOK);
	if(student1 != null)
		sakaiApiService.deleteUser(student1.getEid());
	if(instructor1 != null)
		sakaiApiService.deleteUser(instructor1.getEid());
	if(course1 != null)
		sakaiApiService.deleteSite(course1.getSiteid());

}

@Test(description = "Check MH Campus link behaves correctly for Sakai instructor")
public void checkMhCampusLinkBehavesCorrectlyForInstructor1() throws Exception {

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

@Test(description = "Check MH Campus link behaves correctly for Sakai student", dependsOnMethods = "checkMhCampusLinkBehavesCorrectlyForInstructor1")
public void checkMhCampusLinkBehavesCorrectlyForStudent1() throws Exception {

	sakaiHomeScreen = sakaiApplication.loginToSakai(studentLogin1, password);
	sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
	
	//Check MH Campus link is present for Sakai student
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

//========================= SCORABLE =====================

@Test(description = "Check TestScorableItem form is submitted successfully", dependsOnMethods = "checkMhCampusLinkBehavesCorrectlyForStudent1")
public void checkSubmittingTestScorableItemFormIsSuccessfull() throws Exception {

	TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(customerNumber, providerId,
			course1.getSiteid(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
			scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);

	Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
}

@Test(description = "Check TestScore form is submitted successfully", dependsOnMethods = "checkSubmittingTestScorableItemFormIsSuccessfull")
public void checkSubmittingTestScoreFormIsSuccessfullForStudent1() throws Exception {

	TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(customerNumber, providerId, course1.getSiteid(),
			assignmentId, studentLogin1, commentForStudent1, dateSubmitted, scoreReceivedForStudent1,
			gradebookApplication.tegrityServiceLocation);

	Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
}

@Test(description = "Check with Sakai instructor student's gradebook info", dependsOnMethods = {
"checkSubmittingTestScoreFormIsSuccessfullForStudent1"})
public void checkInstructorCanSeeGradesForEachStudent() throws Exception {

	sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin1, password);
	sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());		
	SakaiGradesDetailsScreen sakaiGradesDetailsScreen = sakaiGradesScreen.goToDetailsOfAssignment();

	Assert.verifyEquals(sakaiGradesDetailsScreen.getScoreReceivedForStudent(studentLogin1), scoreReceivedForStudent1);

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
	//sakaiApiService.addNewToolToSite(course1, SakaiTool.GRADEBOOK);		
	sakaiApiService.addNewToolToSite(course1, SakaiTool.LINK_TOOL);
	
	sakaiApplication.completeMhCampusSetupWithSakai(course1.getSiteid(), customerNumber, sharedSecret);
	sakaiApplication.logoutFromSakai();
			
	// allow the students to see the grades
	sakaiHomeScreen = sakaiApplication.loginToSakai(instructorLogin1, password);
	sakaiCourseDetailsScreen = sakaiHomeScreen.goToCreatedCourse(course1.getSiteid());
	sakaiGradesScreen = sakaiCourseDetailsScreen.clickGradebookBtn();
	SakaiCourseGradeOptionsScreen sakaiCourseGradeOptionsScreen = sakaiGradesScreen.goToCourseGradeOptions();
	sakaiCourseGradeOptionsScreen.allowDisplayCourseGradeForStudents();
	sakaiApplication.logoutFromSakai();		
	
	//checkBoxShowActiveCourse = tegrityAdministrationApplication.getCheckBoxShowActiveCourse();			
	
}
}
