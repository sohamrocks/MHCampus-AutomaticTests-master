package com.mcgraw.test.automation.tests.base;

import org.springframework.context.ConfigurableApplicationContext;

import com.mcgraw.test.automation.framework.core.runner.AppContextInitializer;
import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;
import com.mcgraw.test.automation.framework.core.runner.cli.MhCampusCliParams;


/**
 * Spring initializer for all tests. Uses custom CL parameters
 * 
 * @author Andrei_Turavets
 *
 */
public class MhCampusTestsInitializer extends AppContextInitializer<CommandLineParameters> {
	
	
	
	@Override
	public void initialize(ConfigurableApplicationContext context) {
			super.initialize(context);
			D2lVersion d2lVersion = getD2lVersion();
			if (d2lVersion.equals(D2lVersion.V10)){
				return;
			}
			//set d2l version 9 spring profile
			if(d2lVersion.equals(D2lVersion.V9)){
				context.getEnvironment().setActiveProfiles(d2lVersion.getSpringProfileName(),"default");
			}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.core.runner.AppContextInitializer#
	 * getCommandLineParametersBean()
	 */
	@Override
	public CommandLineParameters getCommandLineParametersBean() {
		return MhCampusCliParams.getInstance();
	}
	
	private D2lVersion getD2lVersion(){			
		return D2lVersion.getVersionByCliName(MhCampusCliParams.getInstance().getD2lVersion());
	}
}
