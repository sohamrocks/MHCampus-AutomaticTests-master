package com.mcgraw.test.automation.api.rest.angel;

public enum ValidCourseSemester {
	TEST_SEMESTER("TestSemester");
	
	private final String value;

	public String getValue() {
		return value;
	}

	private ValidCourseSemester(String value) {
		this.value = value;
	}

	public static ValidCourseSemester getByValue(String value) {
		for (ValidCourseSemester validCourseSemester : values()){
			if (validCourseSemester.getValue().equals(value)) {
				return validCourseSemester;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
