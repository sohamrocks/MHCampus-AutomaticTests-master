package com.mcgraw.test.automation.api.rest.d2l.rqmodel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for D2L create course template Uses Jackson (2.x)
 * for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class D2LCreateCourseTemplateRQ {

	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Code")
	private String code;

	/** path is generated in back-end service */
	@JsonProperty(value = "Path")
	private final String path = "";

	@JsonProperty(value = "ParentOrgUnitIds")
	private List<Integer> parentOrgUnitIds;

	public D2LCreateCourseTemplateRQ() {
		parentOrgUnitIds = new ArrayList<Integer>();
	}

	public D2LCreateCourseTemplateRQ addParentOrgUnitIds(int... parentOrgUnitIds) {
		for (int parentOrgUnitId : parentOrgUnitIds) {
			this.parentOrgUnitIds.add(parentOrgUnitId);
		}
		return this;
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

	public List<Integer> getParentOrgUnitIds() {
		return parentOrgUnitIds;
	}

	public void setParentOrgUnitIds(List<Integer> parentOrgUnitIds) {
		this.parentOrgUnitIds = parentOrgUnitIds;
	}

	public String getPath() {
		return path;
	}
}
