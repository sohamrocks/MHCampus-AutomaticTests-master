package com.mcgraw.test.automation.framework.core.runner;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;

/**
 * Application Context initializer. Should be used with Spring Test Framework to
 * add possibility merging properties from property files with command line
 * parameters<br>
 *
 * To be able to link CLI parameter with property name you have to add
 * {@link com.mcgraw.test.automation.framework.core.runner.cli.Mapped} annotation to the bean
 * with CLI-values
 *
 * @author Andrei Varabyeu
 *
 * @param <T>
 *            - Type of bean with CLI values
 */
public abstract class AppContextInitializer<T extends CommandLineParameters>
		implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		context.getEnvironment()
				.getPropertySources()
				.addFirst(
						new CommandLineParametersPropertySource<T>(
								getCommandLineParametersBean()));

	}

	/**
	 * Source with Command line parameters in bean representation
	 *
	 * @return
	 */
	public abstract T getCommandLineParametersBean();
}
