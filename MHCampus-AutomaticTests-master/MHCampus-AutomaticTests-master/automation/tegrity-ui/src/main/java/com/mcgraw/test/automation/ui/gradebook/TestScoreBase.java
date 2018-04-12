package com.mcgraw.test.automation.ui.gradebook;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils.TestCondition;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

public class TestScoreBase extends Screen {

	//private static String SUCCESS_BB_UPDATE = "Blackboard (auto):updated";
	private static String SUCCESS_BB_UPDATE = "Blackboard:updated";
	private static String SUCCESS_STATUS = "Success";
	private static String SUCCESS_ANGEL_UPDATE = "Angel:updated";
	private static String SUCCESS_CANVAS_UPDATE = "Canvas:updated";
	private static String SUCCESS_MOODLE_UPDATE = "Moodle:updated";
	private static String SUCCESS_D2L_UPDATE = "D2L:updated";
	private static String SUCCESS_SAKAI_UPDATE = "Sakai:updated";
	private static String SUCCESS_ECOLLEGE_UPDATE = "eCollege:updated";
	private static String VALUE = "value";

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtCustomerID"))
	Input customerIdInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtProviderID"))
	Input providerIdInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtCourseID"))
	Input courseIdInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtAssignmentID"))
	Input assignmentIdInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnSubmit"))
	Element submitBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtResult"))
	Input resultInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnSave"))
	Element saveBtn;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtState"))
	Input stateInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtServiceLocation"))
	Input serviceLocationInput;

	public TestScoreBase(Browser browser) {
		super(browser);
	}

	public void typeCustomerId(String customerId) {
		customerIdInput.typeValue(customerId);
	}

	public void typeProviderId(String providerId) {
		providerIdInput.typeValue(providerId);
	}

	public void typeCourseId(String courseId) {
		courseIdInput.typeValue(courseId);
	}

	public void typeAssignmentId(String assignmentId) {
		assignmentIdInput.typeValue(assignmentId);
	}

	public void typeServiceLocation(String serviceLocation) {
		serviceLocationInput.clearAndTypeValue(serviceLocation);
	}

	public String getSubmitResult() {
		return resultInput.getAttribute("value");
	}

	public String getSubmitState() {
		return stateInput.getAttribute("value");
	}

	public boolean formSubmitIsSuccessfullForBlackboard() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_BB_UPDATE);
	}

	public boolean formSubmitIsSuccessfullForCanvas() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_CANVAS_UPDATE);
	}

	public boolean formSubmitIsSuccessfullForAngel() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_ANGEL_UPDATE);
	}

	public boolean formSubmitIsSuccessfullForMoodle() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_MOODLE_UPDATE);
	}

	public boolean formSubmitIsSuccessfullForD2l() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_D2L_UPDATE);
	}

	public boolean formSubmitIsSuccessfullForSakai() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_SAKAI_UPDATE);
	}
	
	public boolean formSubmitIsSuccessfullForEcollege() throws Exception {
		return formSubmitIsSuccessfull(SUCCESS_ECOLLEGE_UPDATE);
	}

	private boolean formSubmitIsSuccessfull(String identifier) throws Exception {
		return getSubmitResult().contains(SUCCESS_STATUS)
				&& getSubmitResult().contains(identifier);
	}

	public void submitForm() throws Exception {
		submitBtn.click();
		waitForResultToAppear();
	}

	public void saveState() throws Exception {
		saveBtn.click();
		waitForStateToAppear();
	}

	private class ResultAppear implements TestCondition {
		public Boolean condition() throws Exception {
			return (resultInput.getAttribute(VALUE).length() > 0);
		}
	}

	private class StateAppear implements TestCondition {
		public Boolean condition() throws Exception {
			return (stateInput.getAttribute(VALUE).length() > 0);
		}
	}

	private void waitForResultToAppear() throws Exception {
		WaitForConditionUtils.WaitForCondition(new ResultAppear(), 20000,
				"No results apeear in 20 seconds!");
		Logger.info("Value of Result field is: " + getSubmitResult());
	}

	private void waitForStateToAppear() throws Exception {
		WaitForConditionUtils.WaitForCondition(new StateAppear(), 10000,
				"No results apeear in 10 seconds!");
		Logger.info("Value of State field is: " + getSubmitState());
	}
}