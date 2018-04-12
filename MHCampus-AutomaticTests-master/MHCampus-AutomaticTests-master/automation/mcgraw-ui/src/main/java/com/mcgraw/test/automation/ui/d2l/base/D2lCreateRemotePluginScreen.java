package com.mcgraw.test.automation.ui.d2l.base;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;

public abstract class D2lCreateRemotePluginScreen extends Screen {

	public D2lCreateRemotePluginScreen(Browser browser) {
		super(browser);
	}

	
	public abstract void selectPluginType(String pluginTypeName);
	
	public abstract void typePluginName(String pluginName);

	public abstract void typeLunchPointUrl(String lunchUrl);
	
	public abstract void typeLtiKey(String customerNumber);
	
	public abstract void typeLtiSecret(String sharedSecret);

	public abstract void setCurentOrgUnitCheckbox(boolean state);

	public abstract D2lManageRemotePluginsScreen clickSaveButton();
	
	protected abstract D2lManageRemotePluginsScreen waitForD2lManageRemotePluginsScreen();

	public abstract void clickAddOrgUnitsBtn();
	public abstract void typeAndSearchAddOrgUnitsCourseValue(String courseName);
	public abstract void selectFirstFoundedCourse();
	public abstract void clickAddOrgUnitsInsertBtn();
	public abstract boolean findCourseOfferingByName(String courseName);
	public abstract void clickSaveAndCloseBtn();

}
