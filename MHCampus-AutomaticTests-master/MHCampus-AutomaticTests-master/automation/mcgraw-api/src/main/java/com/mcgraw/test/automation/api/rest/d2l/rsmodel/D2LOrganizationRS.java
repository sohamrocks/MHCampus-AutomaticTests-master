package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for D2L organization info Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class D2LOrganizationRS {

	@JsonProperty(value = "Identifier")
	private int id;

	@JsonProperty(value = "Name")
	private String name;
	
	@JsonProperty(value = "TimeZone")
	private String timeZone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getTimeZone(){
		return timeZone;
	}
	
	public void setTimeZone(String timeZone){
		this.timeZone = timeZone;
	}

	@Override
	public String toString() {
		return "D2L Organization [id=" + id + ", name=" + name + ", timeZone=" + timeZone + " ]";
	}
}
