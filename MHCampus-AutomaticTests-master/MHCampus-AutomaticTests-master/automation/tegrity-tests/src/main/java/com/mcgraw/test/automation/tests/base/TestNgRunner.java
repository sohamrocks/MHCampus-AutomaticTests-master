package com.mcgraw.test.automation.tests.base;

import com.mcgraw.test.automation.framework.core.runner.CommandLineRunner;
import com.mcgraw.test.automation.framework.core.runner.cli.MhCampusCliParams;
import com.mcgraw.test.automation.framework.selenium2.runner.config.CloseBrowserHook;
import com.mcgraw.test.automation.framework.selenium2.runner.config.PrepareTestOutputDirectoryHook;

/**
 * TestNG runner<br>
 * Entry point for testing application <br>
 * Start it by 'java -jar somejar.jar'
 *
 * @author Andrei Varabyeu
 *
 */
public class TestNgRunner {

	/**
	 * Executes TestNG tests
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CommandLineRunner<MhCampusCliParams> runner = new CommandLineRunner<MhCampusCliParams>(args);

		runner.registerCliParametersBean(MhCampusCliParams.getInstance());
	

		CloseBrowserHook closeBrowserHook = new CloseBrowserHook();
		runner.registerPreLaunchHook(closeBrowserHook);
		runner.registerPostLaunchHook(closeBrowserHook);
		runner.registerPreLaunchHook(new PrepareTestOutputDirectoryHook());
		runner.registerFailurePostProcessor(new TestsFailureProcessor());
		

		runner.run();
	}
}
