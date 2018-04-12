package com.mcgraw.test.automation.api.rest.canvas;

public enum CourseDefaultView {
	FEED("feed"), WIKI("wiki"), MODULES("modules"), ASSIGNMENTS("assignments"), SYLLABUS("syllabus");

	private String value;

	private CourseDefaultView(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CourseDefaultView getByValue(String value) {
		
		for (CourseDefaultView courseDefaultView : values()) {
			if (courseDefaultView.getValue().equals(value)) {
				return courseDefaultView;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
