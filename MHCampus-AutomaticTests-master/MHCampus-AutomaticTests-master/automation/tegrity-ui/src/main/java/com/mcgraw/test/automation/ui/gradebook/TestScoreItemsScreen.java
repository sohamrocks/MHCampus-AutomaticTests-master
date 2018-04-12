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

	public void setIsStudentViewable(Boolean state) {
		new CheckBox(isStudentViewable).setChecked(state);
	}

	public void setIncludedInGrade(Boolean state) {
		new CheckBox(isIncludedInGrade).setChecked(state);
	}
}
