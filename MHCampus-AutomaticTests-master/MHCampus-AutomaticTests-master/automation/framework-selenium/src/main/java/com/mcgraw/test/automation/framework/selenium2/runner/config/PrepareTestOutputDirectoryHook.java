package com.mcgraw.test.automation.framework.selenium2.runner.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.PreLaunchHook;

public class PrepareTestOutputDirectoryHook implements PreLaunchHook {

	@Override
	public void beforeTestsStarted() {
		try {
			FileUtils.forceMkdir(new File("test-output/html"));
		} catch (IOException e) {
			Logger.error("Unable to create test-output direcotory");
		}
	}

}
