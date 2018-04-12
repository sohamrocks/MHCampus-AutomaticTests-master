package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCreateRemotePluginScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageRemotePluginsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditWidgetScreen;
import org.apache.commons.lang.RandomStringUtils;

public abstract class D2LApplication {

	@Value("${d2l.baseurl}")
	public String d2lBaseUrl;

	@Value("${d2l.title}")
	public String d2lTitle;

	@Value("${d2l.adminlogin}")
	public String d2lAdminLogin;

	@Value("${d2l.adminpassword}")
	public String d2lAdminPassword;

	@Value("${d2l.newlink.page.url}")
	public String d2lNewLinkPageUrl;

	@Value("${d2l.coursehome.url}")
	public String d2lCourseHomeUrl;

	@Value("${d2l.newlink.url}")
	public String d2lNewLinkUrl;

	@Value("${d2l.customparameter.name}")
	public String d2lCustomParametersName;

	@Value("${d2l.customparameter.value}")
	public String d2lCustomParametersValue;

	@Value("${d2l.gradebook.service.url}")
	public String d2lGradebookServiceUrl;

	@Value("${d2l.gradebook.extendedproperties.none}")
	public String d2lGradebookExtendedPropertiesNone;
	
	@Value("${d2l.gradebook.extendedproperties.new}")
	public String d2lGradebookExtendedPropertiesNew;
	
	@Value("${d2l.gradebook.extendedproperties.exist}")
	public String d2lGradebookExtendedPropertiesExist;

	@Value("${d2l.manage.remote.plugins.url}")
	public String manageRemotePluginsUrl;

	@Value("${d2l.create.remote.plugin.url}")
	public String createRemotePluginUrl;
	
	@Value("${d2l.manage.external.tools.url}")
	public String d2lManageExternalToolsUrl;

	public abstract String getD2lGradebookServiceUrl();

	public abstract String getD2lGradebookExtendedProperties();

	private D2lNewLinkScreen d2lNewLinkScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;
	private D2lCreateRemotePluginScreen d2lCreateRemotePluginScreen;
	private D2lEditWidgetScreen d2lEditWidgetScreen;
	private D2lHomeScreen d2lHomeScreen;
	
	Browser browser;

	public D2LApplication(Browser browser) {
		this.browser = browser;
	}

	public D2lHomeScreen loginToD2lAsAdmin() {
		Logger.info("Logging in to D2L...");
		browser.manage().deleteAllCookies();
		return openLoginPageAndLoginToD2lAsAdmin();
	}

	public D2lHomeScreen loginToD2l(String username, String password) {
		Logger.info("Logging in to D2L...");
		browser.manage().deleteAllCookies();
		return openLoginPageAndLoginToD2l(username, password);
	}

	protected abstract D2lHomeScreen openLoginPageAndLoginToD2lAsAdmin();

	protected abstract D2lHomeScreen openLoginPageAndLoginToD2l(
			String username, String password);

	public void createD2lLinkConnectedWithInstance(String linkName,
			String courseId, String customerNumber, String sharedSecret) {
		Logger.info("Creating new D2L link '" + linkName
				+ "' connected with MH Campus instance");
		loginToD2lAsAdmin();
		browser.pause(6000);
		d2lNewLinkScreen = openD2lNewLinkPage(courseId);
		d2lNewLinkScreen.typeTitle(linkName);
		d2lNewLinkScreen.typeUrl(d2lNewLinkUrl);
		d2lNewLinkScreen.setAllowUsersToSeeLinkCheckbox(true);
		d2lNewLinkScreen.setSignMessageWithKey(true);
		d2lNewLinkScreen.chooseLinkKeyRadioBtn();
		d2lNewLinkScreen.typeCustomerKey(customerNumber);
		d2lNewLinkScreen.typeSharedSecret(sharedSecret);
		d2lNewLinkScreen.setSendContextToProvider(true);
		d2lNewLinkScreen.setSendLinkDescriptionToProvider(true);
		d2lNewLinkScreen.setSendToolToProvider(true);
		d2lNewLinkScreen.setSendUserEmailToProvider(true);
		d2lNewLinkScreen.setSendUserIdToProvider(true);
		d2lNewLinkScreen.setSendUserNameToProvider(true);
		d2lManageExternalToolsScreen = d2lNewLinkScreen.clickSaveBtn();
		d2lNewLinkScreen = d2lManageExternalToolsScreen.clickLink(linkName);
		try{
			d2lNewLinkScreen.typeCustomParameters(d2lCustomParametersName,
				d2lCustomParametersValue);
		}
		catch (Exception e){
			d2lNewLinkScreen.typeCustomParameters(d2lCustomParametersName,
					d2lCustomParametersValue);
		}
		d2lNewLinkScreen.clickSaveBtn();
	}

	protected abstract D2lNewLinkScreen openD2lNewLinkPage(String courseId);

