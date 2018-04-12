package com.mcgraw.test.automation.framework.core.runner;

import org.testng.TestListenerAdapter;

/**
 * Post Launch Hook. Executes some action after tests finished
 * 
 * @author Andrei Varabyeu
 * 
 */
public interface PostLaunchHook {

	/**
	 * Executes some action after tests finished
	 * 
	 * @param tla
	 *            - TestListenerAdapter
	 */
	void afterTestsLaunched(TestListenerAdapter tla);
}
