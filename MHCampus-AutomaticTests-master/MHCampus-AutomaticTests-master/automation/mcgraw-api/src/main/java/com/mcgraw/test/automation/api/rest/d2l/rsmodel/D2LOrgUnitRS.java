package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for D2L organization unit type info Uses Jackson
 * (2.x) for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class D2LOrgUnitRS {
	
	@JsonProperty(value = "Identifier")
	private int id;
	
	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Code")
	private String code;
	
	@JsonProperty(value = "Type")
	private D2LOuTypeRS d2lOuTypeRS;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public D2LOuTypeRS getD2lOuTypeRS() {
		return d2lOuTypeRS;
	}

	public void setD2lOuTypeRS(D2LOuTypeRS d2lOuTypeRS) {
		this.d2lOuTypeRS = d2lOuTypeRS;
	}
	
	@Override
	public String toString() {
		return "D2L Org Unit [id=" + id + ", name=" + name + ", code=" + code + ", '"+d2lOuTypeRS+ "']";
	}
	
}