	public void addCreatedLinkToD2lModule(String courseId, String linkName,
			String moduleName) {
		Logger.info("Adding link '" + linkName + "' to D2L module '"
				+ moduleName + "'");
		loginToD2lAsAdmin();
		browser.pause(6000);
		d2lContentCourseScreen = openD2lContentCoursePage(courseId);
		d2lContentCourseScreen.addModule(moduleName);
		d2lContentCourseScreen.selectToAddExternalLearningToolsToModule();
		d2lContentCourseScreen.insertExternalLearningTool(linkName);
	}

	public abstract D2lContentCourseScreen openD2lContentCoursePage(String courseId);
    //added by Aleksandr
	public D2lManageRemotePluginsScreen createNewWidgetPlugin(String pluginName, String lunchUrl, String customerNumber, String sharedSecret, String courseName) {
	    Logger.info("Creating new Widget Plugin");
	    browser.pause(3000);
	    d2lCreateRemotePluginScreen = openD2lCreateRemotePluginScreen();
	    d2lCreateRemotePluginScreen.selectPluginType("Widget");
	    d2lCreateRemotePluginScreen.typePluginName(pluginName);
	    d2lCreateRemotePluginScreen.typeLunchPointUrl(lunchUrl);
	    d2lCreateRemotePluginScreen.typeLtiKey(customerNumber);
	    d2lCreateRemotePluginScreen.typeLtiSecret(sharedSecret);
	    d2lCreateRemotePluginScreen.setCurentOrgUnitCheckbox(false);
	    d2lCreateRemotePluginScreen.clickAddOrgUnitsBtn();
	    d2lCreateRemotePluginScreen.typeAndSearchAddOrgUnitsCourseValue(courseName);
	    d2lCreateRemotePluginScreen.selectFirstFoundedCourse();
	    d2lCreateRemotePluginScreen.clickAddOrgUnitsInsertBtn();
		Assert.assertTrue(d2lCreateRemotePluginScreen.findCourseOfferingByName(courseName));
		//d2lCreateRemotePluginScreen.clickSaveAndCloseBtn();	    
	    return d2lCreateRemotePluginScreen.clickSaveButton();
    }
	//added by Aleksandr
	protected abstract D2lCreateRemotePluginScreen openD2lCreateRemotePluginScreen();
	
	public D2lManageExternalToolsScreen bindingPluginToCourse(String courseName, String customerNumber, String sharedSecret, String widgetName){
	    Logger.info("Binding plugin to course");
	    browser.pause(3000);
		d2lManageExternalToolsScreen = openD2LManageExternalToolsPage();
		d2lEditWidgetScreen = d2lManageExternalToolsScreen.findAndClickOnWidgetByName(widgetName);
		browser.pause(3000);
		browser.executeScript("window.scrollBy(0,250)", "");
		d2lEditWidgetScreen.chooseLinkKeyRadioBtn();
		d2lEditWidgetScreen.typeCustomerKey(customerNumber);
		d2lEditWidgetScreen.typeSharedSecret(sharedSecret);
		d2lEditWidgetScreen.clickSaveButton();
		browser.pause(3000);
		browser.executeScript("window.scrollBy(0,250)", "");
		d2lEditWidgetScreen.clickAddCustomParameterBtn();
		browser.pause(5000);
		browser.executeScript("window.scrollBy(0,250)", "");
		d2lEditWidgetScreen.typeCustomParameterName("tool_id");
		d2lEditWidgetScreen.typeCustomParameterValue("connect");		
		browser.executeScript("window.scrollTo(0,document.body.scrollHeight)", "");
		d2lEditWidgetScreen.setSendToolToProvider(true);
		d2lEditWidgetScreen.setSendContextToProvider(true);
		d2lEditWidgetScreen.setSendLinkCourseInformationToProvider(true);
		d2lEditWidgetScreen.setSendUserIdToProvider(true);
		d2lEditWidgetScreen.setSendUserNameToProvider(true);
		d2lEditWidgetScreen.setSendUserEmailToProvider(true);
		d2lEditWidgetScreen.setSendLinkTitleToProvider(true);
		d2lEditWidgetScreen.setSendLinkDescriptionToProvider(true);
		d2lEditWidgetScreen.setCurrentOrgUnitHEBrightspace(false);
		browser.executeScript("window.scrollBy(0,250)", "");
		d2lEditWidgetScreen.clickAddOrgUnitsBtn();
		d2lEditWidgetScreen.typeAndSearchAddOrgUnitsCourseValue(courseName);
		browser.pause(3000);
		d2lEditWidgetScreen.selectFirstFoundedCourse();
		d2lEditWidgetScreen.clickAddOrgUnitsInsertBtn();
		d2lEditWidgetScreen.checkCourseOfferingPresent(courseName);
		browser.pause(1000);
		return d2lEditWidgetScreen.clickSaveAndCloseBtn();
	}
	
	//Added by AleksandrY
	public D2lLoginScreen d2lLogout(D2lHomeScreen d2lHomeScreen){
		Logger.info("Log out from D2l");
		d2lHomeScreen.clickMyAccountLink();
		d2lHomeScreen.clickLogOutLink();
		browser.pause(2000);
		return d2lHomeScreen.waitForD2lLoginScreen();
	}

	//Added By Andrii Vozniuk
	public abstract D2lManageExternalToolsScreen openD2LManageExternalToolsPage();

}
