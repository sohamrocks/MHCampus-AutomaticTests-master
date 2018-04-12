package com.mcgraw.test.automation.tests.lti;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.LTIApplication;

import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.lti.LtiScreen;


public class LTI1_1Test extends BaseTest {

	@Autowired
	protected LTIApplication ltiApplication;
	

	private String customerNumber ="D5SJ-J72I-RKQL";
	private String sharedSecret ="DC8F41";
	private String toolId="tool_id=testlti1_1";
	
	private String studentLogin;
	private String studentFullName;

	private String instructorLogin;
	private String instructorFullName;

	private String courseId;
	private String courseName;
	
	private String assignmentId;
	private String resourceLinkId;
	
	private TestScoreItemsScreen testScoreItemScreen;
	private TestScoreScreen testScoreScreen;
	private LtiScreen ltiScreen;
	
	private String score = "0.33";

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		prepareTestData();
	}
	
	@AfterMethod  
	public void closeAllWindowsExceptFirst() throws Exception {
		browser.closeAllWindowsExceptFirst();
	}
	

	@Test (description = "Check student launch with no mhcampus_lo_id", dependsOnMethods={"checkOutcomeGradeSync"})
	public void checkLtiOneLaunchAsStudent() throws Exception{
		testScoreItemScreen = ltiApplication.fillLtiFormAsStudent(customerNumber, sharedSecret, studentLogin, studentFullName,
				courseId, courseName, toolId,resourceLinkId);

		Assert.verifyTrue(testScoreItemScreen.isRoleCorrect("Learner"),"Role isn't correct");
		Assert.verifyTrue(testScoreItemScreen.isResourceLinkIdCorrect(resourceLinkId),"Resource link id isn't correct");
		Assert.verifyTrue(testScoreItemScreen.isCourseIdCorrect(courseId),"Course Id isn't correct");
		Assert.verifyTrue(testScoreItemScreen.isCustomerIdCorrect(customerNumber),"Customer id isn't correct");
	}
	
	@Test (description = "Check Grade syncing via outcome")
	public void checkOutcomeGradeSync() throws Exception{
		testScoreScreen = ltiApplication.fillLtiFormAsStudent(customerNumber, sharedSecret, studentLogin, studentFullName,
				courseId, courseName, toolId, assignmentId,resourceLinkId);
		
		Assert.verifyTrue(testScoreScreen.isOutcomeUrlCorrect(customerNumber),"Outcome URL isn't correct");
		Assert.verifyTrue(testScoreScreen.isSourcedIdCorrect(courseId, studentLogin, resourceLinkId)); // change resource lo_id to link id
		Assert.verifyTrue(testScoreScreen.isRoleCorrect("Learner"),"Role isn't correct");
		Assert.verifyTrue(testScoreScreen.isResourceLinkIdCorrect(resourceLinkId),"Resource link id isn't correct");
		
		testScoreScreen.submitViaOutcome(score);
		
		String scoreResult= testScoreScreen.getSubmitResult();
		
		Assert.verifyTrue(scoreResult.contains("Success"), "Sending Grade via outcomes failed");
		Assert.verifyTrue(scoreResult.contains("\"Name\":\"LTI 1.1 Compliant LMS Gradebook\""), "Wrong connector was used");
		Assert.verifyTrue(scoreResult.contains("\"Status\":\"updated\""), "Status grade is not updated");
		Assert.verifyTrue(scoreResult.contains("\"ErrorCode\":\"\""), "Error code is not empty");
		Assert.verifyTrue(scoreResult.contains("\"ErrorDescription\":null"), "Error description is not null");
		
		ltiScreen = ltiApplication.goToImsGradebookPage();
		ltiApplication.moveToGradeBookFrame();
		
		String gradeBookContextId  = ltiScreen.getGradeBookContextId();
		String gradeBookResourceId = ltiScreen.getGradeBookResourceId();
		String gradeBookUserId     = ltiScreen.getGradeBookUserId();
		String gradeBookScore      = ltiScreen.getGradeBookScore();
		
		Assert.verifyTrue(gradeBookContextId.equals(courseId), "Course Id in Gradebook frame isn't correct. [expected:" + courseId + "Actual:" + gradeBookContextId+"]");
		Assert.verifyTrue(gradeBookResourceId.equals(resourceLinkId),"Resource Id in Gradebook frame isn't correct. [expected:" + resourceLinkId + "Actual:" + gradeBookResourceId+"]");
		Assert.verifyTrue(gradeBookUserId.equals(studentLogin),"User Id in Gradebook frame isn't correct. [expected:" + studentLogin + "Actual:" + gradeBookUserId+"]");
		Assert.verifyTrue(gradeBookScore.equals(score),"Score in Gradebook frame isn't correct. [expected:" + score + "Actual:" + gradeBookScore+"]");
	}

	@Test (description = "Check Instructor course level launch", dependsOnMethods={"checkLtiOneLaunchAsStudent"})
	public void checkLti1_1ForInstructor() throws Exception{
		testScoreItemScreen = ltiApplication.fillLtiFormAsInstructor(customerNumber, sharedSecret, instructorLogin, instructorFullName,
				courseId, courseName, toolId, resourceLinkId);
		
		Assert.verifyTrue(testScoreItemScreen.isRoleCorrect("Instructor"),"Role isn't correct");
		Assert.verifyTrue(testScoreItemScreen.isResourceLinkIdCorrect(resourceLinkId),"Resource link id isn't correct");
		
		// TODO
		 
	}
	@Test (description = "Check Assignment level launch as Instructor", dependsOnMethods={"checkLti1_1ForInstructor"})
	public void checkInstructorAssignmentLevelLaunch()throws Exception{
		testScoreScreen = ltiApplication.fillLtiFormAsInstructor(customerNumber, sharedSecret, instructorLogin, instructorFullName,
				courseId, courseName, toolId, assignmentId,resourceLinkId);
		
		Assert.verifyTrue(testScoreScreen.isRoleCorrect("Instructor"),"Role isn't correct");
		Assert.verifyTrue(testScoreScreen.isResourceLinkIdCorrect(resourceLinkId),"Resource link id isn't correct");
		Assert.verifyTrue(testScoreScreen.isOutcomeUrlCorrect(),"Outcome URL isn't correct");
		Assert.verifyTrue(testScoreScreen.isSourcedIdCorrect(), "Sourced id isn't correct");
	}
	
	private void prepareTestData() {
		studentLogin = "studentLogin_" + getRandomString();
		studentFullName = "studentFullName_" + getRandomString();
		instructorLogin = "instructorLogin" + getRandomString();
		instructorFullName = "instructorFullName" + getRandomString();
		courseId = "courseId_" + getRandomString();
		courseName = "courseName_" + getRandomString();
		assignmentId = "assignmentId" + getRandomString(); // Assignment Id in outcome flow
		resourceLinkId = "resourceLinkId" + getRandomString();
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}
