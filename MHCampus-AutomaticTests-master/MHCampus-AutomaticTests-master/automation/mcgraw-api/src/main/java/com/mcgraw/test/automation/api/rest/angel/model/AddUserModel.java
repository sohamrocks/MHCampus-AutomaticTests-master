package com.mcgraw.test.automation.api.rest.angel.model;

import com.mcgraw.test.automation.api.rest.angel.AngelSystemRole;
import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class AddUserModel extends BaseNameValueRQ {

	private String userName;

	private String password;

	private String firstName;

	private String lastName;

	private AngelSystemRole systemRole;

	public void setUserName(String userName) {
		this.userName = userName;
		this.setBodyParameter("USER", userName);
	}

	public void setPassword(String password) {
		this.password = password;
		this.setBodyParameter("PASSWORD", password);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.setBodyParameter("FIRSTNAME", firstName);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.setBodyParameter("LASTNAME", lastName);
	}

	public void setSystemRole(AngelSystemRole systemRole) {
		this.systemRole = systemRole;
		this.setBodyParameter("SYSTEMRIGHTS", systemRole.getValue());
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public AngelSystemRole getSystemRole() {
		return systemRole;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", system role=" + systemRole + "]";
	}

}
