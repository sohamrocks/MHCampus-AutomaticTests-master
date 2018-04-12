package com.mcgraw.test.automation.ui.d2l.v10;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.CheckBox;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lCreateRemotePluginScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageRemotePluginsScreen;


@PageRelativeUrl("")
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = ".//span[contains(text(),'Create a new Remote Plugin')]")))

public class D2lCreateRemotePluginScreenV10 extends D2lCreateRemotePluginScreen {

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//select[@id='z_d']//option[contains(text(),'Widget')]"))
	protected Element pluginType;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//input[@id='z_bj']"))
	protected Input pluginNameField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//input[@id='z_bn']"))
	protected Input lunchPointUrlField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//input[@id='z_br']"))
	protected Input ltiKeyField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//input[@id='z_bu']"))
	protected Input ltiSecretField;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Add Org Units']"))
	protected Element addOrgUnitsBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//input[@id='z_cz']"))
	protected CheckBox currentOrgUnitCheckbox;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='d2l_form']//table[@class='dsearch_header']//input"))
	protected Input addOrgUnitsSearchCourseInput;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_j"))
	protected Element addOrgUnitsPerformSearchCourseBtn;

	@DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = " d_lastRow"))
	protected Element firstFoundedCourse;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[text()='Insert'][@class='vui-button d2l-button vui-button-primary']"))
	protected Element addOrgUnitsInsertBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//td[@class='dcs_c dcs_cf']"))
	protected Element courseOffering;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "z_a"))
	protected Element saveAndCloseBtn;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//a[@id='z_a']"))
	protected Element saveButton;
	
	public D2lCreateRemotePluginScreenV10(Browser browser) {
		super(browser);
	}
	
	@Override
	public void selectPluginType(String pluginTypeName) {
		Logger.info(String.format("Select plugin type <%s>", pluginTypeName));
		if(pluginTypeName.equals("Widget")){
			browser.waitForElementPresent(pluginType);
			pluginType.click();
			return;
		}
		throw new IllegalArgumentException(String.format("Plugin type <%s> is not correct", pluginTypeName));
	}
	
	@Override
	public void typePluginName(String pluginName){
		browser.waitForElementPresent(pluginNameField);
		pluginNameField.clearAndTypeValue(pluginName);
	}
	
	@Override
	public void typeLunchPointUrl(String lunchUrl){
		browser.waitForElementPresent(lunchPointUrlField);
		lunchPointUrlField.clearAndTypeValue(lunchUrl);
	}
	
	@Override
	public void typeLtiKey(String customerNumber){
		browser.waitForElementPresent(ltiKeyField);
		ltiKeyField.clearAndTypeValue(customerNumber);
	}
	
	@Override
	public void typeLtiSecret(String sharedSecret){
		browser.waitForElementPresent(ltiSecretField);
		ltiSecretField.clearAndTypeValue(sharedSecret);
	}
	
	@Override
	public void setCurentOrgUnitCheckbox(boolean state){
		browser.waitForElementPresent(currentOrgUnitCheckbox);
		currentOrgUnitCheckbox.setChecked(state);
	}
	
	@Override
	public D2lManageRemotePluginsScreen clickSaveButton(){
		Logger.info("Click Save button");
		browser.waitForElementPresent(saveButton);
		saveButton.click();
		return waitForD2lManageRemotePluginsScreen();
	}	
	
	@Override
	protected D2lManageRemotePluginsScreen waitForD2lManageRemotePluginsScreen() {
		return browser.waitForPage(D2lManageRemotePluginsScreenV10.class, 20);
	}

	@Override	
	public void clickAddOrgUnitsBtn() {
		addOrgUnitsBtn.click();
		browser.pause(2000);
		browser.switchTo().frame(browser.waitForElement(By.xpath(".//iframe[@title='Add Org Units']")));
	}	

	@Override	
	public void typeAndSearchAddOrgUnitsCourseValue(String value) {
		browser.pause(2000);
		if (addOrgUnitsSearchCourseInput.waitForPresence(30)) {
			addOrgUnitsSearchCourseInput.typeValue(value);
			addOrgUnitsSearchCourseInput.sendKeys(Keys.ENTER);
		}
	}	

	@Override	
	public void selectFirstFoundedCourse() {
		browser.waitForElementPresent(firstFoundedCourse);
			firstFoundedCourse.findElement(By.className(" d_chb")).click();
	}	

	@Override	
	public void clickAddOrgUnitsInsertBtn() {
		browser.switchTo().defaultContent();
		addOrgUnitsInsertBtn.click();
	}

	@Override	
	public boolean findCourseOfferingByName(String name) {
		List<WebElement> courses = courseOffering.findElements(By.xpath("//td[@class='dcs_c dcs_cf']"));
		boolean flag = false;
		for (WebElement e : courses) {
			if (e.findElement(By.tagName("strong")).getText().equals(name)) {
				flag = true;
				break;
			}
		}
		return flag;
	}	
	

	@Override
	public void clickSaveAndCloseBtn() {
		saveAndCloseBtn.click();
	}	

}
