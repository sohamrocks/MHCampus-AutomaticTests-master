package com.mcgraw.test.automation.ui.applications;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lHomeScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lLoginScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lNewLinkScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lContentCourseScreenV10;
import com.mcgraw.test.automation.ui.d2l.v10.D2lLoginScreenV10;
import com.mcgraw.test.automation.ui.d2l.v10.D2lNewLinkScreenV10;

public class D2lApplicationV10 extends D2LApplication {

	public D2lApplicationV10(Browser browser) {
		super(browser);
	}

	@Override
	protected D2lHomeScreen openLoginPageAndLoginToD2lAsAdmin() {
		D2lLoginScreen d2lLoginScreen = browser.openScreen(d2lBaseUrl, D2lLoginScreenV10.class);
		return d2lLoginScreen.loginToD2l(d2lAdminLogin, d2lAdminPassword);
	}

	@Override
	protected D2lNewLinkScreen openD2lNewLinkPage(String courseId) {
		return browser.openScreen(d2lNewLinkPageUrl.replace("<course_id>", courseId), D2lNewLinkScreenV10.class);
	}

	@Override
	protected D2lContentCourseScreen openD2lContentCoursePage(String courseId) {
		return browser.openScreen(d2lCourseHomeUrl.replace("<course_id>", courseId), D2lContentCourseScreenV10.class);
	}

	@Override
	protected D2lHomeScreen openLoginPageAndLoginToD2l(String username, String password) {
		D2lLoginScreen d2lLoginScreen = browser.openScreen(d2lBaseUrl, D2lLoginScreenV10.class);
		return d2lLoginScreen.loginToD2l(username, password);
	}

	@Override
	public String getD2lGradebookServiceUrl() {
		return d2lGradebookServiceUrl;
	}

	@Override
	public String getD2lGradebookExtendedProperties() {
		return d2lGradebookExtendedProperties;
	}
}
