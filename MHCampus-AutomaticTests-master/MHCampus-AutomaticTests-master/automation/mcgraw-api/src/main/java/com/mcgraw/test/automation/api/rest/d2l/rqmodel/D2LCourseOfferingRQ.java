package com.mcgraw.test.automation.api.rest.d2l.rqmodel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for D2L create course offering Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.ALWAYS)
public class D2LCourseOfferingRQ {
	
	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Path")
	private String path;
	
	@JsonProperty(value = "CourseTemplateId")
	private int courseTemplateId;
	
	@JsonProperty(value = "SemesterId")
	private Integer semesterId;
	
	@JsonProperty(value = "StartDate")
	private Date startDate;
	
	@JsonProperty(value = "EndDate")
	private Date endDate;
	
	@JsonProperty(value = "LocaleId")
	private String localeId;
	
	@JsonProperty(value = "ForceLocale")
	private boolean forceLocale;
	
	@JsonProperty(value = "ShowAddressBook")
	private boolean showAddressBook;

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

	public int getCourseTemplateId() {
		return courseTemplateId;
	}

	public void setCourseTemplateId(int courseTemplateId) {
		this.courseTemplateId = courseTemplateId;
	}

	public Integer getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public boolean isForceLocale() {
		return forceLocale;
	}

	public void setForceLocale(boolean forceLocale) {
		this.forceLocale = forceLocale;
	}

	public boolean isShowAddressBook() {
		return showAddressBook;
	}

	public void setShowAddressBook(boolean showAddressBook) {
		this.showAddressBook = showAddressBook;
	}

}
