package com.mcgraw.test.automation.api.rest.canvas;

public enum CourseWorkflowState {
	UNPUBLISHED("unpublished"), AVAILABLE("available"), COMPLETED("completed"), DELETED("deleted");

	private String value;

	private CourseWorkflowState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CourseWorkflowState getByValue(String value) {
		for (CourseWorkflowState courseWorkflowState : values()) {
			if (courseWorkflowState.getValue().equals(value)) {
				return courseWorkflowState;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}
}
