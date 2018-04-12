
package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.JSActions;


public class SitesTable extends Element {

	public final String ROW_XPATH_PATTERN = "//tbody/tr['%d']";

	public final String GROUP_XPATH_PATTERN = "//tbody/tr/td[2]/span[text()='%s']";

	public static final String GROUP_NAME_PATH_PATTERN = "//tbody/tr/td[1]/span[text()='%s']/ancestor::tr//span[contains(@id,'ro_site_group_name')]";

	public static final String EDIT_SITE_PATH_PATTERN = "//img[contains(@id,'edit_site')]";

	public static final String SAVE_ACTION_PATH_PATTERN = "//img[contains(@id,'edit_save_site')]";

	public static final String EDIT_NAME_INPUT_PATH_PATTERN = "//tbody/tr[1]/td[1]/input";

	public static final String EDIT_GROUP_INPUT_PATH_PATTERN = "//tbody/tr[1]/td[2]/input";

	public static final String DELETE_SITE_PATH_PATTERN = "//img[contains(@id,'delete_site')]";

	public static final String ROW_XPATH = "//tbody/tr";

	public static final long WAIT = 20;

	public SitesTable(WebElement webElement) {
		super(webElement);
	}

	public String getGroupBySiteName(String siteName) {
		String pathItem = String.format(GROUP_NAME_PATH_PATTERN, siteName);
		Element element = this.findElement(By.xpath(pathItem));
		return element.getText();
	}

	public void deleteSite(Browser browser) {
		Element deleteImg=this.findElement(By.xpath(DELETE_SITE_PATH_PATTERN));
		try{
			browser.fireJavaScriptEventForElement(JSActions.CLICK, deleteImg);
		}catch(WebDriverException e){
		   Logger.warn(" Some problems may have occur during script execution");
		}
	}

	public void editSite(Browser browser, String newSiteName,
			String newGroupName) {
		Element editImg=this.findElement(By.xpath(EDIT_SITE_PATH_PATTERN));
		browser.fireJavaScriptEventForElement(JSActions.CLICK, editImg);	
		Element editName = this.findElement(By
				.xpath(EDIT_NAME_INPUT_PATH_PATTERN));
		Input editNameInput = new Input(editName.getWebElement());
		editNameInput.clearInput();
		editNameInput.typeValue(newSiteName);
		Element editGroup = this.findElement(By
				.xpath(EDIT_GROUP_INPUT_PATH_PATTERN));
		Input editGroupInput = new Input(editGroup.getWebElement());
		editGroupInput.clearInput();
		editGroupInput.typeValue(newGroupName);
		Element saveImg = this.findElement(By
				.xpath(SAVE_ACTION_PATH_PATTERN));
		browser.fireJavaScriptEventForElement(JSActions.CLICK, saveImg);			
	}

	public boolean isGroupPresent(Browser browser, String groupName) {
		boolean result = false;
		try {
			String pathItem = String.format(GROUP_XPATH_PATTERN, groupName);
			Element element = browser.waitForElement(By.xpath(pathItem), WAIT );   
			result = element.isDisplayed();
		} catch (TimeoutException e) {}
		return result;
	}

	public boolean isTableEmpty(){
		boolean result=true;
		try {

			Element element = this.findElement(By.xpath(ROW_XPATH));
			result = !element.isElementPresent();
		} catch (NoSuchElementException e) {
		}
		return result;		
	}
}