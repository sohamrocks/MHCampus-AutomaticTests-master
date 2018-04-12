package com.mcgraw.test.automation.ui.applications;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.gradebook.TestScoreItemsScreen;
import com.mcgraw.test.automation.ui.gradebook.TestScoreScreen;

public class GradebookApplication {

	@Value("${tegrity.scorableitem.url}")
	public String testScorableItemFormUrl;

	@Value("${tegrity.score.url}")
	public String testScoreFormUrl;

	@Value("${tegrity.servicelocation}")
	public String tegrityServiceLocation;

	private TestScoreItemsScreen testScoreItemsScreen;
	
	private TestScoreScreen testScoreScreen;

	Browser browser;

	public GradebookApplication(Browser browser) {
		this.browser = browser;
	}

	public TestScoreItemsScreen fillTestScorableItemForm(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}

	public TestScoreItemsScreen fillTestScorableItemFormDI(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.setToolID(toolId);
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}
	
	public TestScoreScreen fillTestScoreForm(String customerId,
			String providerId, String courseId, String assignmentId,
			String userId, String comment, String dateSubmitted,
			String scoreReceived, String serviceLocation) throws Exception {

		Logger.info("Filling TestScore form...");
		browser.manage().deleteAllCookies();
		testScoreScreen = browser.openScreen(testScoreFormUrl,
				TestScoreScreen.class);
		testScoreScreen.typeServiceLocation(serviceLocation);
		testScoreScreen.typeCustomerId(customerId);
		testScoreScreen.typeProviderId(providerId);
		testScoreScreen.typeAssignmentId(assignmentId);
		testScoreScreen.typeCourseId(courseId);
		testScoreScreen.typeUserId(userId);
		testScoreScreen.typeComment(comment);
		testScoreScreen.typeDateSubmitted(dateSubmitted);
		testScoreScreen.typeScoreReceived(scoreReceived);
		testScoreScreen.submitForm();
		testScoreScreen.saveState();
		browser.makeScreenshot();
		return testScoreScreen;
	}

	public TestScoreScreen fillTestScoreFormDI(String customerId,
			String providerId, String courseId, String assignmentId,
			String userId, String comment, String dateSubmitted,
			String scoreReceived, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScore form...");
		browser.manage().deleteAllCookies();
		testScoreScreen = browser.openScreen(testScoreFormUrl,
				TestScoreScreen.class);
		testScoreScreen.typeServiceLocation(serviceLocation);
		testScoreScreen.typeCustomerId(customerId);
		testScoreScreen.typeProviderId(providerId);
		testScoreScreen.typeAssignmentId(assignmentId);
		testScoreScreen.typeCourseId(courseId);
		testScoreScreen.typeUserId(userId);
		testScoreScreen.typeComment(comment);
		testScoreScreen.typeDateSubmitted(dateSubmitted);
		testScoreScreen.typeScoreReceived(scoreReceived);
		testScoreScreen.typeToolID(toolId);		
		testScoreScreen.submitForm();
		testScoreScreen.saveState();
		browser.makeScreenshot();
		return testScoreScreen;
	}

	public TestScoreItemsScreen fillTestScorableItemFormAsync(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.checkAsync();
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}

	public TestScoreItemsScreen fillTestScorableItemFormDIAsync(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.setToolID(toolId);
		testScoreItemsScreen.checkAsync();		
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}
	
	public TestScoreScreen fillTestScoreFormAsync(String customerId,
			String providerId, String courseId, String assignmentId,
			String userId, String comment, String dateSubmitted,
			String scoreReceived, String serviceLocation) throws Exception {

		Logger.info("Filling TestScore form...");
		browser.manage().deleteAllCookies();
		testScoreScreen = browser.openScreen(testScoreFormUrl,
				TestScoreScreen.class);
		testScoreScreen.typeServiceLocation(serviceLocation);
		testScoreScreen.typeCustomerId(customerId);
		testScoreScreen.typeProviderId(providerId);
		testScoreScreen.typeAssignmentId(assignmentId);
		testScoreScreen.typeCourseId(courseId);
		testScoreScreen.typeUserId(userId);
		testScoreScreen.typeComment(comment);
		testScoreScreen.typeDateSubmitted(dateSubmitted);
		testScoreScreen.typeScoreReceived(scoreReceived);
		testScoreScreen.checkAsync();		
		testScoreScreen.submitForm();
		testScoreScreen.saveState();
		browser.makeScreenshot();
		return testScoreScreen;
	}

