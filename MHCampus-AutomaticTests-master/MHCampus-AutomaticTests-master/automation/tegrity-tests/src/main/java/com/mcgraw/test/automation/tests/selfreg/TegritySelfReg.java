package com.mcgraw.test.automation.tests.selfreg;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySelfReg extends BaseTest {

	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;

	private String student;
	private String instructor;
    private String course;
    
    private int flag = 0;
	
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
	
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureBuilderAuthorizationConnector();
		tegrityInstanceConnectorsScreen.configureBuilderAuthenticationConnector();
		
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);		
	}
	
	@AfterMethod  
	public void logoutFromTegrity() throws Exception {
		if(flag == 1)
			tegrityIntroductionScreen.logOut();
		if(flag == 2)
			tegrityInstanceDashboardScreen.logoutFromTegrity();
	}
	
	@Test(description = "Check Builder links are present")
	public void checkBuilderLinksArePresent() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;	
		// check builder links are present
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isUserBuilderLinkPresent(), "User Builder link is absent");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isCourseBuilderLinkPresent() , "Course Builder link is absent");
		
		// prepare data for test
		deleteUsersAndCourses();
		prepareTestData();
		createUsersAndCourses();
	}
	
	@Test(description = "Check Filters work correctly", dependsOnMethods = { "checkBuilderLinksArePresent" })
	public void checkFiltersWorkCorrectly() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		//check filters work correctly for instructor, student and course		
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();		
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(instructor), "The user " + instructor + " doesn't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(student), "The user " + instructor + " doesn't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isCoursePresentUsingFilter(course), "The course " + course + " doesn't present");
	}
	
	@Test(description = "Check the course present for instructor", dependsOnMethods = { "checkBuilderLinksArePresent" })
	public void checkCoursePresentForInstructor() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		// check course present for instructor
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructor, instructor);
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is absent");
	}
	
	@Test(description = "Check the course present for student", dependsOnMethods = { "checkBuilderLinksArePresent" })
	public void checkCoursePresentForStudent() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		// check course present for student
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(student, student);
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is absent");
	}
	
	@Test(description = "Check the course NOT present for instructor", dependsOnMethods = "checkCoursePresentForInstructor")
	public void checkCourseNotPresentForInstructor() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
				
		// remove membership of course for instructor
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		tegrityInstanceDashboardScreen.removeMembership(course, instructor);
		tegrityInstanceDashboardScreen.logoutFromTegrity();
		
		// check course NOT present for instructor
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructor, instructor);
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is present");
	}
	
	@Test(description = "Check the course NOT present for student", dependsOnMethods = "checkCoursePresentForStudent")
	public void checkCourseNotPresentForStudent() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		// remove membership of course for student
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		tegrityInstanceDashboardScreen.removeMembership(course, student);
		tegrityInstanceDashboardScreen.logoutFromTegrity();
		
		// check course NOT present for student
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(student, student);
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is present");
	}
	
	@Test(description = "Check course and users are not present, after deleting", dependsOnMethods = { "checkCourseNotPresentForStudent", "checkCourseNotPresentForInstructor"})
	public void checkCourseAndUsersNotPresentAfterDeleting() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		// delete users and courses from Tegrity   
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		deleteUsersAndCourses();
		
		// check users don't present after deleting
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(instructor), "The user " + instructor + " is present");
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(student), "The user " + instructor + " is present");
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isCoursePresentUsingFilter(course), "The course " + course + " is present");
	}
	
	@Test(description = "Check users can not login to Tegrity", dependsOnMethods = "checkCourseAndUsersNotPresentAfterDeleting")
	public void checkUsersCanNotLoginToTegrity() throws Exception {
		
		// without logout
		flag = 0;
		
		// check users can not login into Tegrity after deleting
		Assert.verifyFalse(tegrityInstanceApplicationNoLocalConnector.canLoginToTegrityAsUser(student, student), "User " + student + " can login into Tegrity");
		Assert.verifyFalse(tegrityInstanceApplicationNoLocalConnector.canLoginToTegrityAsUser(instructor, instructor), "User " + instructor + " can login into Tegrity");
	}
	
	// Must to work after solving the problem with course not visible after deleting and adding again
    /*@Test(description = "Check course and users are present, after deleting and adding again", dependsOnMethods = {"checkCourseAndUsersNotPresentAfterDeleting", "checkUsersCanNotLoginToTegrity"})
	public void checkCourseAndUsersPresentAfterDeletingAndAddingAgain() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		// create the same users, courses and membership again  
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		createUsersAndCourses();
		
		// check users and courses are present
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(instructor), "The user " + instructor + " doesn't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isUserPresentUsingFilter(student), "The user " + instructor + " doesn't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isCoursePresentUsingFilter(course), "The course " + course + " doesn't present");
	}
	
	@Test(description = "Check the course present for instructor", dependsOnMethods = { "checkCourseAndUsersPresentAfterDeletingAndAddingAgain" })
	public void checkCoursePresentForInstructorAfterDeletingAndAddingAgain() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		// check course present for instructor
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructor, instructor);
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is absent");
	}
	
	@Test(description = "Check the course present for student", dependsOnMethods = { "checkCourseAndUsersPresentAfterDeletingAndAddingAgain" })
	public void checkCoursePresentForStudentAfterDeletingAndAddingAgain() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		// check course present for student
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(student, student);
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(course), "Course " + course + " is absent");
	}*/
	
	
	private void createUsersAndCourses() {
		tegrityInstanceDashboardScreen.createUserBuilder(instructor);
		tegrityInstanceDashboardScreen.createUserBuilder(student);
		tegrityInstanceDashboardScreen.createCourseBuilder(course);
		tegrityInstanceDashboardScreen.addMembership(course, instructor);
		tegrityInstanceDashboardScreen.addMembership(course, student);
	}
	
	private void deleteUsersAndCourses() {
		tegrityInstanceDashboardScreen.deleteAdminUsers();
		tegrityInstanceDashboardScreen.deleteUsers();
		tegrityInstanceDashboardScreen.deleteCourses();
	}

	private void prepareTestData() {
		student = "student" + getRandomString();
		instructor = "instructor" + getRandomString();
		course = "course" + getRandomString();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}