package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleAddCourseRQ extends BaseNameValueRQ {

	private String fullName;

	private String shortName;

	private String categoryId;

	public void setFullName(String fullName) {
		this.fullName = fullName;
		this.setBodyParameter("courses[0][fullname]", fullName);
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
		this.setBodyParameter("courses[0][shortname]", shortName);
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
		this.setBodyParameter("courses[0][categoryid]", categoryId);
	}

	public String getFullName() {
		return fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public String getCategoryId() {
		return categoryId;
	}
	
	@Override
	public String toString() {
		return "Moodle Course [Full name=" + fullName + ",  Short name=" + shortName + "]";
	}	
}
