package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;

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

	@Value("${d2l.gradebook.extendedproperties}")
	public String d2lGradebookExtendedProperties;
	
	@Value("${d2l.lti.title}")
	public String d2lLtiTitle;

	@Value("${d2l.lti.service.url}")
	public String d2lLtiServiceUrl;

	@Value("${d2l.lti.extendedproperties}")
	public String d2lLtiExtendedProperties;
	
	@Value("${d2l.data.title}")
	public String d2lDataTitle;

	@Value("${d2l.data.service.url}")
	public String d2lDataServiceUrl;

	@Value("${d2l.data.extendedproperties}")
	public String d2lDataExtendedProperties;

	public abstract String getD2lGradebookServiceUrl();

	public abstract String getD2lGradebookExtendedProperties();

	private D2lNewLinkScreen d2lNewLinkScreen;
	private D2lManageExternalToolsScreen d2lManageExternalToolsScreen;
	private D2lContentCourseScreen d2lContentCourseScreen;

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
				+ "' connected with Tegrity instance");
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
		d2lNewLinkScreen.typeCustomParameters(d2lCustomParametersName,
				d2lCustomParametersValue);
		browser.makeScreenshot();
		d2lNewLinkScreen.clickSaveBtn(); 
		browser.makeScreenshot();
	}

	protected abstract D2lNewLinkScreen openD2lNewLinkPage(String courseId);

	public void addCreatedLinkToD2lModule(String courseId, String linkName,
			String moduleName) {
		Logger.info("Adding link '" + linkName + "' to D2L module '" + moduleName + "'");
		loginToD2lAsAdmin();
		browser.pause(6000);
		d2lContentCourseScreen = openD2lContentCoursePage(courseId);
		d2lContentCourseScreen.addModule(moduleName);
		d2lContentCourseScreen.selectToAddExternalLearningToolsToModule();
		d2lContentCourseScreen.insertExternalLearningTool(linkName);
	}

	protected abstract D2lContentCourseScreen openD2lContentCoursePage(
			String courseId);
}
