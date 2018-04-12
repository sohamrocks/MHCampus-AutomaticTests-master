package com.mcgraw.test.automation.api.rest.canvas.rqmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json representation model for Canvas create user Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CanvasUserRQ {

	@JsonProperty(value = "user")
	private UserPart userPart;

	@JsonProperty(value = "pseudonym")
	private Pseudonym pseudonym;

	public CanvasUserRQ() {
		userPart = new UserPart();
		pseudonym = new Pseudonym();
	}

	public UserPart getUserPart() {
		return userPart;
	}

	public void setUserPart(UserPart userPart) {
		this.userPart = userPart;
	}

	public Pseudonym getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(Pseudonym pseudonym) {
		this.pseudonym = pseudonym;
	}

	@JsonInclude(Include.NON_NULL)
	public static class Pseudonym {

		@JsonProperty(value = "unique_id")
		private String uniqueId;

		@JsonProperty(value = "password")
		private String password;
		
		@JsonProperty(value="sis_user_id")
		private String sis_id;
		

		public String getUniqueId() {
			return uniqueId;
		}

		public void setUniqueId(String uniqueId) {
			this.uniqueId = uniqueId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public void setSisId (String sis_id){
			this.sis_id = sis_id;
		}
		
		public String getSisId(){
			return this.sis_id;
		}
		

	}

	@JsonInclude(Include.NON_NULL)
	public static class UserPart {

		@JsonProperty(value = "name")
		private String fullName;

		@JsonProperty(value = "short_name")
		private String shortName;

		@JsonProperty(value = "sortable_name")
		private String sortableName;

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getSortableName() {
			return sortableName;
		}

		public void setSortableName(String sortableName) {
			this.sortableName = sortableName;
		}

	}
}
