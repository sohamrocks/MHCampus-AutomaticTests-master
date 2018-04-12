package com.mcgraw.test.automation.framework.core.runner.cli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.args4j.Option;

/**
 * Parser for CLI parameters.
 *
 * @author Andrei Varabyeu
 */
public class CommandLineParameters {

	@Option(name = "-failonerrors", usage = "Wheither application should fail after test failures", handler = ExplicitBooleanOptionHandler.class)
	private Boolean failOnErrors = false;

	@Option(name = "-tng", aliases = { "--testng" }, usage = "Suite to run. Default is 'testng.xml'.")
	private ArrayList<String> xmlSuites = new ArrayList<String>();

	/** Number of Local Urls */
	@Option(name = "-dr", aliases = { "--deepReport" }, usage = "Deep report. Will add log4j logs to TestNG/ReportNG reports. Default is 'TRUE'", handler = ExplicitBooleanOptionHandler.class)
	private Boolean deepRerort = true;

	@Option(name = "-suitethreadpoolsize", usage = "Size of the thread pool to use to run suites")
	private Integer suiteThreadPoolSize = 1;

	@Option(name = "-tngFolder", aliases = { "--testngFolder" }, usage = "Folder with set of testng*.xml files. Define folder parameter when you'd like to execute all XMLs in folder")
	private String tngFolder;

	/**
	 * @return the xmlSuites
	 */
	public List<String> getXmlSuites() {
		if (xmlSuites.isEmpty()) {
			return Collections.singletonList("testng.xml");
		}
		return xmlSuites;
	}

	/**
	 * @param xmlSuites
	 *            the xmlSuites to set
	 */
	public void setXmlSuites(ArrayList<String> xmlSuites) {
		this.xmlSuites = xmlSuites;
	}

	/**
	 * @return the deepRerort
	 */
	public Boolean getDeepRerort() {
		return deepRerort;
	}

	/**
	 * @param deepRerort
	 *            the deepRerort to set
	 */
	public void setDeepRerort(Boolean deepRerort) {
		this.deepRerort = deepRerort;
	}

	/**
	 * @return the suiteThreadPoolSize
	 */
	public Integer getSuiteThreadPoolSize() {
		return suiteThreadPoolSize;
	}

	/**
	 * @param suiteThreadPoolSize
	 *            the suiteThreadPoolSize to set
	 */
	public void setSuiteThreadPoolSize(Integer suiteThreadPoolSize) {
		this.suiteThreadPoolSize = suiteThreadPoolSize;
	}

	/**
	 * @return the tngFolder
	 */
	public String getTngFolder() {
		return tngFolder;
	}

	/**
	 * @param tngFolder
	 *            the tngFolder to set
	 */
	public void setTngFolder(String tngFolder) {
		this.tngFolder = tngFolder;
	}

	public Boolean getFailOnErrors() {
		return failOnErrors;
	}

	public void setFailOnErrors(Boolean failOnErrors) {
		this.failOnErrors = failOnErrors;
	}

}