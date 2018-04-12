package com.mcgraw.test.automation.framework.core.runner.cli;

import org.kohsuke.args4j.Option;



/**
 * Command line parameters bean
 * 
 * @author Andrei_Turavets
 *
 */
public class MhCampusCliParams extends CommandLineParameters {
	
	@Option(name = "-campusurl", aliases = { "--campusInstanceUrl" }, usage = "MhCampus instance url.")
	private String instanceUrl;

	@Option(name = "-d2lver", aliases = { "--d2lVersion" }, usage = "D2L environment version against the tests will be run. Allowed values: v10 and v9. Default version is v10")
	private String d2lVersion = "v10";
	
	
	public String getInstanceUrl() {
		return instanceUrl;
	}

	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	public String getD2lVersion() {
		return d2lVersion;
	}

	public void setD2lVersion(String d2lVersion) {
		this.d2lVersion = d2lVersion;
	}

	private static MhCampusCliParams instance;

	private MhCampusCliParams() {
		// singleton implementation. Private constructor
	}

	public static synchronized MhCampusCliParams getInstance() {
		return instance == null ? instance = new MhCampusCliParams() : instance;
	}
}
