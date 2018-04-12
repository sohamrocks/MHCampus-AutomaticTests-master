package com.mcgraw.test.automation.ui.applications;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCreateRemotePluginScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lManageExternalToolsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lCreateRemotePluginScreenV10;
import com.mcgraw.test.automation.ui.d2l.v9.D2lContentCourseScreenV9;
import com.mcgraw.test.automation.ui.d2l.v9.D2lLoginScreenV9;
import com.mcgraw.test.automation.ui.d2l.v9.D2lNewLinkScreenV9;

public class D2lApplicationV9 extends D2LApplication {

	D2lLoginScreenV9 d2lLoginScreenV9;

	public D2lApplicationV9(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lHomeScreen openLoginPageAndLoginToD2lAsAdmin() {
		d2lLoginScreenV9 = browser.openScreen(d2lBaseUrl, D2lLoginScreenV9.class);
		return d2lLoginScreenV9.loginToD2l(d2lAdminLogin, d2lAdminPassword);
	}

	@Override
	protected D2lNewLinkScreen openD2lNewLinkPage(String courseId) {
		return browser.openScreen(d2lNewLinkPageUrl.replace("<course_id>", courseId), D2lNewLinkScreenV9.class);
	}

	@Override
	public D2lContentCourseScreen openD2lContentCoursePage(String courseId) {
		return browser.openScreen(d2lCourseHomeUrl.replace("<course_id>", courseId), D2lContentCourseScreenV9.class);
	}

	@Override
	protected D2lHomeScreen openLoginPageAndLoginToD2l(String username, String password) {
		d2lLoginScreenV9 = browser.openScreen(d2lBaseUrl, D2lLoginScreenV9.class);
		return d2lLoginScreenV9.loginToD2l(username, password);
	}

	@Override
	public String getD2lGradebookServiceUrl() {
		return d2lGradebookServiceUrl;
	}

	@Override
	public String getD2lGradebookExtendedProperties() {
		return d2lGradebookExtendedPropertiesNone;
	}
	//added by Aleksandr
	@Override
	protected D2lCreateRemotePluginScreen openD2lCreateRemotePluginScreen() {
		return null;
	}

	@Override
	public D2lManageExternalToolsScreen openD2LManageExternalToolsPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
