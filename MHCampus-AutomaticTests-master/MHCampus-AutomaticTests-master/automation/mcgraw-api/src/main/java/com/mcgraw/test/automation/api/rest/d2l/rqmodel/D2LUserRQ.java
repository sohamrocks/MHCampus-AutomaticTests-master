package com.mcgraw.test.automation.api.rest.d2l.rqmodel;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;

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
public class D2LUserRQ {

	@JsonProperty(value = "OrgDefinedId")
	private int orgDefinedId;

	@JsonProperty(value = "FirstName")
	private String firstName;

	@JsonProperty(value = "MiddleName")
	private String middleName;

	@JsonProperty(value = "LastName")
	private String lastName;
	
	@JsonProperty(value = "ExternalEmail")
	private String externalEmail;

	@JsonProperty(value = "UserName")
	private String userName;

	@JsonSerialize(using = D2LUserRoleSerializer.class)
	@JsonDeserialize(using = D2LUserRoleDeserializer.class)
	@JsonProperty(value = "RoleId")
	protected D2LUserRole role;

	@JsonProperty(value = "IsActive")
	private boolean active;

	@JsonProperty(value = "SendCreationEmail")
	private boolean sendCreationEmail;

	public int getOrgDefinedId() {
		return orgDefinedId;
	}

	public void setOrgDefinedId(int orgDefinedId) {
		this.orgDefinedId = orgDefinedId;
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

	public String getExternalEmail() {
		return externalEmail;
	}

	public void setExternalEmail(String externalEmail) {
		this.externalEmail = externalEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public D2LUserRole getRole() {
		return role;
	}

	public void setRole(D2LUserRole role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isSendCreationEmail() {
		return sendCreationEmail;
	}

	public void setSendCreationEmail(boolean sendCreationEmail) {
		this.sendCreationEmail = sendCreationEmail;
	}

	public static class D2LUserRoleSerializer extends JsonSerializer<D2LUserRole> {

		@Override
		public void serialize(D2LUserRole userRole, JsonGenerator generator, SerializerProvider provider) throws IOException,
				JsonProcessingException {
			generator.writeString(String.valueOf(userRole.getRoleIdVersion10()));

		}
	}

	public static class D2LUserRoleDeserializer extends JsonDeserializer<D2LUserRole> {

		@Override
		public D2LUserRole deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException,
				JsonProcessingException {
			return D2LUserRole.getByIdForVersion10(paramJsonParser.getIntValue());
		}
	}

}
