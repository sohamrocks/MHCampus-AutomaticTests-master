/**
 *
 */
package com.mcgraw.test.automation.framework.core.runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ExampleMode;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.configuration.MappedBean;
import com.mcgraw.test.automation.framework.core.results.logger.CustomHTMLReporter;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;
import com.mcgraw.test.automation.framework.core.testng.Assert;

/**
 * @author Andrei Varabyeu
 *
 */
public class CommandLineRunner<C extends CommandLineParameters> implements
		IRunner {

	/** TestNG instance */
	private TestNG testNg;

	/** Command-line arguments */
	private String[] cliArgs;

	/** TestNG listeners */
	@SuppressWarnings("rawtypes")
	private List<Class> testNgListeners;

	private List<MappedBean<C>> configurationMappedBeans;

	private List<String> propertySources;

	private Properties defaultProperties;

	private List<FailurePostProcessor> failurePostProcessors;

	private List<PostLaunchHook> postLaunchHooks;

	private List<PreLaunchHook> preLaunchHooks;

	private C commandLineParameters;

	@SuppressWarnings("rawtypes")
	public CommandLineRunner(String[] args, List<Class> listeners) {
		this.cliArgs = args;
		this.testNgListeners = listeners;
		this.testNg = new TestNG();
		this.configurationMappedBeans = new ArrayList<MappedBean<C>>();
		this.propertySources = new ArrayList<String>();
		this.failurePostProcessors = new ArrayList<FailurePostProcessor>();
		this.postLaunchHooks = new ArrayList<PostLaunchHook>();
		this.preLaunchHooks = new ArrayList<PreLaunchHook>();
	}

	/**
	 * Constructor. Default listener {@link TestListener} will be used.
	 *
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public CommandLineRunner(String[] args) {
		this(args, new ArrayList<Class>());
		this.testNgListeners.add(TestListener.class);
		this.testNgListeners.add(CustomHTMLReporter.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.mcgraw.test.automation.framework.core.runner.IRunner#run()
	 */
	@Override
	public void run() {
		
		Assert.IS_VERIFY_SUPPORTED = true;

		/** Initialize default properties */
		initializeProperties(propertySources);

		/** Parse command line arguments */
		CmdLineParser parser = new CmdLineParser(commandLineParameters);
		try {
			parser.parseArgument(cliArgs);
		} catch (CmdLineException e) {
			parser.printUsage(System.err);
			System.err.println(parser.printExample(ExampleMode.ALL));
			System.exit(1);
		}
		initializeMappedBeans(commandLineParameters, defaultProperties);
		processPreLaunchHooks();
		TestListenerAdapter testResultListener = new TestListenerAdapter();
		/** Parse TestNG XML files and run tests */
		try {
			List<String> xmlSuites = new ArrayList<String>();

			/* If path to Folder with TestNG Suites definition files is defined */
			if (null == commandLineParameters.getTngFolder()
					|| commandLineParameters.getTngFolder().isEmpty()) {
				xmlSuites = commandLineParameters.getXmlSuites();
			} else {
				/* Check that file is Directory and it exists */
				File testSuitesFolder = new File(
						commandLineParameters.getTngFolder());
				if (!testSuitesFolder.exists()
						|| !testSuitesFolder.isDirectory()) {
					throw new RuntimeException(
							"Cannot find directory with TestNG Suite definition files");
				}
				/* Obtain all files from Directory */
				for (File superTagSuite : testSuitesFolder.listFiles()) {
					xmlSuites.add(superTagSuite.getPath());
				}
			}

			for (String suite : xmlSuites) {
				try {
					for (XmlSuite xmlSuite : new Parser(suite).parseToList()) {
						testNg.setCommandLineSuite(xmlSuite);
					}
				} catch (IOException ioExcept) {
					Logger.warn("Suite not found: "
							+ new File(suite).getAbsolutePath());
				}
			}
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			testNg.setSuiteThreadPoolSize(commandLineParameters
					.getSuiteThreadPoolSize());
			testNg.setUseDefaultListeners(true);
			testNg.setListenerClasses(testNgListeners);
			testNg.addListener(testResultListener);
			testNg.run();
		} catch (Exception ex) {
			Logger.fatal("Fatal error during running test suites.", ex);
			throw new RuntimeException(ex);
		} finally {

			processPostLaunchHooks(testResultListener);

			if (commandLineParameters.getFailOnErrors()) {
				if (testResultListener.getFailedTests().size() > 0
						|| testResultListener.getSkippedTests().size() > 0) {
					for (FailurePostProcessor failurePostProcessor : failurePostProcessors) {
						if (failurePostProcessor.shouldFail(testResultListener
								.getFailedTests())
								|| failurePostProcessor
										.shouldFail(testResultListener
												.getSkippedTests())) {
							Logger.info("There are test failures. Application will exit with code'1'");
							System.exit(1);
						}
					}
				}
			}
			System.exit(0);
		}
	}

	private void processPostLaunchHooks(TestListenerAdapter tla) {
		for (PostLaunchHook hook : postLaunchHooks) {
			Logger.debug("Starting processing Post Launch Hook with name '"
					+ hook.getClass().getSimpleName() + "'");
			hook.afterTestsLaunched(tla);
		}

	}

	private void processPreLaunchHooks() {
		for (PreLaunchHook preLaunchHook : preLaunchHooks) {
			Logger.debug("Starting processing Pre Launch Hook with name '"
					+ preLaunchHook.getClass().getSimpleName() + "'");
			preLaunchHook.beforeTestsStarted();
		}
	}

	public void registerMappedBean(MappedBean<C> mappedBean) {
		Logger.debug("Mapped bean with name '"
				+ mappedBean.getClass().getSimpleName()
				+ "' has been registered");
		configurationMappedBeans.add(mappedBean);
	}

	public void registerFailurePostProcessor(
			FailurePostProcessor failurePostProcessor) {
		Logger.debug("Failure post processor with name '"
				+ failurePostProcessor.getClass().getSimpleName()
				+ "' has been registered");
		failurePostProcessors.add(failurePostProcessor);
	}

	public void registerPropertySource(String propertySource) {
		Logger.debug("Property source with name '" + propertySource
				+ "' has been registered");
		propertySources.add(propertySource);
	}

	public void registerPreLaunchHook(PreLaunchHook preLaunchHook) {
		Logger.debug("Pre Launch hook with name '"
				+ preLaunchHook.getClass().getSimpleName()
				+ "' has been registered");
		preLaunchHooks.add(preLaunchHook);
	}

	public void registerPostLaunchHook(PostLaunchHook postLaunchHook) {
		Logger.debug("Post Launch hook with name '"
				+ postLaunchHook.getClass().getSimpleName()
				+ "' has been registered");
		postLaunchHooks.add(postLaunchHook);
	}

	public void registerTestListener(Class<?> clazz) {
		this.testNgListeners.add(clazz);
	}

	public void registerCliParametersBean(C cliBean) {
		this.commandLineParameters = cliBean;
	}

	private void initializeProperties(List<String> propertySources) {
		/* Initialize default properties */
		defaultProperties = new Properties();
		try {
			for (String propertySource : propertySources) {
				defaultProperties.load(ResourceUtils
						.getResourceAsInputStream(propertySource));
			}
		} catch (IOException e) {
			throw new RuntimeException(
					"Unable to read file with default properties", e);
		}
		System.out.println(defaultProperties);
	}

	private void initializeMappedBeans(C cliParameters, Properties properties) {
		for (MappedBean<C> bean : configurationMappedBeans) {
			bean.initializeDefaults(cliParameters, properties);
		}
	}

}
