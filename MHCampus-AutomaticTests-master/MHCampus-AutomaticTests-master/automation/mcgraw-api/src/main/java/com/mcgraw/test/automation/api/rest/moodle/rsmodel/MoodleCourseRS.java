package com.mcgraw.test.automation.api.rest.moodle.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Add Course REST response representation. <br>
 * Uses Jackson (2.x) for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MoodleCourseRS {
	
	@JsonProperty(value = "id")
	private String id;
	
	@JsonProperty(value = "shortname")
	private String shortName;


	public MoodleCourseRS() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return shortName;
	}

	public void setUserName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return "Moodle Course [id=" + id + ", shortName=" + shortName + "]";
	}

}
