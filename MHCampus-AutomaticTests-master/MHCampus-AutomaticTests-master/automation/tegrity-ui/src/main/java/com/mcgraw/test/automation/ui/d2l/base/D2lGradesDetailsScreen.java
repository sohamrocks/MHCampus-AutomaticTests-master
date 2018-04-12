package com.mcgraw.test.automation.ui.d2l.base;

import org.openqa.selenium.By;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;

public abstract class D2lGradesDetailsScreen extends Screen {

	protected static String SLASH = "/";
	protected static String assignmentPrefix = "title";
	protected static String categoryPrefix = "category";

	public D2lGradesDetailsScreen(Browser browser) {
		super(browser);
	}

	public String getCategory() {
		return getFullTextOfElementByPrefix(categoryPrefix);

	}

	public String getAssignmentTitle() {
		return getFullTextOfElementByPrefix(assignmentPrefix);
	}

	public String getScorePossible() {
		return getPoints().substring(getPoints().indexOf(SLASH) + 1,
				getPoints().length()).trim();
	}

	public String getScoreReceived() {
		return getPoints().substring(0, getPoints().indexOf(SLASH)).trim();
	}

	public int getCountOfAssignments() {
		return getCountElements(assignmentPrefix);
	}

	public int getCountOfCategory() {
		return getCountElements(categoryPrefix);
	}

	protected String getFullTextOfElementByPrefix(String prefix) {
		int categoryAndAssignmentElements = browser.getElementsCount(By
				.xpath(getCategoryAndAssignmentXpath()));
		String fullName;
		for (int i = 1; i <= categoryAndAssignmentElements; ++i) {
			Element categoryOrAssignment = browser.findElement(By.xpath("("
					+ getCategoryAndAssignmentXpath() + ")[" + i + "]"));
			if ((fullName = categoryOrAssignment.getText()).startsWith(prefix)) {
				return fullName;
			}
		}
		return null;
	}

	protected int getCountElements(String prefix) {
		int categoryAndAssignmentElements = browser.getElementsCount(By
				.xpath(getCategoryAndAssignmentXpath()));
		int countOfelements = 0;
		for (int i = 1; i <= categoryAndAssignmentElements; ++i) {
			Element categoryOrAssignment = browser.findElement(By.xpath("("
					+ getCategoryAndAssignmentXpath() + ")[" + i + "]"));
			if (categoryOrAssignment.getText().startsWith(prefix)) {
				countOfelements++;
			}
		}
		return countOfelements;
	}

	protected abstract String getPoints();

	protected abstract String getCategoryAndAssignmentXpath();
}
