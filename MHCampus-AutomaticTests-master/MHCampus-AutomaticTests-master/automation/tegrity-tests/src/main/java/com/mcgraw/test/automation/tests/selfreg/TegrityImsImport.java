package com.mcgraw.test.automation.tests.selfreg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.ImsImportApplication;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.tegrity.TegrityImsImportScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegrityImsImport extends BaseTest {

	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;
	
	@Autowired
	protected ImsImportApplication imsImportApplication;	
	
	private String fileName = "ImsImportDataCreation.xml";
	private String tempFile = "temp.xml";
	private String absolutePath;
	private String fullPathToFile;

	private String instructorId_Old;
	private String studentId_Old;
	private String courseId1_Old;
	private String courseId2_Old;
	
	private String instructorFullName_Old;
	private String studentFullName_Old;
	private String courseName1_Old;
	private String courseName2_Old;
	
	private String instructorId;
	private String studentId;
	private String courseId1;
	private String courseId2;
	
	private String instructorFullName;
	private String studentFullName;
	private String courseName1;
	private String courseName2;
	
	private String instructorRoleType = "roletype=\"02";
	private String studentRoleType = "roletype=\"01";
    
    private String password = "tegrity84";
    
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityImsImportScreen tegrityImsImportScreen;
	
	private int flagForLogOut =0;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		fullPathToFile = imsImportApplication.pathToFile + fileName;
		File file = new File(fullPathToFile);
		absolutePath = file.getAbsolutePath();
		
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureImsImportAuthorizationConnector();	
		tegrityInstanceConnectorsScreen.configureImsImportAuthenticationConnector();
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);		
	}
	
	@AfterMethod
	public void logOut() throws Exception {
		browser.pause(1000);
		if(flagForLogOut == 1)
			tegrityInstanceConnectorsScreen.clickLogout();
		if(flagForLogOut == 2)
			tegrityIntroductionScreen.logOut();
	}
	
	@Test(description = "Check Ims Import link is present")
	public void checkImsImportLinkIsPresent() throws Exception {
		
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isImsImportLinkPresent(), "IMS Import link is absent");
	}
	
	@Test(description = "Upload the file first time and prepare OLD users", dependsOnMethods = {"checkImsImportLinkIsPresent"})
	public void prepareOldUsersAndUploadFile() throws Exception {
		
		// upload the file first time with new users, that after second uploading in the next test these users will be OLD
		prepareDataForNewUsers();	
		
		tegrityImsImportScreen = tegrityInstanceDashboardScreen.clickImsImportLink();
		tegrityImsImportScreen.uploadFile(absolutePath);
		
		Assert.verifyTrue(imsImportApplication.isFileUploadSuccessfully(), "The file was not uploaded successfully");
		
		imsImportApplication.deleteMailByBodyContent();
		tegrityImsImportScreen.clickAdminDashboardLink();
	}
	
	@Test(description = "Check XML file was upload successfully", dependsOnMethods = {"checkImsImportLinkIsPresent", "prepareOldUsersAndUploadFile"})
	public void checkFileUploadSuccessfully() throws Exception {
	
		// prepare OLD and NEW users
		prepareTestData();
		
		tegrityImsImportScreen = tegrityInstanceDashboardScreen.clickImsImportLink();
		tegrityImsImportScreen.uploadFile(absolutePath);
		
		Assert.verifyTrue(imsImportApplication.isFileUploadSuccessfully(), "The file was not uploaded successfully");
		
		imsImportApplication.deleteMailByBodyContent();
		tegrityImsImportScreen.clickLogout();
	}
	
	@Test(description = "Check test buttons for Authorization connector under OLD instructo: the data doesn't presentr", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthorizationConnectorUnderOldInstructor() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(instructorId_Old);
		Assert.verifyFalse(result.contains(instructorId_Old), "Instructor username doesn't present");
		Assert.verifyFalse(result.contains(instructorFullName_Old), "Instructor full name doesn't present");
		Assert.verifyFalse(result.contains(courseId1_Old), "Id of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseName1_Old), "Name of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseId2_Old), "Id of the second course doesn't present");
		Assert.verifyFalse(result.contains(courseName2_Old), "Name of the second course doesn't present");
		Assert.verifyFalse(result.contains(instructorRoleType), "Instructor role doesn't right");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(instructorId_Old);
		Assert.verifyFalse(result.contains(instructorId_Old), "Instructor username doesn't present");
		Assert.verifyFalse(result.contains(instructorFullName_Old), "Instructor full name doesn't present");
		Assert.verifyFalse(result.contains(courseId1_Old), "Id of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseName1_Old), "Name of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseId2_Old), "Id of the second course doesn't present");
		Assert.verifyFalse(result.contains(courseName2_Old), "Name of the second course doesn't present");
		Assert.verifyFalse(result.contains(instructorRoleType), "Instructor role doesn't right");		
	}
	
	
	@Test(description = "Check test buttons for Authorization connector under OLD student: the data doesn't present", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthorizationConnectorUnderOldStudent() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(studentId_Old);
		Assert.verifyFalse(result.contains(studentId_Old), "Student username doesn't present");
		Assert.verifyFalse(result.contains(studentFullName_Old), "Student full name doesn't present");
		Assert.verifyFalse(result.contains(courseId1_Old), "Id of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseName1_Old), "Name of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseId2_Old), "Id of the second course doesn't present");
		Assert.verifyFalse(result.contains(courseName2_Old), "Name of the second course doesn't present");
		Assert.verifyFalse(result.contains(studentRoleType), "Student role doesn't right");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(studentId_Old);
		Assert.verifyFalse(result.contains(studentId_Old), "Student username doesn't present");
		Assert.verifyFalse(result.contains(studentFullName_Old), "Student full name doesn't present");
		Assert.verifyFalse(result.contains(courseId1_Old), "Id of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseName1_Old), "Name of the first course doesn't present");
		Assert.verifyFalse(result.contains(courseId2_Old), "Id of the second course doesn't present");
		Assert.verifyFalse(result.contains(courseName2_Old), "Name of the second course doesn't present");
		Assert.verifyFalse(result.contains(studentRoleType), "Student role doesn't right");
	}
	
	@Test(description = "For instructor Tegrity link bahaves correctly", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void testDirectLoginForOldInstructor() {
		
		flagForLogOut = 2;
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructorId_Old, password);
		
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), instructorId_Old);		
		
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(courseName1_Old), "Course " + courseName1_Old + " is absent");
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(courseName2_Old), "Course " + courseName2_Old + " is absent");
	}
	
	@Test(description = "For Student Tegrity link bahaves correctly", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void testDirectLoginForOldStudent() {
		
		flagForLogOut = 2;
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(studentId_Old, password);
		
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), studentId_Old);		
		
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(courseName1_Old), "Course " + courseName1_Old + " is absent");
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(courseName2_Old), "Course " + courseName2_Old + " is absent");
		String sandboxCourse = (studentFullName_Old + " " + "sandbox course");   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");	
	}
	

	
	@Test(description = "Check test buttons for Authentication connector under instructor", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthenticationConnectorUnderInstructor() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(instructorId, password);
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(instructorId, password);
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	
	@Test(description = "Check test buttons for Authentication connector under student", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthenticationConnectorUnderStudent() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthentication(studentId, password);
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthentication(studentId, password);
		Assert.verifyTrue(result.contains("SUCCESS"), "Don't found 'SUCCESS' in the result string");
	}
	

	@Test(description = "Check test buttons for Authorization connector under instructor", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthorizationConnectorUnderInstructor() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(instructorId);
		Assert.verifyTrue(result.contains(instructorId), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseId1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseId2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains(instructorRoleType), "Instructor role doesn't right");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(instructorId);
		Assert.verifyTrue(result.contains(instructorId), "Instructor username doesn't present");
		Assert.verifyTrue(result.contains(instructorFullName), "Instructor full name doesn't present");
		Assert.verifyTrue(result.contains(courseId1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseId2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains(instructorRoleType), "Instructor role doesn't right");		
	
	}
	
	
	@Test(description = "Check test buttons for Authorization connector under student", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void checkTestButtonsForAuthorizationConnectorUnderStudent() throws Exception {
		
		flagForLogOut = 1;
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();
		
		String result = tegrityInstanceConnectorsScreen.getResultOfExternalTestButtonForAuthorization(studentId);
		Assert.verifyTrue(result.contains(studentId), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseId1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseId2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains(studentRoleType), "Student role doesn't right");
		
		result = tegrityInstanceConnectorsScreen.getResultOfInternalTestButtonForAuthorization(studentId);
		Assert.verifyTrue(result.contains(studentId), "Student username doesn't present");
		Assert.verifyTrue(result.contains(studentFullName), "Student full name doesn't present");
		Assert.verifyTrue(result.contains(courseId1), "Id of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseName1), "Name of the first course doesn't present");
		Assert.verifyTrue(result.contains(courseId2), "Id of the second course doesn't present");
		Assert.verifyTrue(result.contains(courseName2), "Name of the second course doesn't present");
		Assert.verifyTrue(result.contains(studentRoleType), "Student role doesn't right");
		
	}
	
	
	
	@Test(description = "Instructor can login into Tegrity and all corses are present", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void testDirectLoginForInstructor() {
		
		flagForLogOut = 2;
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructorId, password);
		
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), instructorFullName);		
		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = (instructorFullName + " " + "sandbox course");
		if(!tegrityIntroductionScreen.isCoursePresent(sandboxCourse)){
			tegrityIntroductionScreen.logOut();
			tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(instructorId, password);
		}
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is absent");
	}
	
	@Test(description = "Student can login into Tegrity and all corses are present", dependsOnMethods = { "checkFileUploadSuccessfully" })
	public void testDirectLoginForStudent() {
		
		flagForLogOut = 2;
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(studentId, password);
		
		Assert.verifyEquals(tegrityIntroductionScreen.getUserNameText(), studentFullName);		
		
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName1), "Course " + courseName1 + " is absent");
		Assert.verifyTrue(tegrityIntroductionScreen.isCoursePresent(courseName2), "Course " + courseName2 + " is absent");
		String sandboxCourse = (studentFullName + " " + "sandbox course");   
		Assert.verifyFalse(tegrityIntroductionScreen.isCoursePresent(sandboxCourse), "Course " + sandboxCourse + " is present");	
	}
	
	private void prepareTestData() throws Exception {
		prepareDataForOldUsers();
		prepareDataForNewUsers();	
	}
	
    private void prepareDataForOldUsers() throws Exception {
		
    	String oldSuffix = getDataFromFile();
		// Create Instructor data from OLD XML file
		instructorId_Old = "Instructor" + oldSuffix;
		instructorFullName_Old = "FirstNameInstructor" + oldSuffix + " LastNameInstructor" + oldSuffix;
		
		// Create Student data from OLD XML file
		studentId_Old = "Student" + oldSuffix;
		studentFullName_Old = "FirstNameStudent" + oldSuffix + " LastNameStudent" + oldSuffix;
		
		// Create Course data from OLD XML file
		String courseSuffix_Old = "Course" + oldSuffix;
		courseId1_Old = "First" + courseSuffix_Old;
		courseName1_Old = "Name " + courseId1_Old;
		courseId2_Old = "Second" + courseSuffix_Old;
		courseName2_Old = "Name " + courseId2_Old;
	}
    
    private String getDataFromFile() {
		
		BufferedReader br = null;
		String oldSuffix = "";
		
		try {
			br = new BufferedReader(new FileReader(fullPathToFile));
			String line;
			while ((line = br.readLine()) != null) {
				if(line.contains("Instructor")){
					int start = line.indexOf("Instructor");
					oldSuffix = line.substring(start+10, start+16);
					Logger.info("The OLD suffix of data is: " + oldSuffix);
					return oldSuffix;
				}
					
			}
		} catch (Exception e) {
			Logger.info("Problems had happens during reading the file " + e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				Logger.info(e.toString());
			}
		}
				
		return oldSuffix;
	}
	
    private void prepareDataForNewUsers() throws Exception {
		
    	String suffix = getRandomString();
		// Create Instructor data and change XML file
		instructorId = "Instructor" + suffix;
		changeDataInFile("Instructor([0-9]+)", instructorId);
		instructorFullName = "FirstNameInstructor" + suffix + " LastNameInstructor" + suffix;
		
		// Create Student data and change XML file
		studentId = "Student" + suffix;
		changeDataInFile("Student([0-9]+)", studentId);
		studentFullName = "FirstNameStudent" + suffix + " LastNameStudent" + suffix;
		
		// Create Course data and change XML file
		String courseSuffix = "Course" + suffix;
		changeDataInFile("Course([0-9]+)", courseSuffix);
		courseId1 = "First" + courseSuffix;
		courseName1 = "Name " + courseId1;
		courseId2 = "Second" + courseSuffix;
		courseName2 = "Name " + courseId2;
	}
    
	private void changeDataInFile(String from, String to) {
		
		String tmpFileName = imsImportApplication.pathToFile + tempFile;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader(new FileReader(fullPathToFile));
			bw = new BufferedWriter(new FileWriter(tmpFileName));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll(from, to);
				bw.write(line + "\n");
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				Logger.info(e.toString());
			}
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				Logger.info(e.toString());
			}
		}

		File oldFile = new File(fullPathToFile);
		oldFile.delete();

		File newFile = new File(tmpFileName);
		newFile.renameTo(oldFile);
		
		Logger.info("The data in xml file was replaced successfully to: " + to);
	}
	
	private String getRandomString() {
		return RandomStringUtils.random(6, false, true);
	}	
}