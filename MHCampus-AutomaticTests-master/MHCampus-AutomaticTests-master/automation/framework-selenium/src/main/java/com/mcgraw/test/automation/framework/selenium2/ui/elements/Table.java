package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;

/**
 * @author Natallia_Mironchyk
 * 
 *         class for work with tables
 * 
 */
public class Table extends Element {
	protected final String ROW_XPATH_PATTERN = "//tbody/tr[%d]";

	protected final String ROW_ACTION_PATH = "/..//div[@class='kr_select_wrapper actions_select_wrapper']/a";

	protected final String SELECT_ALL_CHECKBOX_LOCATOR = "//thead/tr/th[1]/..//input";
	
	public static String SHOW_DETAILS_IMG_LOCATOR_PATTERN="//tbody//a[text()='%s']/ancestor::tr//img[@class='openDetailsImg']";

	protected static final String APPROVAL_STATUS_PATH = "/..//img";

	protected static final String DETAILS_LINK_PATH = "/..//a[contains(@class,'detailsLink')]";

	protected static final String APP_STATUS_PATH_PATTERN = "//tbody/tr/td[3]/span/a[text()='%s']/ancestor::tr//img";

	protected static final String APP_SITES_PATH_PATTERN = "//tbody//a[text()='%s']/ancestor::tr//*[@class='site_detail']";

	protected static final String READONLY_PATH_PATTERN = "//tbody//a[text()='%s']/..//span";

	protected static final String ACTION_PATH_PATTERN = "//tbody/tr/td[3]/span/a[text()='%s']/ancestor::tr/td[5]/div/a";

	protected static final String READONLY_VALUE = "READONLY:";

	public Table(WebElement webElement) {
		super(webElement);
	}

	public void setCheckBox(int row, boolean value) {
		if (row > getRowsCount()) {
			throw new IllegalArgumentException();
		}
		String pathItem = String.format(ROW_XPATH_PATTERN + "/..//input", row);
		CheckBox checkbox = new CheckBox(this.findElement(By.xpath(pathItem))
				.getWebElement());
		checkbox.setChecked(value);
	}

	public void setAllCheckBox() {
		CheckBox checkbox = (CheckBox) this.findElement(By
				.xpath(SELECT_ALL_CHECKBOX_LOCATOR));
		checkbox.setChecked(true);
	}

	/**
	 * make the selected action with the tag situated in the specified row
	 * 
	 * @param row
	 * @param action
	 */
	public void selectRowActionByRow(Browser browser, int row, String action) {

		String pathItem = String.format(ROW_XPATH_PATTERN + ROW_ACTION_PATH,
				row);
		Element element = this.findElement(By.xpath(pathItem));
		ElementWithDropDownMenu dropDownMenu = new ElementWithDropDownMenu(
				element.getWebElement());
		dropDownMenu.selectMenuItem(browser, action);
	}

	public void selectRowActionByName(Browser browser, String tagName, String action) {

		String pathItem = String.format(ACTION_PATH_PATTERN, tagName);
		Element element = this.findElement(By.xpath(pathItem));
		ElementWithDropDownMenu dropDownMenu = new ElementWithDropDownMenu(
				element.getWebElement());
		dropDownMenu.selectMenuItem(browser, action);
	}

	public boolean isRowActionPresent(Browser browser, int row, String action) {
		String pathItem = String.format(ROW_XPATH_PATTERN + ROW_ACTION_PATH,
				row);
		Element element = this.findElement(By.xpath(pathItem));
		ElementWithDropDownMenu dropDownMenu = new ElementWithDropDownMenu(
				element.getWebElement());
		return dropDownMenu.isMenuItemPresent(browser, action);
	}

	/**
	 * @return number of table rows
	 */

	public int getRowsCount() {
		int size = Browser.getBrowser().findElements(By.xpath("//tbody/tr")).size();
		return size;
	}

	/**
	 * @param row
	 * @return status of tag situated in specified row
	 */
	public String getTagStatusByRowNumber(int row) {
		if (row > getRowsCount()) {
			throw new IllegalArgumentException();
		}
		String pathItem = String.format(ROW_XPATH_PATTERN
				+ APPROVAL_STATUS_PATH, row);
		Element element = this.findElement(By.xpath(pathItem));
		String status = element.getAttribute("title");
		return status;
	}

	/**
	 * @param tagName
	 * @return status of tag with specified name
	 */
	public String getTagStatusByName(String tagName) {
		String pathItem = String.format(APP_STATUS_PATH_PATTERN, tagName);
		Element element = this.findElement(By.xpath(pathItem));
		String status = element.getAttribute("title");
		return status;
	}
	
	public void clickStatusIcon(){
		
	}

	/**
	 * extend current view of tag in specified row
	 * 
	 * @param row
	 */
	public void showDetailsInRow(int row) {
		if (row > getRowsCount()) {
			throw new IllegalArgumentException();
		}
		String pathItem = String.format(ROW_XPATH_PATTERN + DETAILS_LINK_PATH,
				row);
		Element detailsLink = Browser.getBrowser().findElement(By.xpath(pathItem));
		detailsLink.click();
	}

	/**
	 * @param tagName
	 * @return sites names which need approval for specified tag
	 */
	public String getSitesForApproval(String tagName)
			throws NoSuchElementException {
		if (isTagPresent(tagName)) {
			String xpathExpression = String.format(APP_SITES_PATH_PATTERN,
					tagName);
			showDetailsForTag(tagName);
			Element element = Browser.getBrowser().findElement(By.xpath(xpathExpression));
			String sites = element.getText().substring(4).trim();
			return sites;
		}
		throw new NoSuchElementException("The tag with name: " + tagName
				+ " doesn't exist");
	}

	/**
	 * method check if tag presents in the table
	 * 
	 * @param tagName
	 * @return
	 */
	public boolean isTagPresent(String tagName) {
		boolean result = false;

		try {
			Element curItem = Browser.getBrowser().findElement(By.linkText(tagName));
			result = curItem.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
		return result;
	}

	public boolean isTagReadOnly(String tagName) {
		boolean result = true;
		try {
			String xpathExpression = String.format(READONLY_PATH_PATTERN,
					tagName);
			Element element = Browser.getBrowser().waitForElement(By.xpath(xpathExpression));
			if (!READONLY_VALUE.equalsIgnoreCase(element.getText())) {
				result = false;
			}
		} catch (NoSuchElementException e) {
			result=false;
		}
		return result;
	}

	public void showDetailsForTag(String tagName){
		Element detailsImg=Browser.getBrowser().findElement(By.xpath(String.format(SHOW_DETAILS_IMG_LOCATOR_PATTERN, tagName)));
		if  (detailsImg.isDisplayed()){
			detailsImg.click();
		}
	}
}
