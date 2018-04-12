package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class CanvasUserRS {

	@JsonProperty(value = "user")
	private CanvasUser user;

	public CanvasUser getUser() {
		return user;
	}

	public void setUser(CanvasUser user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "CanvasUserRS [user=" + user + "]";
	}
}
