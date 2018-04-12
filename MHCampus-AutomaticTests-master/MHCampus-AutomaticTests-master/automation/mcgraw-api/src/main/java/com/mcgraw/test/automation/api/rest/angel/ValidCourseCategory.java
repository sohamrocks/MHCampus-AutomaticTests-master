package com.mcgraw.test.automation.api.rest.angel;

public enum ValidCourseCategory {
	TESTCATEGORY("Test Category");
	
	private final String value;

	public String getValue() {
		return value;
	}

	private ValidCourseCategory(String value) {
		this.value = value;
	}

	public static ValidCourseCategory getByValue(String value) {
		for (ValidCourseCategory validCourseCategory : values()){
			if (validCourseCategory.getValue().equals(value)) {
				return validCourseCategory;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
