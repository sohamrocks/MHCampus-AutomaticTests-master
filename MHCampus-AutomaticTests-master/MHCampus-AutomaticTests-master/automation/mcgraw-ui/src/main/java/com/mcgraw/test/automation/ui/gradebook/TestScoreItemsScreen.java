package com.mcgraw.test.automation.ui.gradebook;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("/")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//*[contains(text(),'Is Student Viewable')]")))
public class TestScoreItemsScreen extends TestScoreBase {

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtAssignmentTitle"))
	Input assignmentTitleInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtCategory"))
	Input categoryInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtDescription"))
	Input descriptionInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtStartDateTime"))
	Input startDateInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtDueDateTime"))
	Input dueDateInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ddlScoreType"))
	Select scoreType;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtScorePossible"))
	Input scorePossibleInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cbIsExtraCredit"))
	Element isExtraCredit;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cbIsStudentViewable"))
	Element isStudentViewable;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cbIsIncludedInGrade"))
	Element isIncludedInGrade;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtModuleID"))
	Input moduleIDInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnGetModules"))
	Element getModulesBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtModuleID"))
	Input ModuleInput;	
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtToolID"))
	Input toolIdInput;	
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "cbAsync"))
	CheckBox asyncCb;	
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "txtResult"))
	Input result;	

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnGetAsyncResults"))
	Element getAsyncResultsBtn;		
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "btnGetAssignmentsMetaData"))
	Element getAssignmentMetaDataBtn;		
	
	public TestScoreItemsScreen(Browser browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public void typeAssignmentTitle(String assignmentTitle) {
		assignmentTitleInput.typeValue(assignmentTitle);
	}

	public void typeCategory(String category) {
		categoryInput.typeValue(category);
	}

	public void typeDescription(String description) {
		descriptionInput.typeValue(description);
	}

	public void typeStartDate(String date) {
		startDateInput.typeValue(date);
	}

	public void typeDueDate(String date) {
		dueDateInput.typeValue(date);
	}

	public void chooseScoreType(String option) {
		scoreType.selectOptionByName(option);
	}

	public void typeScorePossible(String scorePossible) {
		scorePossibleInput.typeValue(scorePossible);
	}
	
	public void typeModule(String module) {
		moduleIDInput.typeValue(module);
	}	

	public void setIsStudentViewable(Boolean state) {
		new CheckBox(isStudentViewable).setChecked(state);
	}

	public void setIncludedInGrade(Boolean state) {
		new CheckBox(isIncludedInGrade).setChecked(state);
	}
	
	public void checkAsync() {
		asyncCb.setChecked(true);		
	}
	
	public String getResult() {	
		return result.getAttribute("value");
	}		

	public boolean isResultContainsRequest()
	{
		String result="";
		result = getResult();
		return result.contains("Req");
	}		
	
	public void getAsyncResults() {
		getAsyncResultsBtn.click();		
	}		
	
	public void getAssignmentsMetaData() {
		getAssignmentMetaDataBtn.click();		
	}	
	
	public int getInternalAssignmentIdForCanvasCourse() {
		String result = getSubmitResult();
		int internalAssignmentId = Integer.parseInt(result.substring(38, 46));
		return internalAssignmentId;
	}

	public void setToolID(String toolId) {
		toolIdInput.typeValue(toolId);		
	}

	public String getModuleID() {
		getModulesBtn.click();
		return "";
	}	
}
