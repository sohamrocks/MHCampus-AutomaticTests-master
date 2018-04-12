package com.mcgraw.test.automation.api.rest.moodle;

public enum MoodleUserRole {

	/** Manager role */
	MANAGER("1"),
	/** Teacher role */
	TEACHER("3"),
	/** Student role */
	STUDENT("5");

	private final String value;

	public String getValue() {
		return value;
	}

	private MoodleUserRole(String value) {
		this.value = value;
	}

	public static MoodleUserRole getByValue(String value) {
		for (MoodleUserRole moodleUserRole : values()){
			if (moodleUserRole.getValue().equals(value)) {
				return moodleUserRole;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
