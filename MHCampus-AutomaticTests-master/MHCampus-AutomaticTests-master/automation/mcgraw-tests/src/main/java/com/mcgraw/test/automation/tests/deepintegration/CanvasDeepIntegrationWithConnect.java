package com.mcgraw.test.automation.tests.deepintegration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;
import com.mcgraw.test.automation.ui.canvas.CanvasCourseDetailsScreen;
import com.mcgraw.test.automation.ui.canvas.CanvasHomeScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.BookForConnect;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentCourseDetailsScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentCompleteRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.CanvasConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class CanvasDeepIntegrationWithConnect extends BaseTest {

	@Autowired
	private CanvasApiUtils canvasApiUtils;

	@Autowired
	private CanvasApplication canvasApplication;

	private String studentRandom = getRandomString(6);
	private String instructorRandom = getRandomString(6);
	private String courseRandom = getRandomString(6);
	private String assignmentRandom1 = getRandomString(6);
	private String assignmentRandom2 = getRandomString(6);
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String courseName = "CourseName" + courseRandom;
	
	private String password = "123qweA@";
	private String passwordForConnect = "Mc123456";

	private CanvasUser student;
	private CanvasUser instructor;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS instructorEnrollment;
	
	private CanvasHomeScreen canvasHomeScreen;
	private CanvasCourseDetailsScreen canvasCourseDetailsScreen;
	private CanvasConnectCourseDetailsScreen canvasConnectCourseDetailsScreen;
	private CanvasConnectScreen canvasConnectScreen;
	private CanvasConnectStudentRegistrationScreen canvasConnectStudentRegistrationScreen;
	private CanvasConnectStudentCompleteRegistrationScreen canvasConnectStudentCompleteRegistrationScreen;
	private CanvasConnectStudentCourseDetailsScreen canvasConnectStudentCourseDetailsScreen;
	
	
	private String fullPathToFile;
	private String fileName = "QA_DI_Cartridge.xml";
	private String appName = "AConnectDI";
	private String courseNameForConnect = "CourseForConnectDI";
	private String sectionNameForConnect = "SectionForConnectDI";
	private String xmlFileConfiguration;
	private String assignmentName1 = "Assignment1" + assignmentRandom1;
	private String assignmentName2 = "Assignment2" + assignmentRandom2;
	private String startDate = "06/30/2018";
	private String endDate = "06/30/2019";
	private String hourMin = "10:30am";
	private String fullStartDate = startDate + " " + hourMin;
	private String fullEndDate = endDate + " " + hourMin;
	
	private String bookId = "0073273163";
	private String bookNameLong = "Economics - BAT TESTING - DO NOT CHANGE CONFIG";
	private String bookName = "Economics - BAT";
	private String category = "Economics";
	//For now this string object is omitted
//	private String secondBookName = "Economics - BAT TESTING - DO NOT CHANGE CONFIG";
	private BookForConnect book;
	private String studentAnswer;
	private short logoutFromCanvas = 0;

	// MH Campus instance for Canvas Deep Integration with Connect
	private int numOfSlave = 5;
	
	private InstanceCredentials instance;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
			
		prepareTestDataInCanvas();
		xmlFileConfiguration = getFile();
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
	}
	
	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		clearCanvasData();
	}
	
	@AfterMethod
	public void logoutFromCanvas() throws Exception {
		if (logoutFromCanvas == 1){
			canvasApplication.logoutFromCanvas();
		}
		if (logoutFromCanvas == 2){
			browser.closeAllWindowsExceptFirst();
			canvasCourseDetailsScreen.logout();	
		}
	}

	@Test(description = "For Canvas instructor, application link was created in Connect ")
	public void checkApplicationLinkIsPresentForInstructorInConnect() throws Exception {

		logoutFromCanvas = 1;
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.createApplicationLink(instance.customerNumber, instance.sharedSecret, xmlFileConfiguration, appName);
		Assert.verifyTrue(canvasCourseDetailsScreen.isApplicationAdded(appName), "Application link was not created for instructor's course " + courseName);
	}

	@Test(description = "For Canvas instructor the course was created in Connect", dependsOnMethods = {"checkApplicationLinkIsPresentForInstructorInConnect"})
	public void checkCourseWasCreatedForInstructorInConnect() throws Exception {

		logoutFromCanvas = 0;
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		Assert.verifyTrue(canvasCourseDetailsScreen.checkApplicationLinkPresentInCanvas(appName),"link with name: " + appName + " doesn't present");
		
		canvasConnectScreen = canvasCourseDetailsScreen.createCourceInConnect(instructorLogin, appName, courseNameForConnect, sectionNameForConnect, book);
		String instructorFullName = instructorLogin + " " + instructorLogin;
		
		Assert.verifyEquals(canvasConnectScreen.getUserFullNameName(), instructorFullName, "Instructor full name is incorrect");
		Assert.verifyEquals(canvasConnectScreen.getSectionName(), sectionNameForConnect, "Wrong section name in Connect for course");
		Assert.verifyEquals(canvasConnectScreen.getCourseName(), '('+ courseNameForConnect +')', "Wrong course name in Connect");
		Assert.verifyTrue(canvasConnectScreen.isSuccessMessagePresent(),"Success message doesn't present");
		Assert.verifyTrue(canvasConnectScreen.isLockImagePresent(),"Lock image doesn't present");	
	}
	
	@Test(description = "For Canvas instructor check course Home Page details  in Connect", dependsOnMethods = {"checkCourseWasCreatedForInstructorInConnect"})
	public void checkCourseHomePageForInstructorInConnect() throws Exception {

		logoutFromCanvas = 0;
		canvasConnectCourseDetailsScreen = canvasConnectScreen.goToHomePage();
		String instructorFullName = instructorLogin + " " + instructorLogin;
		
		Assert.verifyEquals(canvasConnectCourseDetailsScreen.getUserFullNameName(), instructorFullName, "Instructor full name is incorrect");
		Assert.verifyEquals(canvasConnectCourseDetailsScreen.getCourseName(), courseNameForConnect, "Wrong course name in Connect");
		Assert.verifyEquals(canvasConnectCourseDetailsScreen.getSectionName(), sectionNameForConnect, "Wrong section name in Connect for course");
		//For now this validation is being omitted
		//Assert.verifyEquals(canvasConnectCourseDetailsScreen.getBookName(), secondBookName, "Wrong book name in Connect for course");
	}

	@Test(description = "For Canvas instructor were created assignments of course in Connect", dependsOnMethods = {"checkCourseHomePageForInstructorInConnect"})
	public void checkAssagmentsWereCreatedForCourseInConnect() throws Exception {

		logoutFromCanvas = 2;
		canvasConnectCourseDetailsScreen.addAssignment(true, assignmentName1, startDate, endDate, hourMin);
		studentAnswer = canvasConnectCourseDetailsScreen.addAssignment(false, assignmentName2, "", endDate, hourMin);
		
		Assert.verifyTrue(canvasConnectCourseDetailsScreen.isAssignmentNamePresent(assignmentName1),"Assignment name doesn't present");
		Assert.verifyTrue(canvasConnectCourseDetailsScreen.isAssignmentNamePresent(assignmentName2),"Assignment name doesn't present");
		Assert.verifyEquals(canvasConnectCourseDetailsScreen.getCountOfDeployedAssignments(), 2, "Count of Deployed assignments doesn't right");	
		Assert.verifyEquals(canvasConnectCourseDetailsScreen.getCountOfEnabledAssignments(), 2, "Count of Enabled assignments doesn't right");	
	}
	
	@Test(description = "For Canvas instructor check the details of FIRST Assignment in Canvas", 
			dependsOnMethods = {"checkAssagmentsWereCreatedForCourseInConnect"})
	public void checkFirstAssagmentInCanvasForInstructor() throws Exception {

		logoutFromCanvas = 2;
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Assert.assertEquals(canvasCourseDetailsScreen.getQuantityOfAsignments(), 2, "Wrong quantity of assignments");		
		Assert.verifyTrue(canvasCourseDetailsScreen.isAssignmentPresent(assignmentName1), "Assignment " + assignmentName1 + " doesn't present");
		Assert.verifyEquals(canvasCourseDetailsScreen.getMaxPoints(assignmentName1), "10", "Wrong max points");
		
		canvasCourseDetailsScreen.clickAssignmentsLink();
		Assert.verifyTrue(canvasCourseDetailsScreen.isDatesOfAssignmentEqual(assignmentName1, fullStartDate, fullEndDate, true), 
				"The dates of assignment " + assignmentName1 + " don't equal");
	}
	
	@Test(description = "For Canvas instructor check the details if SECOND Assignment in Canvas", 
			dependsOnMethods = {"checkAssagmentsWereCreatedForCourseInConnect"})
	public void checkSecondAssagmentInCanvasForInstructor() throws Exception {

		logoutFromCanvas = 2;
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Assert.assertEquals(canvasCourseDetailsScreen.getQuantityOfAsignments(), 2, "Wrong quantity of assignments");		
		Assert.verifyTrue(canvasCourseDetailsScreen.isAssignmentPresent(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
		Assert.verifyEquals(canvasCourseDetailsScreen.getMaxPoints(assignmentName2), "10", "Wrong max points");
		
		canvasCourseDetailsScreen.clickAssignmentsLink();
		Assert.verifyTrue(canvasCourseDetailsScreen.isDatesOfAssignmentEqual(assignmentName2, fullStartDate, fullEndDate, false),
				"The dates of assignment " + assignmentName2 + " don't equal");
	}
	
	
	@Test(description = "Check the course details are right under student BEFORE registration to Connect", 
			dependsOnMethods = {"checkFirstAssagmentInCanvasForInstructor", "checkSecondAssagmentInCanvasForInstructor"})
    public void checkCourseDetailsForStudentBeforeRegistrationToConnect() throws Exception {

		logoutFromCanvas = 0;
		canvasHomeScreen = canvasApplication.loginToCanvasAndAcceptTerms(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasConnectStudentRegistrationScreen = canvasCourseDetailsScreen.clickBeginAsStudent(appName);
		
		Assert.verifyTrue(canvasConnectStudentRegistrationScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentRegistrationScreen.isSectionPresentInConnect(sectionNameForConnect), "Section " + sectionNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentRegistrationScreen.isBookPresentInConnect(bookNameLong), "Book " + bookNameLong + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentRegistrationScreen.isInstructorNamePresentInConnect(instructorLogin), "Instructor " + instructorLogin + " doesn't present");		
	}
	
	@Test(description = "Check the course details are right under student AFTER registration to Connect",
			dependsOnMethods = {"checkCourseDetailsForStudentBeforeRegistrationToConnect"})
	public void checkCourseDetailsForStudentAfterRegistrationToConnect() throws Exception {

		logoutFromCanvas = 0;
		canvasConnectStudentCompleteRegistrationScreen = canvasConnectStudentRegistrationScreen.registerAsStudentToConnect(studentLogin, passwordForConnect);
		
		Assert.verifyTrue(canvasConnectStudentCompleteRegistrationScreen.isSuccessMessagePresentInConnect(), "Success message doesn't present");
		Assert.verifyTrue(canvasConnectStudentCompleteRegistrationScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCompleteRegistrationScreen.isSectionPresentInConnect(sectionNameForConnect), "Section " + sectionNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCompleteRegistrationScreen.isBookPresentInConnect(bookNameLong), "Book " + bookNameLong + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCompleteRegistrationScreen.isInstructorNamePresentInConnect(instructorLogin), "Instructor " + instructorLogin + " doesn't present");
		
	}
	
	@Test(description = "Check the course details are right under student in Connect", dependsOnMethods = {"checkCourseDetailsForStudentAfterRegistrationToConnect"})
	public void checkTheDetailsOfCourseInConnectUnderStudent() throws Exception {

		logoutFromCanvas = 2;
		canvasConnectStudentCourseDetailsScreen = canvasConnectStudentCompleteRegistrationScreen.goToConnect();
		
		Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isCoursePresentInConnect(courseNameForConnect), "Course " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isInstructorNamePresentInConnect(instructorLogin), "Instructor " + instructorLogin + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isAssignmentPresentInConnect(assignmentName1), "Assignment " + assignmentName1 + " doesn't present");
		Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isAssignmentPresentInConnect(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
		//Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isStudentNamePresentInConnect(studentLogin), "Student " + studentLogin + " doesn't present");
	}
	
	@Test(description = "Check FIRST Assignment for student", dependsOnMethods = {"checkTheDetailsOfCourseInConnectUnderStudent"})
	public void checkFirstAssagmentInCanvasForStudent() throws Exception {

		logoutFromCanvas = 2;		
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();

		Assert.assertEquals(canvasCourseDetailsScreen.getQuantityOfAsignmentsForStudent(), 2, "Wrong quantity of assignments");
		Assert.verifyTrue(canvasCourseDetailsScreen.isAssignmentPresent(assignmentName1), "Assignment " + assignmentName1 + " doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isMessageForAssignmentStartInFuturePresent(assignmentName1), "Message for assignment " + assignmentName1 + " doesn't present");		
	}
	
	@Test(description = "Check SECOND Assignment for student", dependsOnMethods = {"checkTheDetailsOfCourseInConnectUnderStudent"})
	public void checkSecondAssagmentInCanvasForStudent() throws Exception {

		logoutFromCanvas = 0;	
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		canvasCourseDetailsScreen.clickAssignmentsLink();
		
		Assert.assertEquals(canvasCourseDetailsScreen.getQuantityOfAsignmentsForStudent(), 2, "Wrong quantity of assignments");		
		Assert.verifyTrue(canvasCourseDetailsScreen.isAssignmentPresent(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
		
		canvasConnectStudentCourseDetailsScreen = canvasCourseDetailsScreen.clickAssignmentLinkThatAlreadyStarted(assignmentName2);
		Assert.verifyTrue(canvasConnectStudentCourseDetailsScreen.isAssignmentNamePresent(assignmentName2), "Assignment " + assignmentName2 + " doesn't present");
	}
	
	@Test(description = "Answer the question as student of second assignmentand check, that Grade is correct", dependsOnMethods
			= {"checkFirstAssagmentInCanvasForStudent", "checkSecondAssagmentInCanvasForStudent"})
	public void checkGradeIsCorrectForStudentInAssignment2() throws Exception {
	
		String studentGrade = canvasConnectStudentCourseDetailsScreen.answerTheQuestionAndGetGrade(studentAnswer);  
		browser.closeAllWindowsExceptFirst();
		
	    Assert.verifyEquals(canvasCourseDetailsScreen.getStudentGradeFromAssignmentsList(assignmentName2), studentGrade, "Wrong grade of student assignment from assignments list");
	    Assert.verifyEquals(canvasCourseDetailsScreen.getStudentGradeFromGradesTable(assignmentName2), studentGrade, "Wrong grade of student assignment from grades table");	
    }
    
	@Test(description = "Check Connect links are present in Canvas and reset the connection for student", dependsOnMethods = {"checkGradeIsCorrectForStudentInAssignment2"})
	public void checkConnectLinksPresentInCanvasAndResetConnectionForStudent() throws Exception {
		
		logoutFromCanvas = 2;		
		canvasHomeScreen = canvasApplication.loginToCanvas(studentLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		
		Assert.verifyTrue(canvasCourseDetailsScreen.isGoToConnectSectionLinkPresent(appName), "link doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isSectionOfConnectPresent(sectionNameForConnect), "Section " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isUnlinkAutomaticLinkPresent(), "link doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isUserEmailPresent(studentLogin), "Email for user " + instructorLogin + " doesn't present");
		
		canvasCourseDetailsScreen.unlinkConnectAccountOfCourse(appName);	
		Assert.verifyTrue(canvasCourseDetailsScreen.isBeginButtonPresent(appName), "Begin button doesn't present");
	}
	
	@Test(description = "Check Connect links are present in Canvas and reset connection for instructor", dependsOnMethods = {"checkConnectLinksPresentInCanvasAndResetConnectionForStudent"})
	public void checkConnectLinksPresentInCanvasAndResetConnectionForInstructor() throws Exception {
		
		logoutFromCanvas = 0;
		
		canvasHomeScreen = canvasApplication.loginToCanvas(instructorLogin, password);
		canvasCourseDetailsScreen = canvasHomeScreen.goToCreatedCourse(courseName);
		
		Assert.verifyTrue(canvasCourseDetailsScreen.isGoToConnectSectionLinkPresent(appName), "link doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isUnlinkAutomaticLinkPresent(), "link doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isUserEmailPresent(instructorLogin), "Email for user " + instructorLogin + " doesn't present");		
		Assert.verifyTrue(canvasCourseDetailsScreen.isSectionOfConnectPresent(sectionNameForConnect), "Section " + courseNameForConnect + " doesn't present");
		Assert.verifyTrue(canvasCourseDetailsScreen.isSynchronizeWithConnectPresent(), "Synchronize with Connect part doesn't present");
		
		canvasCourseDetailsScreen.resetSectionPairing(appName);
		
		Assert.verifyFalse(canvasCourseDetailsScreen.isSectionOfConnectPresent(sectionNameForConnect), "Section " + courseNameForConnect + " is present");
		Assert.verifyFalse(canvasCourseDetailsScreen.isSynchronizeWithConnectPresent(), "Synchronize with Connect part is present");
		
		canvasCourseDetailsScreen.unlinkConnectAccountOfCourse(appName);
		Assert.verifyTrue(canvasCourseDetailsScreen.isBeginButtonPresent(appName), "Begin button doesn't present");
	}
	
	private void prepareTestDataInCanvas() throws Exception {

		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		instructor = canvasApiUtils.createUser(instructorLogin, password, instructorName);
		course = canvasApiUtils.createPublishedCourse(courseName);
		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		instructorEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(instructor, course);
		
		book = new BookForConnect(bookId, bookNameLong, bookName, category);
	}
	
	private String getFile() throws Exception {
	
		Logger.info("Get file from resources folder...");
		fullPathToFile = canvasApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		
		return new String(encoded, "UTF-8");		
	}
	
	private void clearCanvasData() throws Exception {

		if((studentEnrollment != null) & (course != null))
			canvasApiUtils.deleteEnrollment(studentEnrollment, course);
		if((instructorEnrollment != null) & (course != null))
		    canvasApiUtils.deleteEnrollment(instructorEnrollment, course);
		if(course != null)
			canvasApiUtils.deleteCourse(course);
		
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
	
	private String getRandomString(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}
}