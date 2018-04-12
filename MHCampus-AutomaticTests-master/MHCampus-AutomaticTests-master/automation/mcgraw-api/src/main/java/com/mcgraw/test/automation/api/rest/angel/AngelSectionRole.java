package com.mcgraw.test.automation.api.rest.angel;

public enum AngelSectionRole {
	INSTRUCTOR("32"), COURSE_ASSISTANT("16"), FACULTY("8"), STUDENT("2");

	private final String value;

	public String getValue() {
		return value;
	}

	private AngelSectionRole(String value) {
		this.value = value;
	}

	public static AngelSectionRole getByValue(String value) {
		for (AngelSectionRole angelSectionRole : values()) {
			if (angelSectionRole.getValue().equals(value)) {
				return angelSectionRole;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
