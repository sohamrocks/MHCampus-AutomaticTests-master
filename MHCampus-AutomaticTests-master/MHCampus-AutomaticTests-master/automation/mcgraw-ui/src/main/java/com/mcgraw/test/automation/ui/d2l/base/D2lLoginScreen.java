package com.mcgraw.test.automation.ui.d2l.base;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;

public abstract class D2lLoginScreen extends Screen {

	public D2lLoginScreen(Browser browser) {
		super(browser);
	}

	protected abstract void typeUsername(String username);

	protected abstract void typePassword(String password);

	public abstract D2lHomeScreen loginToD2l(String name, String password);

	protected abstract D2lHomeScreen waitForD2lHomeScreen();
}