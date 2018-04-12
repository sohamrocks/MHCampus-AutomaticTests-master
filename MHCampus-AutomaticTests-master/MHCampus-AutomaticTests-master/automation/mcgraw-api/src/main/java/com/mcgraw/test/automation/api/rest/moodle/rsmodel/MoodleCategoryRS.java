package com.mcgraw.test.automation.api.rest.moodle.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Add Category REST response representation. <br>
 * Uses Jackson (2.x) for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MoodleCategoryRS {
	
	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Moodle Category [id=" + id + ", name=" + name + "]";
	}
	
	

}
