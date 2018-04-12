package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleEnrolUserRQ extends BaseNameValueRQ{
	
	private String role;
	
	private String userId;
	
	private String courseId;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
		this.setBodyParameter("enrolments[0][roleid]", role);		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		this.setBodyParameter("enrolments[0][userid]", userId);
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
		this.setBodyParameter("enrolments[0][courseid]", courseId);
	}	
}
