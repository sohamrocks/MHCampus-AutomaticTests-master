package com.mcgraw.test.automation.api.rest.canvas;

public enum CanvasEnrollmentType {
	STUDENT_ENROLLMENT("StudentEnrollment"), TEACHER_ENROLLMENT("TeacherEnrollment"), TA_ENROLLMENT("TaEnrollment"), DESIGNER_ENROLLMENT(
			"DesignerEnrollment"), OBSERVER_ENROLLMENT("ObserverEnrollment");

	private String value;

	private CanvasEnrollmentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CanvasEnrollmentType getByValue(String value) {
		for (CanvasEnrollmentType canvasEnrollmentType : values()) {
			if (canvasEnrollmentType.getValue().equals(value)) {
				return canvasEnrollmentType;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}
}
