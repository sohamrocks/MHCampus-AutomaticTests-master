package com.mcgraw.test.automation.tests.canvas;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.GradebookApplication;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;

public class GetAllAssignments extends BaseTest {

	private String connectTool = "Connect";
	private String simnetTool = "Simnet";
	private String connectCourseCustomerID = "3GG7-CJAJ-DFGZ";
	private String simnetCourseCustomerId = "L0XB-3GNS-MNPU";
	private String originalConnectDICourseId = "8957972b0a03a74fd78703bd825f23d8";
	private String copiedConnectDICourseId = "4f1f38ac292d73d8f5b1bddd6220f9ae";
	//private String copiedConnectDICourseId = "8957972b0a03a74fd78703bd825f23d8";
	private String originalSimnetDICourseId = "2277796";
	private String copiedSimnetDICourseId = "2281839";
	private String serviceLocation = "http://mhaairs-aws-qa.tegrity.com";
	private List<Assignment> assignmentList = null;
	private List<Assignment> copiedAssignmentList = null;

	@Autowired
	private GradebookApplication gradebookApplication;
	private TestScoreItemsScreen testScoreItemsScreen = new TestScoreItemsScreen(browser);

	@AfterClass(description = "Close WebDriver after tests", alwaysRun = true)
	public void closeAllThreads() {
		browser.quit();
		Logger.info("FINISHING TEST CLASS " + this.getClass().getName());
	}

	@Test(description = "Get all assignment from the original course - Connect")
	public void getAllConnectAssignmentsFromParentCourse() throws Exception {
		Logger.info("Getting all the Connect assignments from the parent(original) course");
		testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(connectCourseCustomerID, connectTool,
				originalConnectDICourseId, "", "", "", "", "", "", "Points", "", false, false, serviceLocation,
				connectTool);
		testScoreItemsScreen.getAssignmentsMetaData();
		String result = testScoreItemsScreen.getResult();
		result = toPureJson(result);
		int assignmentListSize = assignmentList.size();
		System.out.println(assignmentListSize);
		for (int i = 0; i <= assignmentListSize - 1; i++) {
			String toolId = assignmentList.get(i).ToolID.toString();
			Logger.info("Checking assignment #" + (i) + " for toolID:" + toolId);
			Assert.assertTrue((toolId != null), "ToolId of original assignments is null. Check what is the problem.");
		}
		Assert.assertTrue((result.contains("connect course")),
				"This is not a Connect course. Check what is the problem.");
	}

	@Test(description = "Get all assignment from the copied course - Connect", dependsOnMethods = {
			"getAllConnectAssignmentsFromParentCourse" })
	public void getAllConnectAssignmentsFromCopiedCourse() throws Exception {
		Logger.info("Getting all the Connect assignments from the copied course");
		testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(connectCourseCustomerID, connectTool,
				copiedConnectDICourseId, "", "", "", "", "", "", "Points", "", false, false, serviceLocation,
				connectTool);
		testScoreItemsScreen.getAssignmentsMetaData();
		String result = testScoreItemsScreen.getResult();
		result = toPureJson(result);
		int copiedAssignmentListSize = copiedAssignmentList.size();
		Logger.info("number"+copiedAssignmentListSize);
		for (int i = 0; i <= copiedAssignmentListSize - 1; i++) {
			String copiedCourseparentToolId = copiedAssignmentList.get(i).parentToolId.toString();
			String copiedCourseToolId = copiedAssignmentList.get(i).ToolID;
			Logger.info("Checking assignment #" + i + " for parentToolID:" + copiedCourseparentToolId);
			Assert.assertTrue((copiedCourseparentToolId != null && copiedCourseToolId == null),
					"parentToolId of copied assignments is null. Check what is the problem.");
		}
		Assert.assertTrue((result.contains("connect ")),
				"This is not a Connect course. Check what is the problem.");

	}

	@Test(description = "Get all assignment from the original course - Simnet", dependsOnMethods = {
			"getAllConnectAssignmentsFromCopiedCourse" })
	public void getAllSimnetAssignmentsFromParentCourse() throws Exception {
		Logger.info("Getting all the Simnet assignments from the parent(original) course");
		testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(simnetCourseCustomerId, simnetTool,
				originalSimnetDICourseId, "", "", "", "", "", "", "Points", "", false, false, serviceLocation,
				simnetTool);
		testScoreItemsScreen.getAssignmentsMetaData();
		String result = testScoreItemsScreen.getResult();
		result = toPureJson(result);
		int assignmentListSize = assignmentList.size();
		for (int i = 0; i <= assignmentListSize - 1; i++) {
			String toolId = assignmentList.get(i).ToolID.toString();
			Logger.info("Checking assignment #" + (i) + " for toolID:" + toolId);
			Assert.assertTrue((toolId != null), "ToolId of original assignments is null. Check what is the problem.");
		}
		Assert.assertTrue((result.contains("Simnet")), "This is not a Simnet course. Check what is the problem.");
	}

	@Test(description = "Get all assignment from the copied course - Simnet", dependsOnMethods = {"getAllSimnetAssignmentsFromParentCourse"})
	public void getAllSimnetAssignmentsFromCopiedCourse() throws Exception {
		testScoreItemsScreen = gradebookApplication.fillTestScorableItemFormDI(simnetCourseCustomerId, simnetTool,
				copiedSimnetDICourseId, "", "", "", "", "", "", "Points", "", false, false, serviceLocation,
				simnetTool);
		testScoreItemsScreen.getAssignmentsMetaData();
		String result = testScoreItemsScreen.getResult();
		result = toPureJson(result);
		int copiedAssignmentListSize = copiedAssignmentList.size();
		for (int i = 0; i <= copiedAssignmentListSize - 1; i++) {
			String copiedCourseparentToolId = copiedAssignmentList.get(i).parentToolId.toString();
			String copiedCourseToolId = copiedAssignmentList.get(i).ToolID;
			Logger.info("Checking assignment #" + i + " for parentToolID:" + copiedCourseparentToolId);
			Assert.assertTrue((copiedCourseparentToolId != null && copiedCourseToolId == null),
					"parentToolId of copied assignments is null. Check what is the problem.");
		}
		Assert.assertTrue((result.contains("Simnet")),
				"This is not a Simnet course. Check what is the problem.");

	}

	private String toPureJson(String result) {
		result = result.substring(4);
		try {
			toolIdParser(result);
			if (assignmentList.get(0).ToolID != null) {
			} else {
				parentToolIdParser(result);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Assignment> toolIdParser(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		assignmentList = mapper.readValue(json, new TypeReference<List<Assignment>>() {
		});
		System.out.println("Grabbing assignments Tool Ids from the original course:");
		for (Assignment assignment : assignmentList) {
			System.out.println("toolId: " + assignment.ToolID);
		}
		return assignmentList;
	}

	private List<Assignment> parentToolIdParser(String json)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		copiedAssignmentList = mapper.readValue(json, new TypeReference<List<Assignment>>() {
		});
		System.out.println("Grabbing assignments PARENT Tool Ids from the copied course:");
		for (Assignment assignment : copiedAssignmentList) {
			System.out.println("parentToolId: " + assignment.parentToolId);
			System.out.println("ToolId: " + assignment.ToolID);
		}
		return copiedAssignmentList;
	}

	public static class Assignment {
		@JsonProperty("toolId")
		public String ToolID;
		public String category;
		public String dueDate;
		public String lmsId;
		public int maxPoints;
		@JsonProperty("parentToolId")
		public String parentToolId;
		public String scoreType;
		public String startDate;
		public String title;

	}

}