	public TestScoreScreen fillTestScoreFormDIAsync(String customerId,
			String providerId, String courseId, String assignmentId,
			String userId, String comment, String dateSubmitted,
			String scoreReceived, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScore form...");
		browser.manage().deleteAllCookies();
		testScoreScreen = browser.openScreen(testScoreFormUrl,
				TestScoreScreen.class);
		testScoreScreen.typeServiceLocation(serviceLocation);
		testScoreScreen.typeCustomerId(customerId);
		testScoreScreen.typeProviderId(providerId);
		testScoreScreen.typeAssignmentId(assignmentId);
		testScoreScreen.typeCourseId(courseId);
		testScoreScreen.typeUserId(userId);
		testScoreScreen.typeComment(comment);
		testScoreScreen.typeDateSubmitted(dateSubmitted);
		testScoreScreen.typeScoreReceived(scoreReceived);
		testScoreScreen.typeToolID(toolId);	
		testScoreScreen.checkAsync();			
		testScoreScreen.submitForm();
		testScoreScreen.saveState();
		browser.makeScreenshot();
		return testScoreScreen;
	}
	
	// Added by AleksandrY
	public String fillTestScorableItemFormGetModuleID(String customerId,
			String providerId, String courseId, String serviceLocation, String moduleTitle) throws Exception{

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.clickSeeAvailableModules();
		browser.makeScreenshot();
		return testScoreItemsScreen.parseModuleIdFromResult(moduleTitle);
	}

	public void getAsyncInScorableResult()
	{
		testScoreItemsScreen.getAsyncResults();
	}

	public void getAsyncInScoreResult()
	{
		testScoreScreen.getAsyncResults();
	}	
	
	public static String getRandomStartDate() {
		Random rand = new Random();
		return Integer.toString((rand.nextInt(2) + 10)) + "/" + Integer.toString(rand.nextInt(20) + 10) + "/"
				+ Integer.toString(2000 + rand.nextInt(13));
	}

	public static String getRandomDueToDate() {
		Random rand = new Random();
		return Integer.toString((rand.nextInt(2) + 10)) + "/" + Integer.toString(rand.nextInt(20) + 10) + "/" + Integer.toString(2020 + rand.nextInt(10));
	}
	
	public static String getRandomDateSubmitted() {
		Random rand = new Random();
		return Integer.toString((rand.nextInt(2) + 10)) + "/" + Integer.toString(rand.nextInt(20) + 10) + "/" + Integer.toString(2015 + rand.nextInt(4));
	}	
	
	public static String getRandomScore() {
		String random = RandomStringUtils.randomNumeric(2);
		if (random.startsWith("0")) {
			random = random.replaceFirst("0", "");
		}
		return random;
	}

	public TestScoreItemsScreen fillTestScorableItemFormDIWithModuleId(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, String moduleId, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.typeModule(moduleId);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.setToolID(toolId);
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}
	
	public TestScoreItemsScreen fillTestScorableItemFormDIWithAsyncAndModuleId(String customerId,
			String providerId, String courseId, String assignmentId,
			String assignmentTitle, String category, String description,
			String startDate, String dueToDate, String scoreType,
			String scorePossible, String moduleId, Boolean isStudentViewable,
			Boolean isIncludedInGrade, String serviceLocation, String toolId) throws Exception {

		Logger.info("Filling TestScorableItem form...");
		browser.manage().deleteAllCookies();
		try{
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}catch(Exception e){
			Logger.info("Curent URL is: " + browser.getCurrentUrl());
			browser.makeScreenshot();
			e.toString();
			Logger.info("Try again to open 'TestScoreItemsScreen'...");
			browser.manage().deleteAllCookies();
			testScoreItemsScreen = browser.openScreen(testScorableItemFormUrl,
					TestScoreItemsScreen.class);
		}
		
		testScoreItemsScreen.typeServiceLocation(serviceLocation);
		testScoreItemsScreen.typeCustomerId(customerId);
		testScoreItemsScreen.typeProviderId(providerId);
		testScoreItemsScreen.typeCourseId(courseId);
		testScoreItemsScreen.typeAssignmentId(assignmentId);
		testScoreItemsScreen.typeAssignmentTitle(assignmentTitle);
		testScoreItemsScreen.typeCategory(category);
		testScoreItemsScreen.typeDescription(description);
		testScoreItemsScreen.typeStartDate(startDate);
		testScoreItemsScreen.typeDueDate(dueToDate);
		testScoreItemsScreen.typeScorePossible(scorePossible);
		testScoreItemsScreen.chooseScoreType(scoreType);
		testScoreItemsScreen.typeModule(moduleId);
		testScoreItemsScreen.setIsStudentViewable(isStudentViewable);
		testScoreItemsScreen.setIncludedInGrade(isIncludedInGrade);
		testScoreItemsScreen.setToolID(toolId);
		testScoreItemsScreen.checkAsync();
		testScoreItemsScreen.submitForm();
		testScoreItemsScreen.saveState();
		browser.makeScreenshot();
		return testScoreItemsScreen;
	}
}
