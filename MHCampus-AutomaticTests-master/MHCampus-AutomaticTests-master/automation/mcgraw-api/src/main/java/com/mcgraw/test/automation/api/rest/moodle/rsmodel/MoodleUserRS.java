package com.mcgraw.test.automation.api.rest.moodle.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Add User REST response representation. <br>
 * Uses Jackson (2.x) for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MoodleUserRS {

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "username")
	private String userName;

	public MoodleUserRS() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "Moodle User [id=" + id + ", userName=" + userName + "]";
	}

}
