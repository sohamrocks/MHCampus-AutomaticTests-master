package com.mcgraw.test.automation.tests.deepintegration.configurations;

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
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.D2LApplication;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lGradesDetailsScreenForInstructorV10;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.BookForConnect;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentCompleteRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentRegistrationScreen;

public class D2lDeepIntegrationWithConnect extends BaseTest {
	@Autowired
	private D2LApplication d2lApplication;
	
	@Autowired
	private D2LApiUtils d2LApiUtils;

	private String instructorRandom = getRandomString();
	private String instructorLogin1 = "instructor1" + instructorRandom;
	private String instructorName1 = "InstructorName1" + instructorRandom;
	private String instructorSurname1 = "InstructorSurame1" + instructorRandom;

	private String studentRandom = getRandomString();
	private String studentLogin1 = "student1" + studentRandom;
	private String studentName1 = "StudentName1" + studentRandom;
	private String studentSurname1 = "StudentSurname1" + studentRandom;
		
	private String password = "123qweA@";
	private String passwordForConnect = "Mc123456";

	private String courseName1 = "CourseName1" + getRandomString();
	private String courseId1;
	private String moduleName1 = "Module1" + getRandomNumber();
	private String moduleName2 = "Module2" + getRandomNumber();
	private String widgetName = "MHE_PQA_" + getRandomString();
	private String lunchUrl = "https://login-aws-qa.mhcampus.com/sso/di/d2l/lti/Connect";

	private String assignmentRandom1 = getRandomString();
	private String assignmentRandom2 = getRandomString();
	
    private final String customerNumber = "9M89-V0J0-GT67";
    private final String institution = "CustomCanvasConfiguration127";
    private final String sharedSecret = "7DDABC";

	private String instructorFullName = instructorLogin1 + " " + instructorLogin1;
	private BookForConnect book;
	private String studentAnswer1;
	private String studentAnswer2;

	private String appName = "AConnectDI";
	private String courseNameForConnect = "CourseForConnectDI";
	private String sectionNameForConnect = "SectionForConnectDI";
	private String assignmentName1 = "title_assign1" + assignmentRandom1;
	private String assignmentName2 = "title_assign2" + assignmentRandom2;
	private String startDate = "06/30/2018";
	private String endDate = "06/30/2019";
	private String hourMin = "10:30am";
	private String fullStartDate = startDate + " " + hourMin;
	private String fullEndDate = endDate + " " + hourMin;

	private String scorePossible = "10";

	private D2LUserRS studentRS1;
	private D2LUserRS instructorRS1;
	private D2LCourseTemplateRS courseTemplateRS1;
	private D2LCourseOfferingRS courseOfferingRS1;	
	
	private D2lHomeScreen d2lHomeScreen;
	private D2lCourseDetailsScreen d2lCourseDetailsScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lEditHomepageScreen d2lEditHomepageScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private D2lGradesDetailsScreenForInstructorV10 d2lGradesDetailsScreenForInstructor;
	private D2lConnectCourseDetailsScreen d2lConnectCourseDetailsScreen;
	private D2lGradesDetailsScreen d2lGradesDetailsScreen;
	
	private D2lConnectScreen d2lConnectScreen;
	private D2lConnectStudentRegistrationScreen d2lConnectStudentRegistrationScreen;
	private D2lConnectStudentCompleteRegistrationScreen d2lConnectStudentCompleteRegistrationScreen;
	private D2lConnectStudentCourseDetailsScreen d2lConnectStudentCourseDetailsScreen;

	private String bookId = "0073273163";
	private String bookNameLong = "Economics - BAT TESTING - DO NOT CHANGE CONFIG";
	private String bookName = "Economics - BAT";
	private String category = "Economics";

	private short logoutFromCanvas = 0;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		prepareDataInD2l();
		
	    d2lHomeScreen = d2lApplication.loginToD2lAsAdmin();
		d2lApplication.createNewWidgetPlugin(widgetName, lunchUrl, customerNumber, sharedSecret, courseName1);
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lApplication.bindingPluginToCourse(courseName1,customerNumber,sharedSecret, widgetName);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lEditHomepageScreen = d2lCourseDetailsScreen.clickEditHomepageBtn();
		d2lCourseDetailsScreen = d2lEditHomepageScreen.addWidget(widgetName);
		
