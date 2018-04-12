package com.mcgraw.test.automation.framework.selenium2.runner.config;

import org.testng.TestListenerAdapter;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.PostLaunchHook;
import com.mcgraw.test.automation.framework.core.runner.PreLaunchHook;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;

/**
 * Hook for closing all opened browsers (WebDriver instances)
 *
 * @author Andrei Varabyeu
 *
 */
public class CloseBrowserHook implements PreLaunchHook, PostLaunchHook {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.com.mcgraw.test.automation.framework.core.runner.PostLaunchHook#afterTestsLaunched(org.
	 * testng.TestListenerAdapter)
	 */
	@Override
	public void afterTestsLaunched(TestListenerAdapter tla) {
		Browser.quitAll();
		if (Thread.activeCount() != 1) {
			Logger.fatal("There are some threads didn't stop: "
					+ (Thread.activeCount() - 1));
		}

	}

	/**
	 * Add this Shutdown hook to avoid browser handing up in case of incorrect
	 * application exit
	 *
	 * @see Runtime#addShutdownHook(Thread)
	 */
	@Override
	public void beforeTestsStarted() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out
						.println("Shutting down. Trying to close all browser instances...");
				Browser.quitAll();
			}
		}));

	}

}
