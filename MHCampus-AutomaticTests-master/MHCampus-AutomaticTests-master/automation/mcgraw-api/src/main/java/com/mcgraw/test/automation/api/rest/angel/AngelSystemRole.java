package com.mcgraw.test.automation.api.rest.angel;

public enum AngelSystemRole {
	SYSTEM_ADMINISTRATOR("64"), DOMAIN_ADMINISTRATOR("32"), MANAGER("16"), FACULTY("8"), STAFF("4"), STUDENT("2"), GENERAL("0");

	private final String value;

	public String getValue() {
		return value;
	}

	private AngelSystemRole(String value) {
		this.value = value;
	}

	public static AngelSystemRole getByValue(String value) {
		for (AngelSystemRole angelSystemRole : values()) {
			if (angelSystemRole.getValue().equals(value)) {
				return angelSystemRole;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
