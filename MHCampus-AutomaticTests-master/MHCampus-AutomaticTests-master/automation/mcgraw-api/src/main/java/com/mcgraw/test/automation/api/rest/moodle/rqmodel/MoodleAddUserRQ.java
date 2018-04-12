package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleAddUserRQ extends BaseNameValueRQ {
	
	private String userName;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String city;
	
	private String country;
	
	private String auth;

	public void setUserName(String userName) {
		this.userName = userName;
		this.setBodyParameter("users[0][username]", userName);
	}

	public void setPassword(String password) {
		this.password = password;
		this.setBodyParameter("users[0][password]", password);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.setBodyParameter("users[0][firstname]", firstName);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.setBodyParameter("users[0][lastname]", lastName);
	}

	public void setEmail(String email) {
		this.email = email;
		this.setBodyParameter("users[0][email]", email);
	}
	
	public void setCity(String city) {
		this.city = city;
		this.setBodyParameter("users[0][city]", city);
	}

	public void setCountry(String country) {
		this.country = country;
		this.setBodyParameter("users[0][country]", country);
		
	}

	public void setAuth(String auth) {
		this.auth = auth;
		this.setBodyParameter("users[0][auth]", auth);
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

	public String getEmail() {
		return email;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getAuth() {
		return auth;
	}
	
	@Override
	public String toString() {
		return "Moodle user [User name=" + userName + ", Password=" + password + ", First name=" + firstName + ", Last name=" + lastName + "]";
	}

}
