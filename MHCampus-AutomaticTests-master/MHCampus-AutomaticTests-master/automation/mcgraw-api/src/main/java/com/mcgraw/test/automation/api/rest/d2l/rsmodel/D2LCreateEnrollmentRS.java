package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ.D2LUserRoleDeserializer;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ.D2LUserRoleSerializer;

/**
 * Json representation model for D2L Enrollment.EnrollmentData Uses Jackson (2.x)
 * for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class D2LCreateEnrollmentRS {
	
	@JsonProperty(value = "OrgUnitId")
	private int orgUnitId;
	
	@JsonProperty(value = "UserId")
	private int userId; 
	
	@JsonSerialize(using = D2LUserRoleSerializer.class)
	@JsonDeserialize(using = D2LUserRoleDeserializer.class)
	@JsonProperty(value = "RoleId")
	protected D2LUserRole role;
	
	@JsonProperty(value = "IsCascading")
	private boolean isCascading;

	public int getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(int orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public D2LUserRole getRole() {
		return role;
	}

	public void setRole(D2LUserRole role) {
		this.role = role;
	}

	public boolean isCascading() {
		return isCascading;
	}

	public void setCascading(boolean isCascading) {
		this.isCascading = isCascading;
	}
	
	@Override
	public String toString() {
		return "D2L Enrollment [orgUnitId=" + orgUnitId + ", userId=" + userId + ", role=" + role + "]";
	}
	
}
