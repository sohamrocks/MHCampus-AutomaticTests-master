package com.mcgraw.test.automation.api.rest.d2l;

public enum D2LUserRole {
	ADMINISTRATOR(148, 104), 
	INSTRUCTOR(149, 105), 
	STUDENT(150, 106);

	private int roleIdVersion9;
	private int roleIdVersion10;

	private D2LUserRole(int roleIdVersion9, int roleIdVersion10) {
		this.roleIdVersion9 = roleIdVersion9;
		this.roleIdVersion10 = roleIdVersion10;
	}

	public int getRoleIdVersion9() {
		return roleIdVersion9;
	}
	
	public int getRoleIdVersion10() {
		return roleIdVersion10;
	}

	public void setRoleIdVersion10(int roleIdVersion10) {
		this.roleIdVersion10 = roleIdVersion10;
	}

	public static D2LUserRole getByIdForVersion9(int roleIdVersion9) {
		for (D2LUserRole canvasEnrollmentState : values()) {
			if (canvasEnrollmentState.getRoleIdVersion9() == roleIdVersion9) {
				return canvasEnrollmentState;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + roleIdVersion9 + "]");
	}
	
	public static D2LUserRole getByIdForVersion10(int roleIdVersion10) {
		for (D2LUserRole canvasEnrollmentState : values()) {
			if (canvasEnrollmentState.getRoleIdVersion10() == roleIdVersion10) {
				return canvasEnrollmentState;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + roleIdVersion10 + "]");
	}
}
