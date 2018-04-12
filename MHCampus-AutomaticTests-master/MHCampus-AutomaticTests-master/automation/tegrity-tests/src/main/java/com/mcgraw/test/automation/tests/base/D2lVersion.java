package com.mcgraw.test.automation.tests.base;


/** This is used to define under which version d2l tests will be run 
 * 
 * @author Andrei_Turavets
 *
 */
public enum D2lVersion {
	V10("v10", "d2lV10"),
	V9("v9", "d2lV9");
	
	private String cliName;
	private String springProfileName;

	private D2lVersion(String cliName, String springProfileName) {
		this.cliName = cliName;
		this.springProfileName = springProfileName;
	}

	public String getCliName() {
		return cliName;
	}
	
	public String getSpringProfileName() {
		return springProfileName;
	}

	public static D2lVersion getVersionByCliName(String cliName) {
		for (D2lVersion d2lVersion : values()) {
			if (d2lVersion.getCliName().equals(cliName)) {
				return d2lVersion;
			}
		}
		throw new IllegalArgumentException("No matching constant for d2l command line version [" + cliName + "]. The allowed values are 'v9' and 'v10' without quotes");
	}
	
	public static D2lVersion getVersionBySpringProfileName(String springProfileName) {
		for (D2lVersion d2lVersion : values()) {
			if (d2lVersion.getSpringProfileName().equals(springProfileName)) {
				return d2lVersion;
			}
		}
		throw new IllegalArgumentException("No matching constant for d2l spring profile name[" + springProfileName + "]. The allowed values are 'd2lV9' and 'd2lV10' without quotes");
	}

}