		d2lContentCourseScreen = d2lApplication.openD2lContentCoursePage(courseId1);
		d2lContentCourseScreen.addModule(moduleName1);
		d2lContentCourseScreen.addModule(moduleName2);
		browser.switchTo().defaultContent();
		d2lApplication.d2lLogout(d2lHomeScreen);
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearD2lData();
		d2lApplication.loginToD2lAsAdmin();
		d2lManageExternalToolsScreen = d2lApplication.openD2LManageExternalToolsPage();
		d2lManageExternalToolsScreen.deleteWidgetLink(widgetName);
	}

	@AfterMethod
	public void logoutFromCanvas() throws Exception {
		if (logoutFromCanvas == 1){
			d2lApplication.d2lLogout(d2lHomeScreen);
		}
		if (logoutFromCanvas == 2){
			browser.closeAllWindowsExceptFirst();
			d2lApplication.d2lLogout(d2lHomeScreen);
		}
	}

	@Test(description = "For D2l instructor the course was created in Connect")
	public void checkCourseWasCreatedForInstructorInConnect() throws Exception {
		logoutFromCanvas = 0;
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		
		d2lConnectScreen = d2lCourseDetailsScreen.createCourceInConnect(instructorLogin1, appName, courseNameForConnect, sectionNameForConnect, book);
		
		Assert.verifyEquals(d2lConnectScreen.getUserFullNameName(), instructorFullName, "Instructor full name is incorrect");
		Assert.verifyEquals(d2lConnectScreen.getSectionName(), sectionNameForConnect, "Wrong section name in Connect for course");
		Assert.verifyEquals(d2lConnectScreen.getCourseName(), '('+ courseNameForConnect +')', "Wrong course name in Connect");
		Assert.verifyTrue(d2lConnectScreen.isSuccessMessagePresent(),"Success message doesn't present");
		Assert.verifyTrue(d2lConnectScreen.isLockImagePresent(),"Lock image doesn't present");	
		
	}
	
	@Test(description = "For D2l instructor check course Home Page details in Connect", 
			dependsOnMethods = {"checkCourseWasCreatedForInstructorInConnect"})
	public void checkCourseHomePageForInstructorInConnect() throws Exception {
		logoutFromCanvas = 0;
		d2lConnectCourseDetailsScreen = d2lConnectScreen.goToHomePage();
		
		Assert.verifyEquals(d2lConnectCourseDetailsScreen.getUserFullNameName(), instructorFullName, "Instructor full name is incorrect");
		Assert.verifyEquals(d2lConnectCourseDetailsScreen.getCourseName(), courseNameForConnect, "Wrong course name in Connect");
		Assert.verifyEquals(d2lConnectCourseDetailsScreen.getSectionName(), sectionNameForConnect, "Wrong section name in Connect for course");
	}
	
	@Test(description = "For D2l instructor were created assignments of course in Connect", 
			dependsOnMethods = {"checkCourseHomePageForInstructorInConnect"})
	public void checkAssagmentsWereCreatedForCourseInConnect() throws Exception {

		logoutFromCanvas = 2;
		studentAnswer1 = d2lConnectCourseDetailsScreen.addAssignmentChouseModule(false, assignmentName1, moduleName1, "" ,endDate, hourMin);
		studentAnswer2 = d2lConnectCourseDetailsScreen.addAssignmentChouseModule(false, assignmentName2, moduleName2, "" ,endDate, hourMin);

		Assert.verifyTrue(d2lConnectCourseDetailsScreen.isAssignmentNamePresent(assignmentName1),"Assignment name doesn't present");
		Assert.verifyTrue(d2lConnectCourseDetailsScreen.isAssignmentNamePresent(assignmentName2),"Assignment name doesn't present");
		Assert.verifyEquals(d2lConnectCourseDetailsScreen.getCountOfDeployedAssignments(), 2, "Count of Deployed assignments doesn't right");	
		Assert.verifyEquals(d2lConnectCourseDetailsScreen.getCountOfEnabledAssignments(), 2, "Count of Enabled assignments doesn't right");			
	}
	
	@Test(description = "For D2l instructor check the details of FIRST and SECOND Assignments in D2l", 
			dependsOnMethods = {"checkAssagmentsWereCreatedForCourseInConnect"})
	public void checkFirstAssagmentInD2lForInstructor() throws Exception {
		logoutFromCanvas = 1;
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();

		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getCountOfAssignments(), 2, 
				"Ammount of assignments of Instructor " + instructorLogin1 + " is incorrect");
		Assert.verifyTrue(d2lGradesDetailsScreenForInstructor.isAssignmentPresent(assignmentName1), 
				"Assignment title did not match on Grades page");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentName1), scorePossible,
				"Score Possible did not match");
		Assert.verifyTrue(d2lGradesDetailsScreenForInstructor.isAssignmentPresent(assignmentName2), 
				"Assignment title did not match on Grades page");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScorePossible(assignmentName2), scorePossible,
				"Score Possible did not match");

		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName1), 
				"Assignment title did not match on Content page");
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName2), 
				"Assignment title did not match on Content page");		
	}
	
	@Test(description = "Check the course details are right under student BEFORE registration to Connect", 
			dependsOnMethods = {"checkFirstAssagmentInD2lForInstructor"})
    public void checkCourseDetailsForStudentBeforeRegistrationToConnect() throws Exception {
		logoutFromCanvas = 0;
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lConnectStudentRegistrationScreen = d2lCourseDetailsScreen.clickBeginAsStudent();
		
		Assert.verifyTrue(d2lConnectStudentRegistrationScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentRegistrationScreen.isSectionPresentInConnect(sectionNameForConnect), "Section " + sectionNameForConnect + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentRegistrationScreen.isBookPresentInConnect(bookNameLong), "Book " + bookNameLong + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentRegistrationScreen.isInstructorNamePresentInConnect(instructorLogin1), "Instructor " + instructorLogin1 + " doesn't present");		
	}
	
	@Test(description = "Check the course details are right under student AFTER registration to Connect",
			dependsOnMethods = {"checkCourseDetailsForStudentBeforeRegistrationToConnect"})
	public void checkCourseDetailsForStudentAfterRegistrationToConnect() throws Exception {
		logoutFromCanvas = 0;
		d2lConnectStudentCompleteRegistrationScreen = d2lConnectStudentRegistrationScreen.registerAsStudentToConnect(studentLogin1, passwordForConnect);
		
		Assert.verifyTrue(d2lConnectStudentCompleteRegistrationScreen.isSuccessMessagePresentInConnect(), "Success message doesn't present");
		Assert.verifyTrue(d2lConnectStudentCompleteRegistrationScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCompleteRegistrationScreen.isSectionPresentInConnect(sectionNameForConnect), "Section " + sectionNameForConnect + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCompleteRegistrationScreen.isBookPresentInConnect(bookNameLong), "Book " + bookNameLong + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCompleteRegistrationScreen.isInstructorNamePresentInConnect(instructorLogin1), "Instructor " + instructorLogin1 + " doesn't present");
	}
	
	@Test(description = "Check the course details are right under student in Connect", 
			dependsOnMethods = {"checkCourseDetailsForStudentAfterRegistrationToConnect"})
	public void checkTheDetailsOfCourseInConnectUnderStudent() throws Exception {
		logoutFromCanvas = 2;
        d2lConnectStudentCourseDetailsScreen = d2lConnectStudentCompleteRegistrationScreen.goToConnect();
        
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isInstructorNamePresentInConnect(instructorLogin1), "Instructor " + instructorLogin1 + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isAssignmentPresentInConnect(assignmentName1), "Assignment " + assignmentName1 + " doesn't present");
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isAssignmentPresentInConnect(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
    }

	@Test(description = "Lunch and check FIRST Assignment for student", dependsOnMethods = {"checkTheDetailsOfCourseInConnectUnderStudent"})
	public void checkFirstAssagmentInD2lForStudent() throws Exception {
		logoutFromCanvas = 2;		
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);

		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName1), 
				"Assignment title did not match on Content page");
		
		d2lConnectStudentCourseDetailsScreen = d2lContentCourseScreen.lunchConnectLinkInModuleAsStudent(moduleName1, assignmentName1);
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isAssignmentNamePresent(assignmentName1), "Assignment " + assignmentName1 + " doesn't present");
	}
	
	@Test(description = "Lunch and check SECOND Assignment for student", dependsOnMethods = {"checkFirstAssagmentInD2lForStudent"})
	public void checkSecondAssagmentInD2lForStudent() throws Exception {
		logoutFromCanvas = 0;
		d2lHomeScreen = d2lApplication.loginToD2l(studentLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);

		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName2), 
				"Assignment title did not match on Content page");
		
		d2lConnectStudentCourseDetailsScreen = d2lContentCourseScreen.lunchConnectLinkInModuleAsStudent(moduleName2, assignmentName2);
		Assert.verifyTrue(d2lConnectStudentCourseDetailsScreen.isAssignmentNamePresent(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
	}
	
	@Test(description = "Answer the question as student of  assignments check, that Grade is correct", dependsOnMethods
			= {"checkSecondAssagmentInD2lForStudent"})
	public void checkGradeIsCorrectForStudentInAssignments() throws Exception {

		logoutFromCanvas = 1;
		String studentGrade2 = d2lConnectStudentCourseDetailsScreen.answerTheQuestionAndGetGrade(studentAnswer2); 
		browser.closeAllWindowsExceptFirst();
		
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		d2lConnectStudentCourseDetailsScreen = d2lContentCourseScreen.lunchConnectLinkInModuleAsStudent(moduleName1, assignmentName1);
		browser.pause(6000);
		String studentGrade1 = d2lConnectStudentCourseDetailsScreen.answerTheQuestionAndGetGrade(studentAnswer1); 
		browser.closeAllWindowsExceptFirst();

		d2lApplication.d2lLogout(d2lHomeScreen);

		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lGradesDetailsScreenForInstructor = d2lCourseDetailsScreen.clickGradesLinkAsInstructor();
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentName1, studentSurname1+", "+studentName1),studentGrade1,"ScoreReceived did not match");
		Assert.verifyEquals(d2lGradesDetailsScreenForInstructor.getScoreReceived(assignmentName2, studentSurname1+", "+studentName1),studentGrade2,"ScoreReceived did not match");
		 
    }
	
	@Test(description = "verify item titles for both modules", dependsOnMethods 
			={"checkGradeIsCorrectForStudentInAssignments"})
	public void verifyItemTitlesForBothModules() throws Exception {
		
		logoutFromCanvas = 1;
		d2lHomeScreen = d2lApplication.loginToD2l(instructorLogin1, password);
		d2lCourseDetailsScreen = d2lHomeScreen.goToCreatedCourse(courseName1);
		d2lContentCourseScreen = d2lCourseDetailsScreen.clickContentLink();
		
		d2lContentCourseScreen.chooseModuleBlock(moduleName1);
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName1));
		Assert.verifyTrue(d2lContentCourseScreen.verifyItemTitle(moduleName1));
		d2lContentCourseScreen.chooseModuleBlock(moduleName2);
		Assert.verifyTrue(d2lContentCourseScreen.isAssignmentPresent(assignmentName2));
		Assert.verifyTrue(d2lContentCourseScreen.verifyItemTitle(moduleName2));
		
	}

	private void prepareDataInD2l() throws Exception {
		studentRS1 = d2LApiUtils.createUser(studentName1, studentSurname1, studentLogin1, password, D2LUserRole.STUDENT);
		instructorRS1 = d2LApiUtils.createUser(instructorName1, instructorSurname1, instructorLogin1, password, D2LUserRole.INSTRUCTOR);

		courseTemplateRS1 = d2LApiUtils.createCourseTemplate("name" + getRandomString(), "code" + RandomStringUtils.randomNumeric(3));
		courseOfferingRS1 = d2LApiUtils.createCourseOfferingByTemplate(courseTemplateRS1, courseName1,
				"code" + RandomStringUtils.randomNumeric(3));
		courseId1 = Integer.toString(courseOfferingRS1.getId());
		
		d2LApiUtils.createEnrollment(studentRS1, courseOfferingRS1, D2LUserRole.STUDENT);
		d2LApiUtils.createEnrollment(instructorRS1, courseOfferingRS1, D2LUserRole.INSTRUCTOR);
		
		book = new BookForConnect(bookId, bookNameLong, bookName, category);

	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5).toUpperCase();
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
