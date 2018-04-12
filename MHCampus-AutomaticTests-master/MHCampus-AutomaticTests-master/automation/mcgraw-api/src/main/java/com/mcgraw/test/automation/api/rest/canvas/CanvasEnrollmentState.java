package com.mcgraw.test.automation.api.rest.canvas;

public enum CanvasEnrollmentState {
	ACTIVE("active"),
	INVITED("invited"),
	COMPLETED("completed"),
	DELETED("deleted");
	
	private String value;

	private CanvasEnrollmentState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CanvasEnrollmentState getByValue(String value) {
		for (CanvasEnrollmentState canvasEnrollmentState : values()) {
			if (canvasEnrollmentState.getValue().equals(value)) {
				return canvasEnrollmentState;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}
}
