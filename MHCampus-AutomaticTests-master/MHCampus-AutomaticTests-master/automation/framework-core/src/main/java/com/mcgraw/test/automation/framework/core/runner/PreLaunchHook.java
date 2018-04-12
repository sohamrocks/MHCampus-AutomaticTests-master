package com.mcgraw.test.automation.framework.core.runner;

/**
 * Pre Launch Hook. Executes some action before tests started
 * 
 * @author Andrei Varabyeu
 * 
 */
public interface PreLaunchHook {

	/**
	 * Executes some action before tests started
	 */
	void beforeTestsStarted();
}
