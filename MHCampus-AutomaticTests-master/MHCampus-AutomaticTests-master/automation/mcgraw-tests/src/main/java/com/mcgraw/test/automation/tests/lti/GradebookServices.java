package com.mcgraw.test.automation.tests.lti;

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
import com.mcgraw.test.automation.framework.selenium2.ui.exception.ScreenIsNotOpenedException;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.applications.SakaiApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class GradebookServices extends BaseTest {

	@Autowired
	private GradebookApplication gradebookApplication;

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Autowired
	private ISakaiApiService sakaiApiService;

	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = "epamcourse" + getRandomString();
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;
	private String studentSurname = "StudentSurname" + studentRandom;

	private String instructorLogin = "instructor" + instructorRandom;
	private String instructorName = "InstructorName" + instructorRandom;
	private String instructorSurname = "InstructorSurname" + instructorRandom;

	private String password = "123qweA@";

	private String providerId = "provider_" + getRandomString();
	private String description = "description_" + getRandomString();
	private String assignmentTitle = "title_" + getRandomString();
	private String commentForStudent = "comment_" + getRandomString();
	private String assignmentId = getRandomNumber();
	private String startDate = GradebookApplication.getRandomStartDate();
	private String dueToDate = GradebookApplication.getRandomDueToDate();
	private String dateSubmitted = GradebookApplication.getRandomDateSubmitted();
	private String scoreReceivedForStudent = GradebookApplication.getRandomScore();
	private String scorePossible = "100";
	private String categoryType = "Blog";
	private String scoreType = "Percentage";
	private Boolean isIncludedInGrade = false;
	private Boolean isStudentViewable = false;
	
	private AddNewUser student;
	private AddNewUser instructor;
	private AddNewSite course;
	
	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;
	private InstanceCredentials instance;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {

		prepareDataInSakai();

		try{
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}catch(Exception e){
			Logger.info("Failed to create MH Campus instance, trying again...");
			browser.pause(60000);
			instance = tegrityAdministrationApplication.createNewMhCampusInstance();
		}		
		
		browser.pause(mhCampusInstanceApplication.CREATE_NEW_INSTANCE_TIMEOUT);

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAndAcceptTermsOfUse(
				instance.pageAddressForLogin, instance.institution, instance.username, instance.password);		
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(sakaiApplication.sakaiTitle,
				sakaiApplication.sakaiGradebookServiceUrl, sakaiApplication.sakaiGradebookExtendedProperties);
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	//@AfterClass(alwaysRun=true)
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		if(course != null)
			sakaiApiService.deletePageWithToolFromSite(course, SakaiTool.GRADEBOOK);
		if(student != null)
			sakaiApiService.deleteUser(student.getEid());
		if(instructor != null)
			sakaiApiService.deleteUser(instructor.getEid());
		if(course != null)
			sakaiApiService.deleteSite(course.getSiteid());
	}
	
	@Test(description = "Check Enable Gradebook Services BEFORE Changing")
	public void checkEnableGradebookServicesBeforeChanging() throws Exception {

		Assert.assertTrue(tegrityAdministrationApplication.getEnableGradebookServices(), "Gradebook Services doesn't Enable");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				course.getSiteid(), assignmentId, assignmentTitle, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, course.getSiteid(),
				assignmentId, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}
	
	@Test(description = "Check Disable Gradebook Services", dependsOnMethods = { "checkEnableGradebookServicesBeforeChanging" })
	public void checkDisableGradebookServices() throws Exception {

		try{
			tegrityAdministrationApplication.editSettingsInMhCampusInstance(instance.customerNumber, false);
		}catch(ScreenIsNotOpenedException e){
			if(e.toString().contains("TegrityAccountsScreen was not opened!")){
				tegrityAdministrationApplication.editSettingsInMhCampusInstance(instance.customerNumber, false);
			} else {
				throw e;	
			}
		}
		Assert.assertFalse(tegrityAdministrationApplication.getEnableGradebookServices(), "Gradebook Services doesn't Disable");
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		Assert.assertFalse(mhCampusInstanceConnectorsScreen.isGradebookContainerAvailable(), "Gradebook section is available");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				course.getSiteid(), assignmentId+1, assignmentTitle+1, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsFailedForSakai(), "TestScoreItems form submit doesn't return message as failed"); 
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, course.getSiteid(),
				assignmentId+1, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreScreen.formSubmitIsFailedForSakai(), "TestScore form submit doesn't return message as failed");
	}
	
	@Test(description = "Check Enable Gradebook Services AFTER Changing", dependsOnMethods = { "checkEnableGradebookServicesBeforeChanging", "checkDisableGradebookServices" })
	public void checkEnableGradebookServicesAfterChanging() throws Exception {

		try{
			tegrityAdministrationApplication.editSettingsInMhCampusInstance(instance.customerNumber, true);		
		} catch(ScreenIsNotOpenedException e) {
			if(e.toString().contains("TegrityAccountsScreen was not opened!")){
				tegrityAdministrationApplication.editSettingsInMhCampusInstance(instance.customerNumber, true);	
			} else {
				throw e;
			}
		}
		Assert.assertTrue(tegrityAdministrationApplication.getEnableGradebookServices(), "Gradebook Services doesn't Enable");
		
		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(instance.pageAddressForLogin,
				instance.institution, instance.username, instance.password);
		Assert.assertTrue(mhCampusInstanceConnectorsScreen.isGradebookContainerAvailable(), "Gradebook section don't available");
		
		TestScoreItemsScreen testScoreItemsScreen = gradebookApplication.fillTestScorableItemForm(instance.customerNumber, providerId,
				course.getSiteid(), assignmentId+2, assignmentTitle+2, categoryType, description, startDate, dueToDate, scoreType,
				scorePossible, isStudentViewable, isIncludedInGrade, gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreItemsScreen.formSubmitIsSuccessfullForSakai(), "TestScoreItems form submit failed");
		
		TestScoreScreen testScoreScreen = gradebookApplication.fillTestScoreForm(instance.customerNumber, providerId, course.getSiteid(),
				assignmentId+2, studentLogin, commentForStudent, dateSubmitted, scoreReceivedForStudent,
				gradebookApplication.tegrityServiceLocation);
		Assert.assertTrue(testScoreScreen.formSubmitIsSuccessfullForSakai(), "TestScore form submit failed");
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

	public void prepareDataInSakai() throws Exception {

		student = sakaiApiService.createUser(studentLogin, password, studentName, studentSurname);
		instructor = sakaiApiService.createUser(instructorLogin, password, instructorName, instructorSurname);

		course = sakaiApiService.addNewSite(courseRandom);
		sakaiApiService.addMemberToSiteWithRole(course, student, SakaiUserRole.STUDENT);
		sakaiApiService.addMemberToSiteWithRole(course, instructor, SakaiUserRole.INSTRUCTOR);
		sakaiApiService.addNewToolToSite(course, SakaiTool.GRADEBOOK);
	}
}
