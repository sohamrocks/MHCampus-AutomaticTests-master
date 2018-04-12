package com.mcgraw.test.automation.tests.ecollege;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ECollegeApplication;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TestConnectors extends BaseTest {

	@Autowired
	private ECollegeApplication eCollegeApplication;

	private String ecollegeInstructorName1 = "MHCampus eCollege Instructor 1";
	private String ecollegeInstructorLogin1 = "mh_epaminstructor1";
	private String internalInstructorID1 = "22159479";  

	// Not in using
	//private String ecollegeInstructorName2 = "MHCampus eCollege Instructor 2";
	//private String ecollegeInstructorLogin2 = "mh_epaminstructor2";
	//private String internalInstructorID2 = "22159666";  

	private String ecollegeStudentName1 = "MHCampus eCollege Student 1";
	//private String ecollegeStudentLogin1 = "mh_epamstudent1"; 
	private String internalStudentID1 = "22159766";

	private String ecollegeCourseName1 = "MHCampus eCollege Test Course 1";
	private String ecollegeCourseShortName1 = "mhcampus_epamcourse1";
	private String internalCourseID1 = "8929695";  

	private String ecollegeCourseName2 = "MHCampus eCollege Test Course 2"; 
	//private String ecollegeCourseShortName2 = "mhcampus_epamcourse2";
	private String internalCourseID2 = "8929711";  
	
	private String notExistingUsername = "automationTestUser";
	
	private String ecollegePassword = "123456";

	private int numOfSlave = 3; 
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;	
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		instance = tegrityAdministrationApplication.useExistingMhCampusInstance(numOfSlave);
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomAuthorizationConnector(eCollegeApplication.ecollegeTitle,
				eCollegeApplication.ecollegeAuthorizationServiceUrl, eCollegeApplication.ecollegeAuthorizationExtendedProperties);
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(eCollegeApplication.ecollegeTitle, eCollegeApplication.ecollegeGradebookServiceUrl,
				eCollegeApplication.ecollegeGradebookExtendedProperties);	
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();		
		
		eCollegeApplication.completeMhCampusSetupWithEcollege(ecollegeInstructorLogin1, ecollegePassword, ecollegeCourseShortName1,
				instance.customerNumber);
		
		browser.pause(mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT);
	}

	@Test(description = "Check Authorization connector behaves correctly for Instructor")
	public void checkAuthorizationConnectorForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(internalInstructorID1);
		Assert.verifyTrue(result.contains(internalInstructorID1), "Instructor Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeInstructorName1), "Instructor name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(internalInstructorID1);
		Assert.verifyTrue(result.contains(internalInstructorID1), "Instructor Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeInstructorName1), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for Student")
	public void checkAuthorizationConnectorForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
			
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(internalStudentID1);
		Assert.verifyTrue(result.contains(internalStudentID1), "Student Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeStudentName1), "Student name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(internalStudentID1);
		Assert.verifyTrue(result.contains(internalStudentID1), "Student Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeStudentName1), "Student name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check Authorization connector behaves correctly for not existing user")
	public void checkAuthorizationConnectorForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String returnedMessage = "eCollege Connector: The remote server returned an error: (404) Not Found";
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains(returnedMessage), "Not found the message in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(notExistingUsername);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains(returnedMessage), "Not found the message in the result string");
	}
	
	@Test(description = "Check Gradebook connector behaves correctly")
	public void checkGradebookConnector() {
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfExternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("Result is null"), "Not found in the result string");
		
		result = mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook();
		Assert.verifyTrue(result.contains("Result is null"), "Not found in the result string");
	}
	
	@Test(description = "Check result of Common 'Test' button for Instructor")
	public void checkResultOfCommonTestButtonForInstructor() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(internalInstructorID1, ecollegePassword);
		Assert.verifyTrue(result.contains(internalInstructorID1), "Instructor Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeInstructorName1), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"02"), "Instructor role doesn't right");
	}
	
	@Test(description = "Check result of Common 'Test' button for Angel Student")
	public void checkResultOfCommonTestButtonForStudent() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(internalStudentID1, ecollegePassword);
		Assert.verifyTrue(result.contains(internalStudentID1), "Student Internal ID doesn't present");
		Assert.verifyTrue(result.contains(ecollegeStudentName1), "Student name doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID1), "Internal ID of the first course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(internalCourseID2), "Internal ID of the second course doesn't present");
		Assert.verifyTrue(result.contains(ecollegeCourseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains("roletype=\"01"), "Student role doesn't right");
	}
	
	@Test(description = "Check result of Common 'Test' button for for not existing user")
	public void checkResultOfCommonTestButtonForNotExistingUser() {
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin, 
				instance.institution, instance.username, instance.password);
		
		String returnedMessage = "eCollege Connector: The remote server returned an error: (404) Not Found";
		
		String result = mhCampusInstanceConnectorsScreen.getResultOfCommonTestButton(notExistingUsername, ecollegePassword);
		Assert.verifyFalse(result.contains(notExistingUsername), "Instructor username is present");
		Assert.verifyTrue(result.contains(returnedMessage), "Not found the message in the result string");
	}
}
