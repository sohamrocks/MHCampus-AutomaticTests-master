package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleDeleteUserRQ extends BaseNameValueRQ{
	
	private String userId;
	
	public void setUserId(String userId) {
		this.userId = userId;
		this.setBodyParameter("userids[0]", userId);
	}

	public String getUserId() {
		return userId;
	}
	
	@Override
	public String toString() {
		return "Moodle user [User id=" + userId + "]";
	}
}
