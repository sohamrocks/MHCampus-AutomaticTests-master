package com.mcgraw.test.automation.api.rest.angel.model;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class EnrolUserModel extends BaseNameValueRQ {

	private String userName;

	private String courseId;

	private AngelSectionRole angelSectionRole;

	public void setUserName(String userName) {
		this.userName = userName;
		this.setBodyParameter("USER", userName);
	}

	public void setCourseId(String sectionCode) {
		this.courseId = sectionCode;
		this.setBodyParameter("SECTION", sectionCode);
	}

	public void setAngelSectionRole(AngelSectionRole angelSectionRole) {
		this.angelSectionRole = angelSectionRole;
		this.setBodyParameter("SECTIONRIGHTS", angelSectionRole.getValue());
	}

	public String getUserName() {
		return userName;
	}

	public String getCourseId() {
		return courseId;
	}

	@Override
	public String toString() {
		return "Enrol User to course [user name=" + userName + ", course id=" + courseId + ", section role="
				+ angelSectionRole + "]";
	}

}
