package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class D2LCourseTemplateRS {

	@JsonProperty(value = "Identifier")
	private int id;

	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Path")
	private String path;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "D2L Course Template [id=" + id + ", name=" + name + ", code=" + code + ", path=" + path + "]";
	}

}
