package com.mcgraw.test.automation.ui.mhcampus;

public enum MhCampusReaderType {
	VITAL_SOURCE("VitalSource LTI"),
	CREATE("CREATE"), 
	COURSE_SMART("Course Smart"), 
	DEFAULT("Default");

	private String value;

	private MhCampusReaderType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
