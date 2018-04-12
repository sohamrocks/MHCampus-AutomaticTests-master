package com.mcgraw.test.automation.tests.sfconnector;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreBase;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceConnectorsScreen;

public class SFConnectorStubScoreTest extends BaseTest {

	private String customerDomain = "http://SFConnectorStubTest.mhcampus.com";
	private String institutionName = "SFConnectorStubTest";
	private String adminUserName = "admin@SFConnectorStubTest.mhcampus.com";
	private String adminPassword = "ddelunrf";
	private String connectorTitle = "SFConnectorStub";
	private String connectorServiceUrl = "http://testcustomer/successfactors";
	private String extendedProps = "{\"ClientId\": \"test\", \"Secret\": \"test\", \"ServerURL\":\"http://google.com\"}";
	private String customerId = "2GVO-GW1P-9TV2";
	private String providerId = "Connect";
	private String courseId = getRandomString();
	private String assignmentId = getRandomNumber();
	private String testUserId = getRandomNumber();
	private String errorDescriptionForLessThenOne = "No grade sent - score indicates non-completed status";
	private String errorDescriptionForLargerThenOne = "out of range - should be between 0 and 1";
	private String errorDescriptionForSuccess = "\"ErrorDescription\":" + null;
	private String emptyErrorCode = "\"ErrorCode\":\"\"";
	private String sfConnectorStatus = "\"Status\":\"updated\"";
	private String requestId = "\"RequestID\":" + "\"Req";
	private String requestSuccessStatusResponse = "\"Status\":\"Success\"";
	private String connectorNameFromResult = "\"Name\":" + "\"" + connectorTitle + "\"";
	private String assignmentIdFromResult = "\"AssignmentID\":" + "\"" + assignmentId + "\"";
	private String assignmentIdMoreThenMax = "\"AssignmentID\":" + null;
	private String errorCodeMoreThenMax = "\"ErrorCode\":\"SCORE_OUT_OF_RANGE\"";
	private String sfFailConnectorStatus = "\"Status\":\"failed\"";
	private String requestFailStatusResponse = "\"Status\":\"Failure\"";

	private MhCampusInstanceConnectorsScreen mhCampusInstanceConnectorsScreen;

