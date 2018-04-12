package com.mcgraw.test.automation.api.sakai;

public enum SakaiUserRole {
	INSTRUCTOR("Instructor"),
	STUDENT("Student"),
	TEACHING_ASSISTANT("Teaching Assistant");
	
	private String value;
	
	private SakaiUserRole(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}
}
