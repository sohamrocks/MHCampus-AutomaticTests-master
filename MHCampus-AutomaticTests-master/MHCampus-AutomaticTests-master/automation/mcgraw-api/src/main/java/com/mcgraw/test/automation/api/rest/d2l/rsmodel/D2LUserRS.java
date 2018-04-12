package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json representation model for D2L create user Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class D2LUserRS {

	@JsonProperty(value = "OrgId")
	private int orgId;

	@JsonProperty(value = "UserId")
	private int userId;

	@JsonProperty(value = "FirstName")
	private String firstName;

	@JsonProperty(value = "MiddleName")
	private String middleName;

	@JsonProperty(value = "LastName")
	private String lastName;

	@JsonProperty(value = "UserName")
	private String userName;

	@JsonProperty(value = "OrgDefinedId")
	private int orgDefinedId;

	@JsonProperty(value = "UniqueIdentifier")
	private String uniqueIdentifier;

	@JsonProperty(value = "DisplayName")
	private String displayName;

	

	public int getOrgId() {
		return orgId;
	}



	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getMiddleName() {
		return middleName;
	}



	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public int getOrgDefinedId() {
		return orgDefinedId;
	}



	public void setOrgDefinedId(int orgDefinedId) {
		this.orgDefinedId = orgDefinedId;
	}



	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}



	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}



	public String getDisplayName() {
		return displayName;
	}



	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}



	@Override
	public String toString() {
		return "D2L User [userId=" + userId + ", userName=" + userName + ", orgId=" + orgId + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", orgId=" + orgId + ", displayName=" + displayName + "]";
	}

}