	@Autowired
	private GradebookApplication gradebookApplication;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		mhCampusInstanceConnectorsScreen = mhCampusInstanceApplication.loginToMhCampusInstanceAsAdmin(customerDomain,
				institutionName, adminUserName, adminPassword);
		mhCampusInstanceConnectorsScreen.deleteAllConnectors();
		mhCampusInstanceConnectorsScreen.configureCustomGradebookConnector(connectorTitle, connectorServiceUrl,
				extendedProps);

	}

	@AfterClass(description = "Close WebDriver after tests", alwaysRun = true)
	public void closeAllThreads() {
		browser.quit();
		Logger.info("FINISHING TEST CLASS " + this.getClass().getName());
	}

	@Test
	public void gradeBookConnectorTest() {
		Assert.verifyTrue(
				mhCampusInstanceConnectorsScreen.getResultOfInternalTestButtonForGradebook().contains("SUCCESS"),
				"Gradebook Connector was not defined propely");
		mhCampusInstanceConnectorsScreen.clickSaveAndContinueButton();
	}

	@Test(description = "Check score=0 get the correct result", dependsOnMethods = { "gradeBookConnectorTest" })
	public void checkSubmitResultForScoreZero() throws Exception {

		TestScoreBase testScoreBase = gradebookApplication.fillTestScoreForm(customerId, providerId, courseId,
				assignmentId, testUserId, "", "", "0", gradebookApplication.tegrityServiceLocation);
		String result = testScoreBase.getSubmitResult();
		Assert.assertTrue(result.contains(errorDescriptionForLessThenOne), "TestScore form submit failed");
		Assert.assertTrue(result.contains(emptyErrorCode), "TestScore form submit failed");
		Assert.assertTrue(result.contains(assignmentIdFromResult), "AssignemntID wasn't found in response");
		Assert.assertTrue(result.contains(connectorNameFromResult), "Connector Title wasn't found in response");
		Assert.assertTrue(result.contains(sfConnectorStatus), "Status from connector wasn't found in response");
		Assert.assertTrue(result.contains(requestId), "RequestID wasn't found in response");
		Assert.assertTrue(result.contains(requestSuccessStatusResponse), "TestScore form submit failed");
	}

	@Test(description = "Check score<1 get the correct result", dependsOnMethods = { "checkSubmitResultForScoreZero" })
	public void checkSubmitResultForScoreLessThenOne() throws Exception {

		TestScoreBase testScoreBase = gradebookApplication.fillTestScoreForm(customerId, providerId, courseId,
				assignmentId, testUserId, "", "", "0.55", gradebookApplication.tegrityServiceLocation);
		String result = testScoreBase.getSubmitResult();
		Assert.assertTrue(result.contains(errorDescriptionForLessThenOne), "TestScore form submit failed");
		Assert.assertTrue(result.contains(emptyErrorCode), "TestScore form submit failed");
		Assert.assertTrue(result.contains(assignmentIdFromResult), "AssignemntID wasn't found in response");
		Assert.assertTrue(result.contains(connectorNameFromResult), "Connector Title wasn't found in response");
		Assert.assertTrue(result.contains(sfConnectorStatus), "Status from connector wasn't found in response");
		Assert.assertTrue(result.contains(requestId), "RequestID wasn't found in response");
		Assert.assertTrue(result.contains(requestSuccessStatusResponse), "TestScore form submit failed");
	}

	@Test(description = "Check score>1 get the correct result", dependsOnMethods = {
			"checkSubmitResultForScoreLessThenOne" })
	public void checkSubmitResultForScoreLargerThenOne() throws Exception {

		TestScoreBase testScoreBase = gradebookApplication.fillTestScoreForm(customerId, providerId, courseId,
				assignmentId, testUserId, "", "", "1.55", gradebookApplication.tegrityServiceLocation);
		String result = testScoreBase.getSubmitResult();
		Assert.assertTrue(result.contains(errorDescriptionForLargerThenOne), "TestScore form submit failed");
		Assert.assertTrue(result.contains(errorCodeMoreThenMax), "TestScore form submit failed");
		Assert.assertTrue(result.contains(assignmentIdMoreThenMax), "AssignemntID wasn't found in response");
		Assert.assertTrue(result.contains(connectorNameFromResult), "Connector Title wasn't found in response");
		Assert.assertTrue(result.contains(sfFailConnectorStatus), "Status from connector wasn't found in response");
		Assert.assertTrue(result.contains(requestId), "RequestID wasn't found in response");
		Assert.assertTrue(result.contains(requestFailStatusResponse), "TestScore form submit failed");
	}

	@Test(description = "Check score=1 get the correct result", dependsOnMethods = {
			"checkSubmitResultForScoreLargerThenOne" })
	public void checkSubmitResultForScoreOne() throws Exception {

		TestScoreBase testScoreBase = gradebookApplication.fillTestScoreForm(customerId, providerId, courseId,
				assignmentId, testUserId, "", "", "1", gradebookApplication.tegrityServiceLocation);
		String result = testScoreBase.getSubmitResult();
		Assert.assertTrue(result.contains(requestSuccessStatusResponse), "TestScore form submit failed");
		Assert.assertTrue(result.contains(errorDescriptionForSuccess), "TestScore form submit failed");
		Assert.assertTrue(result.contains(emptyErrorCode), "AssignemntID wasn't found in response");
		Assert.assertTrue(result.contains(connectorNameFromResult), "Connector Title wasn't found in response");
		Assert.assertTrue(result.contains(sfConnectorStatus), "Status from connector wasn't found in response");
		Assert.assertTrue(result.contains(requestId), "RequestID wasn't found in response");
	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

	private String getRandomNumber() {
		return RandomStringUtils.randomNumeric(5);
	}

}
